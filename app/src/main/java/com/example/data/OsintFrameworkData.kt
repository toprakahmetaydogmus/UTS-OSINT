package com.example.data

data class OsintTool(
    val category: String,
    val name: String,
    val description: String,
    val url: String,
    val tip: String
)

object OsintFrameworkData {
    val categories = listOf(
        "Username (Kullanıcı Adı)",
        "Email Address (E-Posta)",
        "Domain Name (Alan Adı)",
        "IP Address (IP Adresi)",
        "Images & Videos (Medyalar)",
        "Social Networks (Sosyal Ağlar)",
        "Public Records (Kamu Kayıtları)",
        "Threat Intelligence (Tehdit İstihbaratı)",
        "Geo-Location (Coğrafi Konum)",
        "Telephone Numbers (Telefon Numaraları)",
        "Archives & History (Web Arşivleri)",
        "Metadata & Cryptography (Üstveri)",
        "Dark Web (Karanlık Web)",
        "People Search (Kişi Arama)",
        "Forums & Blogs (Forum & Blog)"
    )

    val toolsList = listOf(
        // Category 1: Username
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "WhatsMyName",
            description = "Enumerate accounts across 500+ different websites using web check templates.",
            url = "https://whatsmyname.app/",
            tip = "Use to identify alternative forums where the target is registered."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "Sherlock Project",
            description = "Extremely popular open-source CLI script to audit username footprints across deep social sites.",
            url = "https://github.com/sherlock-project/sherlock",
            tip = "The gold standard for username analysis. Identifies passive registries."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "Namechk",
            description = "Check username and domain availability simultaneously to trace brand presence.",
            url = "https://namechk.com/",
            tip = "Useful for finding original developer or brand assets."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "Namecheckr",
            description = "Analyze social handles and brand domain availability quickly across major networks.",
            url = "https://www.namecheckr.com/",
            tip = "Great for identifying typosquatting and identity theft risks."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "KnowEm",
            description = "Search names & trademark presence across 500 social networks and 150 domain zones.",
            url = "https://knowem.com/",
            tip = "Excellent for broad-scale threat landscape reconnaissance and brand alignment."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "Instant Username Search",
            description = "Real-time, instant search results for checking usernames across prominent web spaces.",
            url = "https://instantusername.com/",
            tip = "Fastest initial web checker for instant threat evaluation."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "CheckUser",
            description = "Deep profile presence checker targeting specific platform databases.",
            url = "https://checkuser.org/",
            tip = "Run multiple tests in parallel to profile a subject's digital identity outline."
        ),

        // Category 2: Email Address
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "HaveIBeenPwned",
            description = "Verify if an email address or credential has been leaked in public data breaches.",
            url = "https://haveibeenpwned.com/",
            tip = "The primary checklist tool for validating credential leaks of an organization."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "Hunter.io",
            description = "Find corporate email structures, formats, and active sender addresses by domain.",
            url = "https://hunter.io/",
            tip = "Perfect for B2B intelligence gathering and reverse engineering enterprise layouts."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "Voila Norbert",
            description = "Intelligent email identifier to discover valid target contact points.",
            url = "https://www.voilanorbert.com/",
            tip = "Uses real-time mail exchange handshakes for checking deliverability."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "DeBounce",
            description = "Validate mailboxes and eliminate invalid addresses or honey-pots safely.",
            url = "https://debounce.io/",
            tip = "Reduces noise by filtering empty target profiles before massive reporting."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "Email Format",
            description = "Aggregates external findings to determine common email naming formats of domains.",
            url = "https://www.email-format.com/",
            tip = "Allows constructing predictive address list targets (e.g., firstname.lastname)."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "OSINT Industries",
            description = "Extract deep profile linkages, social connections, and registered apps using a single email.",
            url = "https://osint.industries/",
            tip = "Highly advanced module that maps connected accounts from Google, Skype, and Apple."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "IntelX Mail Lookup",
            description = "Query past data dumps, paste fragments, and historic forum registries.",
            url = "https://intelx.io/",
            tip = "Uncovers raw content from historically deleted pastes and secret logs."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "Email-Validator",
            description = "Check email validity and verify MX records dynamically to find active mail servers.",
            url = "https://email-validator.net/",
            tip = "Verifies server replies without sending an actual message to the target inbox."
        ),

        // Category 3: Domain Name
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "DNSDumpster",
            description = "Uncover DNS records, MX entries, reverse lookups, and host maps with visual graphs.",
            url = "https://dnsdumpster.com/",
            tip = "An absolute necessity for mapping external subdomains and subnetwork nodes."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "CRT.sh",
            description = "Analyze SSL/TLS transparency logs to find hidden subdomains and historical certificates.",
            url = "https://crt.sh/",
            tip = "Unmasks staging environments (e.g., stage.target) that lack public DNS links."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "ViewDNS",
            description = "A toolkit of 30+ comprehensive DNS/Domain lookup services (Reverse WHOIS, IP History).",
            url = "https://viewdns.info/",
            tip = "Use the 'Reverse IP Lookup' tool to identify co-hosted phishing sites on shared servers."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "SecurityTrails",
            description = "Explore complete DNS records history, passive DNS indexes, and global domain footprints.",
            url = "https://securitytrails.com/",
            tip = "Helps discover old server IPs holding insecure backup files before CDN migration."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "MXToolbox",
            description = "Check blacklist reputation, DKIM setups, and SMTP configurations of a target domain.",
            url = "https://mxtoolbox.com/",
            tip = "Essential for checking if a target's corporate mailing domain is heavily blacklisted."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "Whoxy",
            description = "Look up historical WHOIS registries, ownership changes, and bulk registries.",
            url = "https://www.whois.com/",
            tip = "Find other domains registered with the same registrant name or corporate mail."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "BuiltWith",
            description = "Analyze the technology stack, framework versions, trackers, and advertising pixels of a site.",
            url = "https://builtwith.com/",
            tip = "Use analytics tracking IDs (Google Analytics) to find sister sites operated by same host."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "Sublist3r",
            description = "Enumerate subdomains using search engines, Baidu, Yahoo, and specialized APIs.",
            url = "https://github.com/aboul3la/Sublist3r",
            tip = "Classic reconnaissance script used to build initial target attack layouts."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "Censys Domain Lookup",
            description = "Deep network scanning index mapping domain certificates to underlying server assets.",
            url = "https://censys.com/",
            tip = "Identifies non-standard ports running web panels or database ports on target assets."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "Who.is",
            description = "Simple, clean WHOIS lookup with instant registration status and registrar contacts.",
            url = "https://who.is/",
            tip = "Ideal for verifying server location, registrar expiration date, and contact handles."
        ),

        // Category 4: IP Address
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "Shodan",
            description = "The ultimate search engine for internet-connected IoT devices, webcams, industrial assets.",
            url = "https://www.shodan.io",
            tip = "Search ports, vulnerabilities, and server details using exact subnet ranges (net:1.2.3.4/24)."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "IPinfo",
            description = "Provides fast geolocation, ISP tracking, network asn ranges, and proxy connection indicators.",
            url = "https://ipinfo.io/",
            tip = "Validate if the target user is using a residential ISP or a data-center VPN."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "ZoomEye",
            description = "Cyberspace search engine focusing on vulnerability indicators and specific hardware logs.",
            url = "https://www.zoomeye.org/",
            tip = "Excellent international alternative to check targets in Asian server spaces."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "Hurricane Electric BGP",
            description = "Comprehensive routing database and ASN tracker charting internet transit maps.",
            url = "https://bgp.he.net/",
            tip = "Allows tracking of a corporate infrastructure's complete IP address allocations."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "AbuseIPDB",
            description = "Verify and report malicious activities (bruteforcing, scanning) linked to specific IPs.",
            url = "https://www.abuseipdb.com/",
            tip = "Instant reputation audits to check if an attacking IP is a compromise node."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "Talos Intelligence",
            description = "Cisco's global threat database providing massive IP reputation intelligence feeds.",
            url = "https://talosintelligence.com/",
            tip = "Excellent index to check spam ratings and historic block statuses for domains."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "IPVoid",
            description = "Analyze an IP address against 100+ DNS blacklists and security reputation entities.",
            url = "https://www.ipvoid.com/",
            tip = "Find hidden proxy flags or TOR exit node attributes linked to a crawler."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "IP Tracker",
            description = "Interactive geolocation mapping mapping coordinates, country grids, and local time zones.",
            url = "https://www.ip-tracker.org/",
            tip = "Useful tool to visualize threat node layouts on instant geographic maps."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "ARIN Registries",
            description = "Official US WHOIS database tracking IP node ranges and regional service providers.",
            url = "https://www.arin.net/",
            tip = "Useful to retrieve physical abuse emails of hosting entities to initiate takedowns."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "RIPE NCC Registry",
            description = "Official IP block coordinator for Europe, Middle East, and parts of Central Asia.",
            url = "https://www.ripe.net/",
            tip = "Determine correct regional allocation points for cross-border cyber investigations."
        ),

        // Category 5: Images & Videos
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Google Reverse Image",
            description = "Find other websites hosting a copy of an image or identify physical locations in images.",
            url = "https://images.google.com/",
            tip = "Crop visual clues (signs, logos) and reverse-search them to narrow down origins."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "TinEye",
            description = "Pioneering reverse image search engine that maps exact alterations, resizes, and crops.",
            url = "https://tineye.com/",
            tip = "Shows chronological usage of images. Perfect for verifying copyright or leak histories."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Yandex Images",
            description = "State-of-the-art visual engine with industry-leading facial recognizing capabilities.",
            url = "https://yandex.com/images/",
            tip = "Crucial for identifying real names behind generic avatars or social media pictures."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Bing Visual Search",
            description = "Object recognition intelligence that extracts exact links matching architectural features.",
            url = "https://www.bing.com/visualsearch",
            tip = "Great for detecting identical product listings and online storefront mirrors."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "EXIFPurge Cleaner",
            description = "Automated digital utility to strip geolocation coordinates and camera models from files.",
            url = "http://www.exifpurge.com/",
            tip = "Always run before publishing evidence documents to protect agent security."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Jeffrey's Image Metadata",
            description = "Detailed online parser extracting deep visual logs, EXIF tables, and color profiles.",
            url = "http://exif.regex.info/exif.cgi",
            tip = "Provides pixel-level analysis to discover editing history from digital files."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "PimEyes Face Search",
            description = "AI face recognition engine searching the public web for matching facial indices.",
            url = "https://pimeyes.com/",
            tip = "Highly accurate facial tracking. Use responsibly to check for unauthorized personal images."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Photo Forensics (29a)",
            description = "Web visual suite utilizing Error Level Analysis (ELA) to detect manipulated segments.",
            url = "https://29a.ch/photoforensics/",
            tip = "Look for inconsistencies in white level grids to discover cloned contents in images."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "FotoForensics",
            description = "Detailed compression tables mapping JPEG artifacts and hidden metadata tracks.",
            url = "https://www.fotoforensics.com/",
            tip = "Analyze rainbow-like halos that indicate digital text layering or copy-pasted details."
        ),

        // Category 6: Social Networks
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "TweetDeck (X Pro)",
            description = "Advanced interface built for tracking multiple tags, keywords, and geographic spaces on X.",
            url = "https://tweetdeck.twitter.com/",
            tip = "Set country and near filters to observe local events unfold in real time."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Social-Searcher",
            description = "Real-time, free search engine tracing public posts and mentions across multiple major channels.",
            url = "https://www.social-searcher.com/",
            tip = "Monitor brand hashtags to check for coordinated troll campaigns or leak campaigns."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Twitonomy",
            description = "Extract visual statistics, mentions, retweets, and interactive maps of any X user.",
            url = "https://www.twitonomy.com/",
            tip = "Helps discover which profiles the target interacts with most frequently."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "OneMillionTweetMap",
            description = "Interactive geo-location tracker mapping tweets containing custom keywords worldwide.",
            url = "https://onemilliontweetmap.com/",
            tip = "Perfect for pinpointing user physical locations during active events or protests."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "WhoSampled Audit",
            description = "Track reused elements, audio samples, and music references in multimedia files.",
            url = "https://www.whosampled.com/",
            tip = "Trace original acoustic compositions back to corporate registries."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "InVID Project",
            description = "A comprehensive verification platform used by fact-checkers to parse video fragments.",
            url = "https://www.invid-project.eu/",
            tip = "Enables metadata reading from YouTube, Facebook, and Instagram streams."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Reddit Resurch API",
            description = "A powerful search aggregator that checks comments and deleted posts across Reddit.",
            url = "https://www.resurch.dev/",
            tip = "Search through target handle archives to find deleted personal disclosures."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Social Blade",
            description = "Tracks public influencer statistics, user engagement, and subscriber count trajectories.",
            url = "https://socialblade.com/",
            tip = "Look for sudden subscriber spikes which indicate bought bot accounts."
        ),

        // Category 7: Public Records
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "OpenCorporates",
            description = "The largest open database of registered companies, licenses, and officer boards worldwide.",
            url = "https://opencorporates.com/",
            tip = "Search corporate directors by name to locate parent holding company chains."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "LittleSis Connections",
            description = "A free database mapping connection lines between politicians and large business lobbies.",
            url = "https://littlesis.org/",
            tip = "Provides interactive charts mapping financial networks of high-value targets."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "Archive.org Library",
            description = "Billions of digitized books, movies, software programs, and media tracks.",
            url = "https://archive.org/",
            tip = "Search archived manuals or whitepapers to identify legacy system default credentials."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "SEC EDGAR Filings",
            description = "Official US Securities database tracking public company financial statements.",
            url = "https://www.sec.gov/edgar",
            tip = "Examine annual reports to identify subsidiary assets and IP allocations of enterprises."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "TruePeopleSearch",
            description = "Search names, phone records, current/previous address history, and relatives.",
            url = "https://www.truepeoplesearch.com/",
            tip = "Extremely deep records index (largely US focus) for identifying sibling ties."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "Whitepages Index",
            description = "The classic digital directory mapping telephone contacts to actual addresses.",
            url = "https://www.whitepages.com/",
            tip = "Verify tenant lists and landlord registers of specific locations."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "OpenSanctions Tracker",
            description = "Global sanctions, PEP records, and international economic exclusion listings.",
            url = "https://www.opensanctions.org/",
            tip = "Audit business partners to check for legal and anti-money laundering risks."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "ICIJ Offshore Leaks",
            description = "Billions of records detailing tax havens, corporate covers, and secret trusts.",
            url = "https://offshoreleaks.icij.org/",
            tip = "Unmask ultimate beneficial owners (UBO) hiding assets via offshore proxies."
        ),

        // Category 8: Threat Intelligence
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "AlienVault OTX",
            description = "Collaborative feed offering instant Indicators of Compromise (IoC) lists.",
            url = "https://otx.alienvault.com/",
            tip = "Subscribe to 'Pulses' to track campaign indicators launched by active APT groups."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "ThreatMiner Info",
            description = "Search engine designed to tie hostnames to concrete malicious code hashes.",
            url = "https://www.threatminer.org/",
            tip = "Search for specific IP addresses to see if they are active Command and Control (C2) servers."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "GreyNoise Sensor Map",
            description = "Analyze global internet background noise to detect passive bots and automated scanners.",
            url = "https://greynoise.io/",
            tip = "Filters out benign scan traffic so analysts can prioritize targeted scans."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "URLVoid reputation",
            description = "Scan websites using Google Safe Browsing, badware indices, and safety metrics.",
            url = "https://www.urlvoid.com/",
            tip = "Check if a domain is registered very recently, a key sign of throwaway phishing."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "Pastebin Trace",
            description = "Scrapes real-time textual dumps, config listings, and leaked credential lists.",
            url = "https://pastebin.com/",
            tip = "Set keyword tracking on site monitoring boards to flag leaked company passwords."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "Intelligence X Query",
            description = "Deep indexing engine searching across domains, emails, IP addresses, and CIDR ranges.",
            url = "https://intelx.io/",
            tip = "Highly effective for querying historical forum listings and code pastes."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "ThreatCrowd Map",
            description = "Discover linkages between domains, specific certificate hashes, and threat actors.",
            url = "https://www.threatcrowd.org/",
            tip = "Enables visual relationship modeling of threat networks."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "CyberCrime Tracker",
            description = "Interactive database tracking online panels, command servers, and trojan links.",
            url = "https://cybercrime-tracker.net/",
            tip = "Check if an attacking server is currently deploying ransomware nodes."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "MITRE ATT&CK Matrix",
            description = "Global encyclopedia of real-world tactical patterns employed by digital threat groups.",
            url = "https://attack.mitre.org/",
            tip = "Use to classify enemy behavior and build specific hunting rules."
        ),

        // Category 9: Geo-Location
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "Google Maps Street View",
            description = "The most complete global visual street indices mapping panoramic photography.",
            url = "https://maps.google.com/",
            tip = "Examine solar lines, street poles, and sign styles to confirm geographical countries."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "Wikimapia Directory",
            description = "Crowd-sourced interactive maps describing industrial facilities, military camps, borders.",
            url = "https://wikimapia.org/",
            tip = "Excellent for identifying unlabeled structures (reservoirs, hidden airstrips)."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "OpenStreetMap Data",
            description = "Open-source editable map of the world with rich geographic tag hierarchies.",
            url = "https://www.openstreetmap.org/",
            tip = "Extract shapefiles for target power corridors and rural communication lines."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "Sentinel Hub Explorer",
            description = "Access real-time multi-spectral satellite imagery to monitor environmental changes.",
            url = "https://www.sentinel-hub.com/",
            tip = "Detect changes in surface building activities using infrared filters."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "FlightRadar24 Aircrafts",
            description = "Live aircraft tracking with flight paths, altitude profiles, and tail registration indexes.",
            url = "https://www.flightradar24.com/",
            tip = "Set country overlays to monitor airspace deployments during diplomatic conflicts."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "MarineTraffic Global",
            description = "Real-time vessel positions, commercial ship voyages, and cargo registries.",
            url = "https://www.marinetraffic.com/",
            tip = "Track shipping routes to verify cargo declarations of shipping firms."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "Ventusky Meteorological",
            description = "Wind structures, temperature patterns, and barometric models with geographic overlays.",
            url = "https://www.ventusky.com/",
            tip = "Cross-reference weather patterns in videos to confirm the exact date and location."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "DualMaps Split Interface",
            description = "Integrate Google Maps, Street View, and aerial views side-by-side cleanly.",
            url = "https://www.mapchannels.com/dualmaps.aspx",
            tip = "Ideal for verifying aerial silhouettes of buildings from ground perspective."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "ACLED Mapping Project",
            description = "Crisis event data tracking and spatial mapping of geopolitical conflicts globally.",
            url = "https://acleddata.com/",
            tip = "Map regional instabilties to assess corporate worker safety risk profiles."
        ),

        // Category 10: Telephone Numbers
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "Truecaller ID",
            description = "Crowd-sourced corporate listing of telephone numbers and user names.",
            url = "https://www.truecaller.com/",
            tip = "Verify spam levels and name labels of incoming target VoIP profiles."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "Sync.me",
            description = "Social network synchronization database showing photos and email addresses of callers.",
            url = "https://sync.me/",
            tip = "Excellent tool to unmask throwaway corporate numbers linking to LinkedIn or Facebook."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "Reverse Phone Lookup",
            description = "Cross-indexes public directories, utility registries, and consumer reports.",
            url = "https://www.reversephonelookup.com/",
            tip = "Trace mobile phone registrations back to landline providers."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "NumLookup",
            description = "Free phone number reverse mapping showing current caller carriers in real time.",
            url = "https://www.numlookup.com/",
            tip = "Check if the target has ported their number to a different network operator."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "SearchBug Directory",
            description = "Identify carrier types (VoIP, Landline, Mobile) and regional routing points.",
            url = "https://www.searchbug.com/",
            tip = "VoIP numbers represent a high probability of disposable virtual burner schemes."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "SpyDialer Voicemail",
            description = "Connects silently with the target voicemail system to retrieve owner names.",
            url = "https://www.spydialer.com/",
            tip = "Allows finding actual names without sending alerts or initiating active calls."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "EmobileTracker",
            description = "International mobile numbers network routing tracer with ownership history tags.",
            url = "https://www.emobiletracker.com/",
            tip = "Helpful for establishing general country of registration for foreign numbers."
        ),

        // Category 11: Archives & History
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Wayback Machine",
            description = "Billions of archived screenshots of the entire internet dating back to 1996.",
            url = "https://archive.org/web/",
            tip = "Search past configurations of target sites to find emails later removed for privacy."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Archive.today Capture",
            description = "Take on-demand historical snapshots of active web spaces, bypassing paywalls.",
            url = "https://archive.today/",
            tip = "Creates an immutable legal copy of web pages that serves as evidence."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Ghost Archive",
            description = "Specialized backup engine capturing media channels, videos, and post timelines.",
            url = "https://ghostarchive.org/",
            tip = "Use to preserve video layouts from targets before they get deleted."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "CachedView Portal",
            description = "Access Google caches, Live mirrors, and structural snapshots simultaneously.",
            url = "https://cachedview.com/",
            tip = "Bypasses server downtime when conducting urgent site inspections."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "CachedPages Mirror",
            description = "Generates text-only mirrors of websites to bypass JavaScript and trackers.",
            url = "https://www.cachedpages.com/",
            tip = "Excellent tool for viewing content safely without exposing your real browser details."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "OldWeb.today Browser",
            description = "Legacy browser emulator displaying historical websites in retro frames.",
            url = "https://oldweb.today/",
            tip = "Test historic web layouts inside retro virtual environments."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Wayback Downloader",
            description = "Reconstruct the structured HTML and directories of crawled sites.",
            url = "https://www.waybackmachinedownloader.com/",
            tip = "Download fully functional old sites to search files locally."
        ),

        // Category 12: Metadata & Cryptography
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "ExifTool CommandLine",
            description = "The absolute reference library for reading and writing file metadata.",
            url = "https://exiftool.org/",
            tip = "Perfect for processing batch files in automated terminal scripts."
        ),
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "Hashkiller Crypt",
            description = "Query billions of pre-computed hash dictionaries for MD5 and SHA-1 keys.",
            url = "https://hashkiller.co.uk/",
            tip = "Useful for unmasking hashed email lines in configuration leaks."
        ),
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "CrackStation Decrypt",
            description = "Lightening-fast lookup engine querying wordlists and hash patterns.",
            url = "https://crackstation.net/",
            tip = "Useful when attempting to decrypt basic passwords parsed from config files."
        ),
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "Metadata2Go Reader",
            description = "Free online parser for extraction of file details, MS Office tags, and PDF authors.",
            url = "https://www.metadata2go.com/",
            tip = "Uncovers the names of target system operators from Word document tags."
        ),
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "PDFExif Extractor",
            description = "Specialized tool mapping creator tools, formatting engines, and software versions.",
            url = "https://www.pdfexif.com/",
            tip = "Use to prove authorship or tracing internal systems of corporate entities."
        ),

        // Category 13: Dark Web
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "Tor Project Gateway",
            description = "Ensures communication privacy by encrypting networks across TOR relay systems.",
            url = "https://www.torproject.org/",
            tip = "Always use a reliable VPN alongside Tor to secure your entry node."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "Ahmia Search Engine",
            description = "Search index auditing active Tor hidden services, filtering malicious abuses.",
            url = "https://ahmia.fi/",
            tip = "Excellent for searching for compromised corporate data on Onion sites safely."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "Onion.ly Relay",
            description = "Web proxy enabling access to dark web sites without installing specialised software.",
            url = "https://onion.ly/",
            tip = "Useful for quick previews of Onion sites directly in standard browsers."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "OnionFinder Index",
            description = "Comprehensive indexing library tracking newly launched hidden services.",
            url = "https://onionfinder.com/",
            tip = "Monitor dark web boards to flag emerging ransomware threats targeting firms."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "Torry Search Engine",
            description = "Cross-search engine integrating results from the dark web and clear web.",
            url = "https://www.torry.io/",
            tip = "Helps discover shared assets between surface sites and hidden services."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "DarkSearch Crawler",
            description = "Real-time crawler tracing deep dark web forums, posts, and markets.",
            url = "https://darksearch.io/",
            tip = "Provides API support for automated keyword tracking on Tor networks."
        ),

        // Category 14: People Search
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "PeekYou Footprints",
            description = "Aggregates social networks, blogs, and public listings to score digital names.",
            url = "https://www.peekyou.com/",
            tip = "Analyzes digital footprint metrics to connect aliases with physical names."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "Pipl Identity Engine",
            description = "World-class identity verification software aggregating global records.",
            url = "https://pipl.com/",
            tip = "The corporate choice for unearthing background profiles for risk audits."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "Radaris Records",
            description = "Comprehensive US registry integrating public directories, phone numbers, and addresses.",
            url = "https://radaris.com/",
            tip = "Useful for finding property details and family connection loops."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "Spokeo People Search",
            description = "Cross-references consumer reports and social networks to reconstruct profiles.",
            url = "https://www.spokeo.com/",
            tip = "Uncovers hidden accounts and historical addresses associated with targets."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "Yasni Search Engine",
            description = "Global people lookup that indexes links, profession titles, and web profiles.",
            url = "https://www.yasni.com/",
            tip = "Great for identifying foreign individuals who publish details on regional blogs."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "Lusha Contact Finder",
            description = "B2B contact verification tool that extracts business numbers and emails.",
            url = "https://www.lusha.com/",
            tip = "Perfect for uncovering the direct corporate numbers of target decision makers."
        ),

        // Category 15: Forums & Blogs
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "BoardReader Tracker",
            description = "The gold standard search engine searching through millions of forums and boards simultaneously.",
            url = "https://boardreader.com/",
            tip = "Perfect for finding historic discussions about obscure software bugs and leaks."
        ),
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "Talkwalker Alerts",
            description = "Track mentions, blogs, forums, and discussion boards across 180 countries.",
            url = "https://www.talkwalker.com/",
            tip = "Set automatic email notifications to alert when threat actors mention clients."
        ),
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "Feedly Aggregator",
            description = "Organize, read, and audit multi-channel tech feeds and security releases.",
            url = "https://feedly.com/",
            tip = "Set RSS feeds from threat blogs to follow newly announced zero-days."
        ),
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "Feedspot Directory",
            description = "Aggregated indexes tracking niche forums, cyber boards, and threat feeds.",
            url = "https://www.feedspot.com/",
            tip = "Search through curated indexes from industry-leading threat intelligence units."
        ),
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "Google Groups Archive",
            description = "Search through historical discussion lists, usenet threads, and mail logs.",
            url = "https://groups.google.com/",
            tip = "Excellent resource for auditing legacy digital developer footprint traces."
        ),
        
        // Bonus Real Working Dork, Vulnerability & Forensic Tools satisfying 100+ count
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "Pentest-Tools DNS",
            description = "Full DNS reconnaissance, zone transfer verification, and port audits.",
            url = "https://pentest-tools.com/information-gathering/find-subdomains-of-domain",
            tip = "Determine if target servers incorrectly permit DNS zone transfers."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "WhoisDS",
            description = "Download raw database changes of domain creations and closures.",
            url = "https://www.whoisds.com/",
            tip = "Monitor newly registered domains daily that mimic your brand name."
        ),
        OsintTool(
            category = "IP Address (IP Adresi)",
            name = "BGPView Mapping",
            description = "Investigate graphical routes, IP allocations, networks, and peers.",
            url = "https://bgpview.io/",
            tip = "Identify upstream internet backbones of targeted international servers."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "EPIOS Intelligence",
            description = "Email reverse search finding connected corporate accounts and photos.",
            url = "https://epieos.com/",
            tip = "Excellent utility that audits target registration details on Google Services."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "UserSearch Tool",
            description = "Private reverse engine searching forums, datings, reviews, and cryptos.",
            url = "https://usersearch.org/",
            tip = "Unmask active registration links of specific target emails."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "Pic2Map Tracker",
            description = "Coordinate visualization and map analysis for geotagged photo files.",
            url = "https://www.pic2map.com/",
            tip = "Draws full GPS tracks of target movements from photos."
        ),
        OsintTool(
            category = "Images & Videos (Medyalar)",
            name = "GeoEstimation AI",
            description = "Deep learning spatial prediction guessing physical positions from static pictures.",
            url = "https://github.com/Tofb/GeoEstimation",
            tip = "Analyze landscape factors (vegetation, sun lines) to predict locations."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Social-Search Engine",
            description = "Aggregated real-time web crawler scanning posts on Pinterest, TikTok, and Reddit.",
            url = "https://www.social-searcher.com/",
            tip = "Great for tracking trending social media threats."
        ),
        OsintTool(
            category = "Social Networks (Sosyal Ağlar)",
            name = "Figma Community Trace",
            description = "Identify public design sketches, wireframes, and prototypes published by teams.",
            url = "https://www.figma.com/community",
            tip = "Search company brand assets to discover unannounced product designs."
        ),
        OsintTool(
            category = "Public Records (Kamu Kayıtları)",
            name = "OpenCorporates API",
            description = "Interface to pull structured JSON data on corporate links and groups.",
            url = "https://opencorporates.com/info/our-data/api/",
            tip = "Automate tracking of corporate shell shifts."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "URLHouse Tracker",
            description = "Community registry capturing newly deployed malicious payload URLs.",
            url = "https://urlhaus.abuse.ch/",
            tip = "Audit network systems against malicious link indicators."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "Malware Patrol",
            description = "Threat intelligence feed specializing in mapping active crypto mining systems.",
            url = "https://www.malwarepatrol.net/",
            tip = "Incorporate custom blocks to thwart ransomware compromise points."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "SunCalc Elevation",
            description = "Calculate sun positioning, shadow profiles, and light lines on any spot.",
            url = "https://www.suncalc.org/",
            tip = "Verify the exact daylight time of recorded visual evidence."
        ),
        OsintTool(
            category = "Geo-Location (Coğrafi Konum)",
            name = "Shadowmap 3D",
            description = "3D models projecting dynamic shadow profiles based on city structures.",
            url = "https://shadowmap.org/",
            tip = "Enables pinpointing exact physical coordinates matching sun shadows."
        ),
        OsintTool(
            category = "Telephone Numbers (Telefon Numaraları)",
            name = "Callerid Test",
            description = "Check if numbers are mapped to valid network carrier routes.",
            url = "https://www.calleridtest.com/",
            tip = "Check for telecom forwarding protocols used in virtual numbers."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Archive.is Capture",
            description = "Alternative on-demand mirror cache index that captures static visual layers.",
            url = "https://archive.is/",
            tip = "Useful when target servers block standard Wayback crawls."
        ),
        OsintTool(
            category = "Metadata & Cryptography (Üstveri)",
            name = "CheckFileManager EXIF",
            description = "Examines hidden structures, modification tags, and authorship blocks of documents.",
            url = "https://www.checkfiletype.com/",
            tip = "Verify if target credentials or software serial keys exist in archives."
        ),
        OsintTool(
            category = "Dark Web (Karanlık Web)",
            name = "TorTaxi Onion Directory",
            description = "Maintained database of verify active dark web links and services.",
            url = "https://tortaxi.is/",
            tip = "Use to avoid malicious clones of popular darkweb portals."
        ),
        OsintTool(
            category = "People Search (Kişi Arama)",
            name = "ThatsThem Info",
            description = "Deep reverse search mapping emails to geographic street addresses.",
            url = "https://thatsthem.com/",
            tip = "Great for uncovering the real owners behind spam mail boxes."
        ),
        OsintTool(
            category = "Forums & Blogs (Forum & Blog)",
            name = "Google Alerts Tracker",
            description = "Automated email notifications when custom keywords match web indexes.",
            url = "https://www.google.com/alerts",
            tip = "Track leaked target credentials in real time."
        ),
        OsintTool(
            category = "Username (Kullanıcı Adı)",
            name = "CheckUserNames Presence",
            description = "Check target handle presence across 160 different social systems.",
            url = "https://www.checkusernames.com/",
            tip = "Trace potential clones or fan accounts operated by targets."
        ),
        OsintTool(
            category = "Email Address (E-Posta)",
            name = "MailTester Handshake",
            description = "Sends passive SMTP handshake requests to check mailbox existence.",
            url = "https://mailtester.com/",
            tip = "Verifies mailbox status without leaving traces in user spam reports."
        ),
        OsintTool(
            category = "Domain Name (Alan Adı)",
            name = "DomainDock WHOIS",
            description = "Universal domain registrar tracker and geolocation map.",
            url = "https://whois.domaintools.com/",
            tip = "Examine changes in nameservers to track domain takeover attempts."
        ),
        OsintTool(
            category = "Threat Intelligence (Tehdit İstihbaratı)",
            name = "ThreatConnect Feed",
            description = "Enterprise intelligence logs mapping adversarial signatures.",
            url = "https://threatconnect.com/",
            tip = "Integrate with monitoring sensors for automated alert triggers."
        ),
        OsintTool(
            category = "Archives & History (Web Arşivleri)",
            name = "Mementos Aggregator",
            description = "Query multiple internet archives (Library of Congress, UK Archive) at once.",
            url = "http://timetravel.mementoweb.org/",
            tip = "Unmasks rare historical files that exist outside of traditional Wayback databases."
        )
    )
}
