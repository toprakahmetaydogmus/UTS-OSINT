package com.example.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.OsintApis
import com.example.api.OsintPlatform
import com.example.data.OsintDatabase
import com.example.data.OsintRepository
import com.example.data.OsintScanItem
import com.example.data.SavedDork
import com.example.utils.ExifExtractor
import com.example.utils.ImageExifMetadata
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed interface GeoIpUiState {
    object Idle : GeoIpUiState
    object Loading : GeoIpUiState
    data class Success(val dataJson: String, val parsed: ParsedGeoIp) : GeoIpUiState
    data class Error(val message: String) : GeoIpUiState
}

data class ParsedGeoIp(
    val ip: String,
    val country: String,
    val countryCode: String,
    val region: String,
    val city: String,
    val zip: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val isp: String,
    val org: String,
    val asInfo: String
)

sealed interface UsernameScanUiState {
    object Idle : UsernameScanUiState
    data class Scanning(val progress: Float, val currentPlatform: String) : UsernameScanUiState
    data class Finished(val claimedCount: Int) : UsernameScanUiState
}

data class PlatformResult(
    val platformName: String,
    val profileUrl: String,
    val exists: Boolean?, // null = waiting, true = claimed, false = safe/absent
    val error: String? = null
)



class OsintViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OsintRepository

    init {
        val database = OsintDatabase.getDatabase(application)
        repository = OsintRepository(database.osintDao())
    }

    // --- Observable streams from database ---
    val allScans: StateFlow<List<OsintScanItem>> = repository.allScans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSavedDorks: StateFlow<List<SavedDork>> = repository.allDorks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- State: IP/Domain lookup ---
    private val _geoIpState = MutableStateFlow<GeoIpUiState>(GeoIpUiState.Idle)
    val geoIpState: StateFlow<GeoIpUiState> = _geoIpState.asStateFlow()

    private val _reconTarget = MutableStateFlow("")
    val reconTarget: StateFlow<String> = _reconTarget.asStateFlow()

    // --- State: Username scanners ---
    private val _usernameTarget = MutableStateFlow("")
    val usernameTarget: StateFlow<String> = _usernameTarget.asStateFlow()

    private val _usernameScanState = MutableStateFlow<UsernameScanUiState>(UsernameScanUiState.Idle)
    val usernameScanState: StateFlow<UsernameScanUiState> = _usernameScanState.asStateFlow()

    private val _usernameResults = MutableStateFlow<List<PlatformResult>>(emptyList())
    val usernameResults: StateFlow<List<PlatformResult>> = _usernameResults.asStateFlow()

    private var usernameScanJob: Job? = null

    // --- State: Metadata analyzer ---
    private val _metadataImageUri = MutableStateFlow<Uri?>(null)
    val metadataImageUri: StateFlow<Uri?> = _metadataImageUri.asStateFlow()

    private val _exifData = MutableStateFlow<ImageExifMetadata?>(null)
    val exifData: StateFlow<ImageExifMetadata?> = _exifData.asStateFlow()



    // --- State: Dork Constructor parameters ---
    private val _dorkCategory = MutableStateFlow("Sensitive Files")
    val dorkCategory: StateFlow<String> = _dorkCategory.asStateFlow()

    private val _dorkDomain = MutableStateFlow("")
    val dorkDomain: StateFlow<String> = _dorkDomain.asStateFlow()

    private val _dorkKeyword = MutableStateFlow("")
    val dorkKeyword: StateFlow<String> = _dorkKeyword.asStateFlow()

    private val _dorkExtension = MutableStateFlow("pdf")
    val dorkExtension: StateFlow<String> = _dorkExtension.asStateFlow()

    // --- Methods: IP/Domain Lookup ---
    fun updateReconTarget(target: String) {
        _reconTarget.value = target
    }

    fun lookupIpOrDomain() {
        val target = _reconTarget.value.trim()
        if (target.isEmpty()) return

        viewModelScope.launch {
            _geoIpState.value = GeoIpUiState.Loading
            val rawJson = OsintApis.fetchGeoIpInfo(target)
            try {
                val json = JSONObject(rawJson)
                if (json.optString("status") == "success") {
                    val parsed = ParsedGeoIp(
                        ip = json.optString("query", target),
                        country = json.optString("country", "Unknown"),
                        countryCode = json.optString("countryCode", "UN"),
                        region = json.optString("regionName", "Unknown"),
                        city = json.optString("city", "Unknown"),
                        zip = json.optString("zip", ""),
                        lat = json.optDouble("lat", 0.0),
                        lon = json.optDouble("lon", 0.0),
                        timezone = json.optString("timezone", ""),
                        isp = json.optString("isp", "No ISP"),
                        org = json.optString("org", "No Org"),
                        asInfo = json.optString("as", "No AS")
                    )
                    _geoIpState.value = GeoIpUiState.Success(rawJson, parsed)

                    // Write search query to offline trace history
                    repository.insertScan(
                        OsintScanItem(
                            type = "IP_GEO",
                            target = target,
                            resultJson = rawJson
                        )
                    )
                } else {
                    val msg = json.optString("message", "Validation/Lookup failed")
                    _geoIpState.value = GeoIpUiState.Error(msg)
                }
            } catch (e: Exception) {
                _geoIpState.value = GeoIpUiState.Error("Data compilation failure: ${e.message}")
            }
        }
    }

    // --- Methods: Username scanning ---
    fun updateUsernameTarget(target: String) {
        _usernameTarget.value = target
    }

    fun startUsernameScan() {
        val username = _usernameTarget.value.trim()
        if (username.isEmpty()) return

        usernameScanJob?.cancel()
        usernameScanJob = viewModelScope.launch {
            val platformsList = OsintApis.platforms
            _usernameResults.value = platformsList.map { 
                PlatformResult(it.name, it.urlTemplate.replace("{}", username), null)
            }

            var claimedCount = 0
            platformsList.forEachIndexed { index, platform ->
                _usernameScanState.value = UsernameScanUiState.Scanning(
                    progress = (index + 1).toFloat() / platformsList.size,
                    currentPlatform = platform.name
                )

                val exists = OsintApis.checkUsernameAvailability(username, platform)
                if (exists) claimedCount++

                // Update results list state with platform response
                _usernameResults.value = _usernameResults.value.map {
                    if (it.platformName == platform.name) {
                        it.copy(exists = exists)
                    } else it
                }
            }

            _usernameScanState.value = UsernameScanUiState.Finished(claimedCount)

            // Save trace to scan log history
            val summary = "Username '$username' verified on ${platformsList.size} networks. $claimedCount trace-matches verified."
            repository.insertScan(
                OsintScanItem(
                    type = "USERNAME",
                    target = username,
                    resultJson = JSONObject().put("summary", summary).put("matches", claimedCount).toString()
                )
            )
        }
    }

    fun cancelUsernameScan() {
        usernameScanJob?.cancel()
        _usernameScanState.value = UsernameScanUiState.Idle
        _usernameResults.value = emptyList()
    }

    // --- Methods: Metadata Extraction ---
    fun analyzeLocalImageExif(uri: Uri) {
        _metadataImageUri.value = uri
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val metadata = ExifExtractor.extractMetadata(context, uri)
            _exifData.value = metadata

            // Store scan in persistent db logs if valid EXIF records present
            if (metadata.hasExif) {
                val detailString = "Exif found: device=${metadata.deviceMake} ${metadata.deviceModel}, date=${metadata.dateTimeTaken}, coords=${metadata.latitude},${metadata.longitude}"
                repository.insertScan(
                    OsintScanItem(
                        type = "EXIF_FILE",
                        target = uri.lastPathSegment ?: "image",
                        resultJson = JSONObject().put("summary", detailString).put("lat", metadata.latitude).put("lon", metadata.longitude).toString()
                    )
                )
            }
        }
    }

    fun clearMetadataState() {
        _metadataImageUri.value = null
        _exifData.value = null
    }



    // --- Methods: Google Dork Generator ---
    fun updateDorkCategory(category: String) {
        _dorkCategory.value = category
    }

    fun updateDorkDomain(domain: String) {
        _dorkDomain.value = domain
    }

    fun updateDorkKeyword(keyword: String) {
        _dorkKeyword.value = keyword
    }

    fun updateDorkExtension(extension: String) {
        _dorkExtension.value = extension
    }

    fun buildGoogleDorkQuery(): String {
        val domainSearch = if (_dorkDomain.value.trim().isNotEmpty()) "site:${_dorkDomain.value.trim()} " else ""
        val keywordSearch = if (_dorkKeyword.value.trim().isNotEmpty()) "\"${_dorkKeyword.value.trim()}\" " else ""
        
        return when (_dorkCategory.value) {
            "Sensitive Files" -> {
                val ext = _dorkExtension.value.trim()
                "${domainSearch}${keywordSearch}filetype:${ext}"
            }
            "Directory Listing" -> {
                "${domainSearch}intitle:\"index of\" \"${_dorkKeyword.value.trim().ifEmpty { "parent directory" }}\""
            }
            "Login Portals" -> {
                "${domainSearch}inurl:login OR inurl:signin intitle:login OR intitle:signin"
            }
            "Vulnerability Scan" -> {
                "${domainSearch}inurl:\"/phpinfo.php\" OR inurl:\"/.env\" OR inurl:\"/config.json\""
            }
            "Email Harvesting" -> {
                "site:${_dorkDomain.value.trim().ifEmpty { "linkedin.com" }} \"@${_dorkDomain.value.trim().ifEmpty { "gmail.com" }}\""
            }
            else -> "${domainSearch}${keywordSearch}"
        }
    }

    fun saveConstructedDork() {
        val query = buildGoogleDorkQuery()
        val cat = _dorkCategory.value
        val desc = "Dork for target keyword: ${_dorkKeyword.value.ifEmpty { "none" }} [domain: ${_dorkDomain.value.ifEmpty { "any" }}]"
        
        viewModelScope.launch {
            repository.insertDork(
                SavedDork(
                    category = cat,
                    query = query,
                    description = desc
                )
            )
        }
    }

    fun deleteSavedDork(id: Int) {
        viewModelScope.launch {
            repository.deleteDork(id)
        }
    }

    fun deleteScanItem(id: Int) {
        viewModelScope.launch {
            repository.deleteScan(id)
        }
    }

    fun clearAllScanHistory() {
        viewModelScope.launch {
            repository.clearScans()
        }
    }

    fun executeCoroutineInScope(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    suspend fun saveCustomAuditLog(toolName: String, type: String, result: String) {
        repository.insertScan(
            com.example.data.OsintScanItem(
                type = type,
                target = toolName,
                resultJson = org.json.JSONObject().put("url", result).toString()
            )
        )
    }
}
