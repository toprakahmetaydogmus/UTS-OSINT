package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.*
import com.example.ui.theme.*
import com.example.data.*
import com.example.utils.ImageExifMetadata
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainContainerScreen()
            }
        }
    }
}

enum class OsintTab(val title: String, val icon: ImageVector) {
    DASHBOARD("Hub", Icons.Default.Home),
    FRAMEWORK("Framework", Icons.Default.Menu),
    RECON("Recon", Icons.Default.Search),
    METADATA("Exif", Icons.Default.Info),
    DORKING("Dorks", Icons.Default.Build)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainContainerScreen(viewModel: OsintViewModel = viewModel()) {
    var currentTab by remember { mutableStateOf(OsintTab.DASHBOARD) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            Column {
                TopAppBarContent(currentTab)
                HorizontalDivider(color = CyberBorder, thickness = 1.dp)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = CyberBorder, thickness = 1.dp)
                TacticalBottomBar(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it }
                )
            }
        },
        containerColor = CyberBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CyberBlack)
        ) {
            // Cool cyber matrix background overlay grid
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val gridSpacing = 45.dp.toPx()
                        var x = 0f
                        while (x < size.width) {
                            drawLine(
                                color = CyberBorder.copy(alpha = 0.12f),
                                start = androidx.compose.ui.geometry.Offset(x, 0f),
                                end = androidx.compose.ui.geometry.Offset(x, size.height),
                                strokeWidth = 1f
                            )
                            x += gridSpacing
                        }
                        var y = 0f
                        while (y < size.height) {
                            drawLine(
                                color = CyberBorder.copy(alpha = 0.12f),
                                start = androidx.compose.ui.geometry.Offset(0f, y),
                                end = androidx.compose.ui.geometry.Offset(size.width, y),
                                strokeWidth = 1f
                            )
                            y += gridSpacing
                        }
                    }
            )

            // Dynamic Screen Transitions
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150)) togetherWith fadeOut(animationSpec = tween(150))
                },
                label = "ScreenTransition"
            ) { tab ->
                when (tab) {
                    OsintTab.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                    OsintTab.FRAMEWORK -> OsintFrameworkScreen(viewModel = viewModel)
                    OsintTab.RECON -> ReconScreen(viewModel = viewModel)
                    OsintTab.METADATA -> MetadataScreen(viewModel = viewModel)
                    OsintTab.DORKING -> DorkBuilderScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TopAppBarContent(activeTab: OsintTab) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CyberBlack)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Dim blinking LED matching status
                val infiniteTransition = rememberInfiniteTransition(label = "Blink")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 1.0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1200, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "BlinkAlpha"
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(CyberGreen.copy(alpha = alpha))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SYSTEM STATUS: ACTIVE",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = CyberCyan,
                    letterSpacing = 2.sp
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "UTS-OSINT",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = CyberWhite,
                letterSpacing = 0.5.sp
            )
        }

        // Circular badge matching header card of Sophisticated Dark
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF1A1C1F))
                .border(1.dp, Color(0x1BFFFFFF), RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Tactical Shield Link",
                tint = CyberCyan,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun TacticalBottomBar(
    currentTab: OsintTab,
    onTabSelected: (OsintTab) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF1A1C1F),
        tonalElevation = 0.dp,
        modifier = Modifier.navigationBarsPadding()
    ) {
        OsintTab.values().forEach { tab ->
            val selected = currentTab == tab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = "Navigate to ${tab.title}",
                        tint = if (selected) CyberCyan else CyberGray
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) CyberCyan else CyberGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = CyberNavy,
                    selectedIconColor = CyberCyan,
                    unselectedIconColor = CyberGray
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
            )
        }
    }
}

// --- SUB SCREEN: DASHBOARD ---
@Composable
fun AppLightningLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF00D2FF), Color(0xFF005BFF))
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            val w = size.width
            val h = size.height
            
            // Drop shadow path
            val shadowPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.60f, h * 0.18f)
                lineTo(w * 0.37f, h * 0.58f)
                lineTo(w * 0.52f, h * 0.58f)
                lineTo(w * 0.44f, h * 0.92f)
                lineTo(w * 0.70f, h * 0.50f)
                lineTo(w * 0.55f, h * 0.50f)
                close()
            }
            drawPath(path = shadowPath, color = Color.Black.copy(alpha = 0.25f))
            
            // Main white lightning bolt
            val boltPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(w * 0.58f, h * 0.16f)
                lineTo(w * 0.35f, h * 0.56f)
                lineTo(w * 0.50f, h * 0.56f)
                lineTo(w * 0.42f, h * 0.90f)
                lineTo(w * 0.68f, h * 0.48f)
                lineTo(w * 0.53f, h * 0.48f)
                close()
            }
            drawPath(path = boltPath, color = Color.White)
        }
    }
}

@Composable
fun DashboardScreen(viewModel: OsintViewModel) {
    val context = LocalContext.current
    val history by viewModel.allScans.collectAsStateWithLifecycle()
    val savedDorks by viewModel.allSavedDorks.collectAsStateWithLifecycle()
 
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Tactical Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberNavy),
                border = BorderStroke(1.dp, CyberBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "SECURE TELEMETRY LINK ESTABLISHED",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = CyberGreen,
                                fontSize = 12.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "UTS-OSINT WORKSTATION",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.ExtraBold,
                                color = CyberWhite,
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        AppLightningLogo()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Developer: Toprak Ahmet Aydoğmuş",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This mobile workstation coordinates open-source reconnaissance (OSINT), profile mapping, Exif analysis, cyber intelligence queries, and Google Dorking. Developed by Toprak Ahmet Aydoğmuş.",
                        color = CyberWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                    
                    Spacer(modifier = Modifier.height(14.dp))
                    
                    // Clickable secure site link
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberBlack),
                        border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://hopp.bio/siberegitim"))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Cannot open external web agent.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Siber Eğitim Link",
                                tint = CyberCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "SİBER EĞİTİM PORTALI",
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = CyberCyan
                                )
                                Text(
                                    text = "hopp.bio/siberegitim",
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 12.sp,
                                    color = CyberWhite,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Go",
                                tint = CyberCyan.copy(alpha = 0.7f),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }

        // Actionable External Portals Grid
        item {
            Text(
                text = "INVESTIGATION WEBSITES",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = CyberGray,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PortalCard(
                        title = "Shodan",
                        desc = "IoT Search Engine",
                        url = "https://www.shodan.io",
                        modifier = Modifier.weight(1f)
                    )
                    PortalCard(
                        title = "VirusTotal",
                        desc = "Threat Intelligence",
                        url = "https://www.virustotal.com",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PortalCard(
                        title = "HaveIBeenPwned",
                        desc = "Breach Checking",
                        url = "https://haveibeenpwned.com",
                        modifier = Modifier.weight(1f)
                    )
                    PortalCard(
                        title = "DNSDumpster",
                        desc = "DNS Recon / Mapping",
                        url = "https://dnsdumpster.com",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Database Metrics summary
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard(
                    label = "RECON SEARCHES",
                    value = history.size.toString(),
                    color = CyberCyan,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    label = "STASHED DORKS",
                    value = savedDorks.size.toString(),
                    color = CyberGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // History list title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "OFFLINE ACTIVITY TRACE HISTORY",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = CyberGray,
                    letterSpacing = 1.sp
                )
                if (history.isNotEmpty()) {
                    TextButton(
                        onClick = { viewModel.clearAllScanHistory() },
                        colors = ButtonDefaults.textButtonColors(contentColor = CyberRed)
                    ) {
                        Text(
                            text = "CLEAR ALL",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (history.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "No scan logs present",
                            tint = CyberGray.copy(alpha = 0.3f),
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No investigative traces recorded locally.",
                            color = CyberGray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        } else {
            items(history) { scan ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberNavy.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, CyberBorder),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (scan.type == "USERNAME") CyberGreen.copy(alpha = 0.15f)
                                            else if (scan.type == "IP_GEO") CyberCyan.copy(alpha = 0.15f)
                                            else CyberOrange.copy(alpha = 0.15f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = scan.type,
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = if (scan.type == "USERNAME") CyberGreen else if (scan.type == "IP_GEO") CyberCyan else CyberOrange,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = scan.target,
                                    color = CyberWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Format timestamp nicely
                            val dateString = remember(scan.timestamp) {
                                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                                    .format(java.util.Date(scan.timestamp))
                            }
                            Text(
                                text = "Logged: $dateString",
                                color = CyberGray,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        IconButton(
                            onClick = { viewModel.deleteScanItem(scan.id) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove trace record from logs",
                                tint = CyberRed.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PortalCard(
    title: String,
    desc: String,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberNavy),
        border = BorderStroke(1.dp, CyberBorder),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Cannot open external web browser", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = CyberCyan
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Launch external URL",
                    tint = CyberCyan.copy(alpha = 0.5f),
                    modifier = Modifier.size(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = desc,
                fontSize = 10.sp,
                color = CyberWhite.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MetricCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberBlack),
        border = BorderStroke(1.dp, CyberBorder),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = CyberGray,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 26.sp,
                color = color,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black
            )
        }
    }
}

// --- SUB SCREEN: RECON (IP/Domain + Username checks) ---
@Composable
fun ReconScreen(viewModel: OsintViewModel) {
    var subTabState by remember { mutableStateOf(0) } // 0 = IP/Domain lookup, 1 = Username Search

    Column(modifier = Modifier.fillMaxSize()) {
        // Styled Tab Subheaders
        TabRow(
            selectedTabIndex = subTabState,
            containerColor = CyberBlack,
            contentColor = CyberCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[subTabState]),
                    color = CyberCyan
                )
            },
            divider = { HorizontalDivider(color = CyberBorder) }
        ) {
            Tab(
                selected = subTabState == 0,
                onClick = { subTabState = 0 },
                text = {
                    Text(
                        "IP / DOMAIN",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            Tab(
                selected = subTabState == 1,
                onClick = { subTabState = 1 },
                text = {
                    Text(
                        "USERNAME PROFILE",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (subTabState == 0) {
                IpDomainLookupPane(viewModel)
            } else {
                UsernameScannerPane(viewModel)
            }
        }
    }
}

@Composable
fun IpDomainLookupPane(viewModel: OsintViewModel) {
    val target by viewModel.reconTarget.collectAsStateWithLifecycle()
    val geoIpState by viewModel.geoIpState.collectAsStateWithLifecycle()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "INTEL TARGET LOCATOR / WHOIS",
            color = CyberGray,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = target,
                onValueChange = { viewModel.updateReconTarget(it) },
                placeholder = {
                    Text(
                        "Enter IP or host...",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = CyberGray
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ip_lookup_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberCyan.copy(alpha = 0.5f),
                    unfocusedBorderColor = CyberBorder,
                    focusedLabelColor = CyberCyan,
                    unfocusedContainerColor = Color(0xFF1B1D21),
                    focusedContainerColor = Color(0xFF1B1D21)
                ),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = CyberWhite
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Ascii
                ),
                keyboardActions = KeyboardActions(onSearch = { viewModel.lookupIpOrDomain() })
            )

            Button(
                onClick = { viewModel.lookupIpOrDomain() },
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberBlack),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(56.dp)
                    .width(88.dp)
                    .testTag("ip_lookup_button")
            ) {
                Text(
                    text = "RECON",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Search Results display
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (val state = geoIpState) {
                is GeoIpUiState.Idle -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                            .background(CyberNavy.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Radar Idle",
                                tint = CyberGray.copy(alpha = 0.3f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Awaiting IP or host coordinates...",
                                color = CyberGray,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is GeoIpUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = CyberCyan)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "RESOLVING DNS / IP BLOCK LOCATION...",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = CyberCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                is GeoIpUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                            .background(CyberNavy.copy(alpha = 0.3f))
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                text = "CORE LOCATION COORDINATES",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = CyberGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                CoordinateField(label = "LATITUDE", value = state.parsed.lat.toString(), modifier = Modifier.weight(1f))
                                CoordinateField(label = "LONGITUDE", value = state.parsed.lon.toString(), modifier = Modifier.weight(1f))
                            }
                        }

                        item {
                            HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 4.dp))
                        }

                        item {
                            Text(
                                text = "GEOGRAPHIC REGISTRATION NETWORK INFO",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = CyberCyan,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        item {
                            DetailsRow(label = "RESOLVED QUERY IP", value = state.parsed.ip)
                        }
                        item {
                            DetailsRow(label = "COUNTRY / CODE", value = "${state.parsed.country} [${state.parsed.countryCode}]")
                        }
                        item {
                            DetailsRow(label = "REGION / STATE", value = state.parsed.region)
                        }
                        item {
                            DetailsRow(label = "CITY INFO", value = state.parsed.city)
                        }
                        item {
                            DetailsRow(label = "POSTAL CODE", value = state.parsed.zip.ifEmpty { "n/a" })
                        }
                        item {
                            DetailsRow(label = "TIMEZONE BLOC", value = state.parsed.timezone)
                        }
                        item {
                            DetailsRow(label = "ISP ASSIGNED", value = state.parsed.isp)
                        }
                        item {
                            DetailsRow(label = "ORGANIZATION", value = state.parsed.org)
                        }
                        item {
                            DetailsRow(label = "ROUTE (AS)", value = state.parsed.asInfo)
                        }
                    }
                }
                is GeoIpUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(BorderStroke(1.dp, CyberRed.copy(alpha = 0.5f)), RoundedCornerShape(4.dp))
                            .background(CyberRed.copy(alpha = 0.05f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Recon Error",
                                tint = CyberRed,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "RESOLUTION FAILED:",
                                color = CyberRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = state.message,
                                color = CyberWhite,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoordinateField(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberBlack),
        border = BorderStroke(1.dp, CyberBorder),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                color = CyberGray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = CyberGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailsRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = CyberGray,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = value,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = CyberWhite,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        HorizontalDivider(color = CyberBorder.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun UsernameScannerPane(viewModel: OsintViewModel) {
    val username by viewModel.usernameTarget.collectAsStateWithLifecycle()
    val scanState = viewModel.usernameScanState.collectAsStateWithLifecycle().value
    val results by viewModel.usernameResults.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "TARGET USERNAME CLUE AUDIT",
            color = CyberGray,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.updateUsernameTarget(it) },
                placeholder = {
                    Text(
                        "Clue username...",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = CyberGray
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("username_scan_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen.copy(alpha = 0.5f),
                    unfocusedBorderColor = CyberBorder,
                    focusedLabelColor = CyberGreen,
                    unfocusedContainerColor = Color(0xFF1B1D21),
                    focusedContainerColor = Color(0xFF1B1D21)
                ),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = CyberWhite
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Ascii
                ),
                keyboardActions = KeyboardActions(onSearch = { viewModel.startUsernameScan() })
            )

            if (scanState is UsernameScanUiState.Scanning) {
                Button(
                    onClick = { viewModel.cancelUsernameScan() },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberRed, contentColor = CyberWhite),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .width(88.dp)
                ) {
                    Text(
                        text = "ABORT",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Button(
                    onClick = { viewModel.startUsernameScan() },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberGreen, contentColor = CyberBlack),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .width(88.dp)
                        .testTag("username_scan_button")
                ) {
                    Text(
                        text = "SCAN",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Processing Progress bar
        if (scanState is UsernameScanUiState.Scanning) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberNavy.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "SCANNING: ${scanState.currentPlatform}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = CyberGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${(scanState.progress * 100).toInt()}%",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = CyberWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { scanState.progress },
                        color = CyberGreen,
                        trackColor = CyberBorder,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else if (scanState is UsernameScanUiState.Finished) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberNavy),
                border = BorderStroke(1.dp, CyberGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Scan Done",
                        tint = CyberGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "SCAN COMPLETED. STASH FOUND: ${scanState.claimedCount} VERIFIED ACCOUNTS CORRELATED.",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = CyberGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Results lists
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (results.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                        .background(CyberNavy.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "No scan logs present",
                            tint = CyberGray.copy(alpha = 0.3f),
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Enter a username to scan public platforms database...",
                            color = CyberGray,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                        .background(CyberBlack)
                        .padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(results) { res ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CyberNavy.copy(alpha = 0.4f)),
                            border = BorderStroke(1.dp, if (res.exists == true) CyberGreen.copy(alpha = 0.6f) else CyberBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = res.exists == true) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(res.profileUrl))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast
                                            .makeText(context, "Could not open profile URL link", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = res.platformName,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CyberWhite
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = res.profileUrl,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        color = CyberGray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                when (res.exists) {
                                    null -> {
                                        CircularProgressIndicator(
                                            color = CyberCyan,
                                            strokeWidth = 2.dp,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    true -> {
                                        Box(
                                            modifier = Modifier
                                                .background(CyberGreen.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                                .border(0.5.dp, CyberGreen, RoundedCornerShape(4.dp))
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "FOUND",
                                                color = CyberGreen,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 9.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                    }
                                    false -> {
                                        Text(
                                            text = "VACANT",
                                            color = CyberGray.copy(alpha = 0.6f),
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB SCREEN: IMAGE METADATA EXPLORER ---
@Composable
fun MetadataScreen(viewModel: OsintViewModel) {
    val imageUri by viewModel.metadataImageUri.collectAsStateWithLifecycle()
    val exifData by viewModel.exifData.collectAsStateWithLifecycle()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.analyzeLocalImageExif(it) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "EXIF METADATA FORENSIC LOADER",
                color = CyberGray,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberNavy),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Camera Loader",
                        tint = CyberCyan,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Verify metadata traces such as camera details, capture software, date-timestamps, GPS locations stashed in local images.",
                        color = CyberWhite.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberBlack),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("pick_image_button")
                    ) {
                        Text(
                            text = "CHOOSE EVIDENCE IMAGE",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    if (imageUri != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        TextButton(
                            onClick = { viewModel.clearMetadataState() },
                            colors = ButtonDefaults.textButtonColors(contentColor = CyberRed)
                        ) {
                            Text(
                                text = "CLEAR LOCAL PICKS",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        if (imageUri == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Awaiting chosen file to extract traces...",
                        color = CyberGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        } else if (exifData != null) {
            val exif = exifData!!
            if (!exif.hasExif) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberRed.copy(alpha = 0.05f)),
                        border = BorderStroke(1.dp, CyberRed.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = "No details", tint = CyberRed)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "No EXIF details metadata found inside this image. Details may have been stripped by messaging apps.",
                                color = CyberWhite,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "EXTRACTED IMAGE DATA AUDIT",
                        color = CyberGreen,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberNavy.copy(alpha = 0.5f)),
                        border = BorderStroke(1.dp, CyberBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            exif.deviceMake?.let { DetailsRow("DEVICE MAKE", it) }
                            exif.deviceModel?.let { DetailsRow("DEVICE MODEL", it) }
                            exif.dateTimeTaken?.let { DetailsRow("DATE DIGITIZED", it) }
                            exif.width?.let { DetailsRow("IMAGE RESOLUTION", "${exif.width} x ${exif.height}") }
                            exif.software?.let { DetailsRow("SOFTWARE INSTANCE", it) }
                            exif.iso?.let { DetailsRow("ISO SENSITIVITY", it) }
                            exif.aperture?.let { DetailsRow("F-NUMBER (APERTURE)", "f/$it") }
                            exif.exposureTime?.let { DetailsRow("EXPOSURE DURATION", "$it sec") }
                            exif.focalLength?.let { DetailsRow("FOCAL LENGTH", "$it mm") }
                            exif.flashStatus?.let { DetailsRow("FLASH REGISTER", it) }
                        }
                    }
                }

                // If GPS present, show simulated Coordinates map indicator
                if (exif.latitude != null && exif.longitude != null) {
                    item {
                        Text(
                            text = "STASHED GEOLOCATION COORDINATES FOUND",
                            color = CyberGreen,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CyberBlack),
                            border = BorderStroke(1.dp, CyberGreen),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("LATITUDE: ${exif.latitude}", color = CyberWhite, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                                        Text("LONGITUDE: ${exif.longitude}", color = CyberWhite, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                                    }
                                    val context = LocalContext.current
                                    IconButton(
                                        onClick = {
                                            try {
                                                val uri = "geo:${exif.latitude},${exif.longitude}?q=${exif.latitude},${exif.longitude}(Investigated Target)"
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "No maps application installed", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier
                                            .background(CyberGreen.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                            .border(1.dp, CyberGreen, RoundedCornerShape(4.dp))
                                            .size(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Search coordinates on external map",
                                            tint = CyberGreen
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Simulated Radar geolocator compass indicator of extreme visual quality
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .border(0.5.dp, CyberBorder, RoundedCornerShape(8.dp))
                                        .background(CyberNavy.copy(alpha = 0.3f))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Canvas(modifier = Modifier.size(80.dp)) {
                                        val centerOffset = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)
                                        drawCircle(
                                            color = CyberBorder,
                                            style = Stroke(width = 1f)
                                        )
                                        drawCircle(
                                            color = CyberGreen.copy(alpha = 0.15f),
                                            style = Stroke(width = 0.5f)
                                        )
                                        // Compass needle path
                                        drawLine(
                                            color = CyberGreen,
                                            start = centerOffset,
                                            end = androidx.compose.ui.geometry.Offset(centerOffset.x, centerOffset.y - 35.dp.toPx()),
                                            strokeWidth = 2f
                                        )
                                        // Glint targeting cross
                                        drawCircle(
                                            color = CyberGreen,
                                            radius = 3.dp.toPx(),
                                            center = centerOffset
                                        )
                                    }
                                    Text(
                                        text = "GPS LOCK ACTIVE",
                                        color = CyberGreen,
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.align(Alignment.BottomCenter)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB SCREEN: GOOGLE DORK BUILDER & BOOKMARKS ---
@Composable
fun DorkBuilderScreen(viewModel: OsintViewModel) {
    val dorkCategory by viewModel.dorkCategory.collectAsStateWithLifecycle()
    val dorkDomain by viewModel.dorkDomain.collectAsStateWithLifecycle()
    val dorkKeyword by viewModel.dorkKeyword.collectAsStateWithLifecycle()
    val dorkExtension by viewModel.dorkExtension.collectAsStateWithLifecycle()
    
    val savedDorks by viewModel.allSavedDorks.collectAsStateWithLifecycle()
    
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    
    val generatedQuery = remember(dorkCategory, dorkDomain, dorkKeyword, dorkExtension) {
        viewModel.buildGoogleDorkQuery()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "INTERACTIVE GOOGLE DORKING GENERATOR",
                color = CyberGray,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberNavy),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Category dropdown row
                    Text(
                        text = "AUDIT SECTOR",
                        color = CyberGray,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )

                    val sectors = listOf("Sensitive Files", "Directory Listing", "Login Portals", "Vulnerability Scan", "Email Harvesting")
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        sectors.forEach { sec ->
                            val active = dorkCategory == sec
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (active) CyberCyan else CyberBorder.copy(alpha = 0.3f))
                                    .border(1.dp, if (active) CyberCyan else CyberBorder, RoundedCornerShape(4.dp))
                                    .clickable { viewModel.updateDorkCategory(sec) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = sec,
                                    fontSize = 11.sp,
                                    color = if (active) CyberBlack else CyberWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = CyberBorder, modifier = Modifier.padding(vertical = 2.dp))

                    // Text fields modifiers
                    OutlinedTextField(
                        value = dorkDomain,
                        onValueChange = { viewModel.updateDorkDomain(it) },
                        label = { Text("Target Domain (Optional, e.g. target.com)", fontSize = 11.sp, fontFamily = FontFamily.Monospace) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberCyan.copy(alpha = 0.5f),
                            unfocusedBorderColor = CyberBorder,
                            focusedLabelColor = CyberCyan,
                            unfocusedLabelColor = CyberGray,
                            unfocusedContainerColor = Color(0xFF1B1D21),
                            focusedContainerColor = Color(0xFF1B1D21)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = CyberWhite),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = dorkKeyword,
                        onValueChange = { viewModel.updateDorkKeyword(it) },
                        label = { Text("Core Clue Keyword (e.g. secret, admin, bypass)", fontSize = 11.sp, fontFamily = FontFamily.Monospace) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberCyan.copy(alpha = 0.5f),
                            unfocusedBorderColor = CyberBorder,
                            focusedLabelColor = CyberCyan,
                            unfocusedLabelColor = CyberGray,
                            unfocusedContainerColor = Color(0xFF1B1D21),
                            focusedContainerColor = Color(0xFF1B1D21)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = CyberWhite),
                        singleLine = true
                    )

                    if (dorkCategory == "Sensitive Files") {
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = dorkExtension,
                            onValueChange = { viewModel.updateDorkExtension(it) },
                            label = { Text("File Extension (pdf, xls, doc, txt, log, etc.)", fontSize = 11.sp, fontFamily = FontFamily.Monospace) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CyberCyan.copy(alpha = 0.5f),
                                unfocusedBorderColor = CyberBorder,
                                focusedLabelColor = CyberCyan,
                                unfocusedLabelColor = CyberGray,
                                unfocusedContainerColor = Color(0xFF1B1D21),
                                focusedContainerColor = Color(0xFF1B1D21)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = CyberWhite),
                            singleLine = true
                        )
                    }
                }
            }
        }

        // Output Preview Panel
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberBlack),
                border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CONSTRUCTED DORK SYNTAX:",
                            color = CyberCyan,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { viewModel.saveConstructedDork() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Bookmark query",
                                tint = CyberGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CyberNavy)
                            .border(0.5.dp, CyberBorder)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = generatedQuery,
                            color = CyberWhite,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(generatedQuery))
                                Toast.makeText(context, "Dork copied to clipboard buffer.", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberNavy, contentColor = CyberWhite),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, CyberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("copy_dork_button")
                        ) {
                            Text(
                                "COPY TEXT",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                try {
                                    val escapedQuery = Uri.encode(generatedQuery)
                                    val url = "https://www.google.com/search?q=$escapedQuery"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                } catch (e: java.lang.Exception) {
                                    Toast.makeText(context, "Error launching browser", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberBlack),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("execute_dork_button")
                        ) {
                            Text(
                                "EXECUTE SEARCH",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Bookmark Header
        item {
            Text(
                text = "SAVED BOOKMARKS / STASHED QUERY COPIES",
                color = CyberGray,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        if (savedDorks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No saved bookmarks. Click bookmark icons to stowed.",
                        color = CyberGray,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        } else {
            items(savedDorks) { dork ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberNavy.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .background(CyberCyan.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = dork.category,
                                        fontSize = 8.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = CyberCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = dork.description,
                                    color = CyberWhite,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(dork.query))
                                        Toast.makeText(context, "Dork copied directly.", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Copy stashed query",
                                        tint = CyberCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.deleteSavedDork(dork.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove bookmark",
                                        tint = CyberRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CyberBlack)
                                .border(0.5.dp, CyberBorder)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = dork.query,
                                color = CyberWhite.copy(alpha = 0.8f),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}



// --- SUB SCREEN: OSINT FRAMEWORK EXPLORER ---
@Composable
fun OsintFrameworkScreen(viewModel: OsintViewModel) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    // Prepare filter options
    val filterCategories = remember { listOf("All") + OsintFrameworkData.categories }
    
    // Filter the 100+ tools
    val filteredTools = remember(searchQuery, selectedCategory) {
        OsintFrameworkData.toolsList.filter { tool ->
            val matchCategory = selectedCategory == "All" || tool.category == selectedCategory
            val matchSearch = searchQuery.trim().isEmpty() || 
                    tool.name.contains(searchQuery, ignoreCase = true) ||
                    tool.description.contains(searchQuery, ignoreCase = true) ||
                    tool.tip.contains(searchQuery, ignoreCase = true) ||
                    tool.category.contains(searchQuery, ignoreCase = true)
            matchCategory && matchSearch
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Author & Site Info Badge
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberNavy),
            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = CyberCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "UTS-OSINT ENCYCLOPEDIA",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A definitive offline registry containing ${OsintFrameworkData.toolsList.size} premium, verified open source intelligence tools categorized based on standard auditing domains. Curated by Toprak Ahmet Aydoğmuş.",
                    color = CyberWhite.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        // Search Bar styled elegantly
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { 
                Text(
                    "Search 100+ OSINT tools...", 
                    fontFamily = FontFamily.Monospace, 
                    fontSize = 11.sp, 
                    color = CyberGray
                ) 
            },
            leadingIcon = { 
                Icon(
                    imageVector = Icons.Default.Search, 
                    contentDescription = "Search icon", 
                    tint = CyberCyan,
                    modifier = Modifier.size(18.dp)
                ) 
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear, 
                            contentDescription = "Clear search",
                            tint = CyberGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().testTag("framework_search_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberCyan.copy(alpha = 0.6f),
                unfocusedBorderColor = CyberBorder,
                focusedLabelColor = CyberCyan,
                unfocusedContainerColor = Color(0xFF1B1D21),
                focusedContainerColor = Color(0xFF1B1D21),
                focusedTextColor = CyberWhite,
                unfocusedTextColor = CyberWhite
            ),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
            singleLine = true
        )

        // Horizontal Category Badge Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filterCategories) { cat ->
                val isSelected = selectedCategory == cat
                val cleanName = cat.substringBefore(" (") // shorten name on bubble
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) CyberCyan else Color(0xFF14161A))
                        .border(
                            width = 1.dp,
                            color = if (isSelected) CyberCyan else CyberBorder,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = if (cat == "All") "🗂️ HEPSİ" else cleanName,
                        color = if (isSelected) CyberBlack else CyberWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }

        // Tools Match Metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MATCHED: ${filteredTools.size} / ${OsintFrameworkData.toolsList.size} TOOLS",
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CyberGray,
                letterSpacing = 1.sp
            )
            
            if (searchQuery.isNotEmpty() || selectedCategory != "All") {
                TextButton(
                    onClick = {
                        searchQuery = ""
                        selectedCategory = "All"
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = CyberCyan)
                ) {
                    Text(
                        "RESET FILTERS",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Primary Scrollable list of matched tools
        if (filteredTools.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "No results",
                        tint = CyberOrange,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Aradığınız kriterlere uygun araç bulunamadı.",
                        color = CyberWhite.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Try clearing search keywords or choosing another category.",
                        color = CyberGray,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredTools) { tool ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberNavy),
                        border = BorderStroke(1.dp, CyberBorder),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Header Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = tool.name,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = CyberWhite
                                )
                                
                                // Mini Category tag
                                Box(
                                    modifier = Modifier
                                        .background(CyberCyan.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                                        .border(0.5.dp, CyberCyan.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = tool.category.substringBefore(" (").uppercase(),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CyberCyan
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(6.dp))
                            
                            // Domain URL text
                            Text(
                                text = "Source: ${tool.url.substringAfter("https://").substringBefore("/")}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = CyberGray
                            )
                            
                            Spacer(modifier = Modifier.height(6.dp))
                            
                            // Description text
                            Text(
                                text = tool.description,
                                fontSize = 11.5.sp,
                                color = CyberWhite.copy(alpha = 0.85f),
                                lineHeight = 16.sp
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Expert Analysis Tip
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF0F1115), RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "💡 ANALYST TIP: ",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CyberGreen
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tool.tip,
                                    fontSize = 10.sp,
                                    color = CyberGray,
                                    lineHeight = 13.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(10.dp))
                            
                            // Launch and Copy Actions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Copy Link action
                                OutlinedButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(tool.url))
                                        Toast.makeText(context, "${tool.name} link copied to clipboard.", Toast.LENGTH_SHORT).show()
                                        
                                        // Save standard audit to history
                                        viewModelScopeLaunch(viewModel) {
                                            viewModel.saveCustomAuditLog(tool.name, "FRAMEWORK_COPY", tool.url)
                                        }
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberWhite),
                                    border = BorderStroke(1.dp, CyberBorder),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).height(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Copy Link",
                                        tint = CyberWhite,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Linki Kopyala", fontSize = 11.sp, fontFamily = FontFamily.SansSerif)
                                }
                                
                                // Launch Browser action
                                Button(
                                    onClick = {
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tool.url))
                                            context.startActivity(intent)
                                            
                                            // Save standard audit to history
                                            viewModelScopeLaunch(viewModel) {
                                                viewModel.saveCustomAuditLog(tool.name, "FRAMEWORK_LAUNCH", tool.url)
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Buse browser agent could not be launched.", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberBlack),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1.2f).height(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Launch",
                                        tint = CyberBlack,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Aracı Başlat", fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Simple coroutine launch helper for composables bypassing direct viewModelScope issues
private fun viewModelScopeLaunch(viewModel: OsintViewModel, block: suspend () -> Unit) {
    // We can run via a wrapper in viewModel or run directly.
    viewModel.executeCoroutineInScope(block)
}
