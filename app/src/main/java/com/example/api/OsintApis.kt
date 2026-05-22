package com.example.api

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class OsintPlatform(
    val name: String,
    val urlTemplate: String,
    val iconName: String = ""
)

object OsintApis {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val geminiClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Top platforms for username target profile check
    val platforms = listOf(
        OsintPlatform("GitHub", "https://github.com/{}"),
        OsintPlatform("Reddit", "https://www.reddit.com/user/{}"),
        OsintPlatform("Medium", "https://medium.com/@{}"),
        OsintPlatform("Pinterest", "https://www.pinterest.com/{}"),
        OsintPlatform("Dev.to", "https://dev.to/{}"),
        OsintPlatform("DeviantArt", "https://www.deviantart.com/{}"),
        OsintPlatform("SoundCloud", "https://soundcloud.com/{}"),
        OsintPlatform("Vimeo", "https://vimeo.com/{}"),
        OsintPlatform("ProductHunt", "https://www.producthunt.com/@{}"),
        OsintPlatform("Steam", "https://steamcommunity.com/id/{}"),
        OsintPlatform("Letterboxd", "https://letterboxd.com/{}")
    )

    /**
     * Checks if a username profile exists on a specific platform by executing a real HTTP request.
     */
    suspend fun checkUsernameAvailability(username: String, platform: OsintPlatform): Boolean = withContext(Dispatchers.IO) {
        val targetUrl = platform.urlTemplate.replace("{}", username)
        try {
            val request = Request.Builder()
                .url(targetUrl)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9")
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                // Standard check: 200 means active/claimed, 404 means vacant/not found
                response.code == 200
            }
        } catch (e: Exception) {
            Log.e("OsintApis", "Failed to check username availability for $targetUrl: ${e.message}")
            false
        }
    }

    /**
     * Queries ip-api.com for coordinates, location, ISP information of an IP or Domain.
     */
    suspend fun fetchGeoIpInfo(query: String): String = withContext(Dispatchers.IO) {
        val cleanQuery = query.trim()
            .replace("https://", "")
            .replace("http://", "")
            .substringBefore("/")

        // ip-api.com returns Whois/lookup data
        val url = "http://ip-api.com/json/$cleanQuery?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query"
        try {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "AegisOSINT/1.0")
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    response.body?.string() ?: "{\"status\":\"fail\",\"message\":\"Empty response body\"}"
                } else {
                    "{\"status\":\"fail\",\"message\":\"HTTP error ${response.code}\"}"
                }
            }
        } catch (e: Exception) {
            "{\"status\":\"fail\",\"message\":\"${e.message}\"}"
        }
    }

    /**
     * Asks the intelligence analyst assistant powered by Gemini 3.5 Flash for investigation planning.
     */
    suspend fun askGeminiAnalyst(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "ERROR_API_KEY_MISSING"
        }

        // Direct REST API for Gemini
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
        val systemMessage = "You are a seasoned Cyber Security Intelligence command officer and expert military-grade OSINT specialist. Your name is 'CyberAnalyst-3.5'. " +
                "Generate precise, detailed investigative advice. Construct specialized search patterns (Google Dorks), map potential next steps of reconnaissance, " +
                "and explain data correlations objectively. Keep formatting clear with bullet points, terminal command syntax, and actionable items. Never write conversational filler."

        try {
            val systemInstructionPart = JSONObject().put("text", systemMessage)
            val systemInstructionContent = JSONObject().put("parts", JSONArray().put(systemInstructionPart))

            val userPart = JSONObject().put("text", prompt)
            val userContent = JSONObject().put("parts", JSONArray().put(userPart))

            val requestBodyJson = JSONObject()
                .put("contents", JSONArray().put(userContent))
                .put("systemInstruction", systemInstructionContent)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = requestBodyJson.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            geminiClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val bodyString = response.body?.string() ?: ""
                    val rootJson = JSONObject(bodyString)
                    val candidates = rootJson.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val firstCandidate = candidates.getJSONObject(0)
                        val content = firstCandidate.getJSONObject("content")
                        val parts = content.getJSONArray("parts")
                        if (parts.length() > 0) {
                            parts.getJSONObject(0).optString("text") ?: "No text output."
                        } else {
                            "Error: Output part list empty."
                        }
                    } else {
                        "Error: Response model did not generate output candidates ($bodyString)."
                    }
                } else {
                    val errBody = response.body?.string() ?: ""
                    Log.e("OsintApis", "Gemini call failed with code ${response.code}: $errBody")
                    "Error: Server returned HTTP status ${response.code}. Details: $errBody"
                }
            }
        } catch (e: Exception) {
            Log.e("OsintApis", "Gemini HTTP Client error: ${e.message}", e)
            "Error querying intel unit: ${e.message}"
        }
    }
}
