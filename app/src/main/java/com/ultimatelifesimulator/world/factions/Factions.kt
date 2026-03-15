package com.ultimatelifesimulator.world.factions

data class Faction(
    val id: String,
    val name: String,
    val type: FactionType,
    val description: String,
    val power: Int = 50,
    val playerReputation: Int = 0,
    val ideology: String = "",
    val goals: List<String> = emptyList(),
    val enemies: List<String> = emptyList(),
    val allies: List<String> = emptyList(),
    val headquarters: String = "",
    val resources: Map<String, Int> = emptyMap(),
    val isHidden: Boolean = false,
    val leadership: String = ""
)

enum class FactionType(val displayName: String) {
    ROYAL("Royalty"),
    NOBLE("Noble House"),
    RELIGIOUS("Religious"),
    GOVERNMENT("Government"),
    MILITARY("Military"),
    POLITICAL("Political Party"),
    CRIMINAL("Criminal Organization"),
    BUSINESS("Business"),
    LABOR("Labor Union"),
    MEDIA("Media"),
    CIVIL_RIGHTS("Civil Rights"),
    SCIENCE("Scientific"),
    COMMUNITY("Community"),
    SUBFACTION("Sub-faction")
}

data class FactionRelationship(
    val factionId: String,
    val opinion: Int = 0,
    val trust: Int = 50,
    val allianceLevel: Int = 0,
    val warLevel: Int = 0,
    val tradeLevel: Int = 0,
    val lastInteraction: Long = System.currentTimeMillis()
)

object FactionRegistry {
    // Royalty Factions
    val royaltyFactions = listOf(
        Faction("royal_family", "The Royal Family", FactionType.ROYAL, "The ruling dynasty",
            power = 100, ideology = "Maintain throne", headquarters = "palace"),
        Faction("high_council", "High Council", FactionType.GOVERNMENT, "King's advisors",
            power = 80, ideology = "Advise crown", headquarters = "royal_court"),
        Faction("courtiers", "Courtiers", FactionType.NOBLE, "Royal court members",
            power = 40, ideology = "Social climbing", headquarters = "royal_court"),
        Faction("royal_guard", "Royal Guard", FactionType.MILITARY, "King's personal guard",
            power = 60, ideology = "Loyalty to crown", headquarters = "palace"),
        Faction("noble_houses", "Noble Houses", FactionType.NOBLE, "Aristocratic families",
            power = 70, ideology = "Various", headquarters = "noble_estate"),
        Faction("church_authoriy", "Religious Authority", FactionType.RELIGIOUS, "The established church",
            power = 75, ideology = "Faith", headquarters = "church"),
        Faction("merchant_guilds", "Merchant Guilds", FactionType.BUSINESS, "Trade organization",
            power = 60, ideology = "Commerce", headquarters = "marketplace"),
        Faction("peasant_commons", "Peasant Commons", FactionType.COMMUNITY, "Common folk",
            power = 20, ideology = "Survival", headquarters = "slums")
    )
    
    // Political Factions
    val politicalFactions = listOf(
        Faction("democrats", "Democratic Party", FactionType.POLITICAL, "Progressive politics",
            power = 50, ideology = "Democracy", headquarters = "government_building"),
        Faction("republicans", "Republican Party", FactionType.POLITICAL, "Conservative politics",
            power = 50, ideology = "Conservatism", headquarters = "government_building"),
        Faction("progressive_wing", "Progressive Wing", FactionType.POLITICAL, "Left-wing activists",
            power = 30, ideology = "Progressivism", headquarters = "city_hall"),
        Faction("moderate_wing", "Moderate Wing", FactionType.POLITICAL, "Centrist politicians",
            power = 35, ideology = "Centrism", headquarters = "government_building"),
        Faction("conservative_wing", "Conservative Wing", FactionType.POLITICAL, "Right-wing activists",
            power = 30, ideology = "Conservatism", headquarters = "government_building"),
        Faction("lobbying_group", "Corporate Lobby", FactionType.POLITICAL, "Business interests",
            power = 55, ideology = "Pro-business", headquarters = "office_building"),
        Faction("labor_union", "Labor Unions", FactionType.LABOR, "Worker organization",
            power = 45, ideology = "Workers rights", headquarters = "city_hall"),
        Faction("media_outlet", "Media Outlets", FactionType.MEDIA, "News organizations",
            power = 60, ideology = "Information", headquarters = "office_building"),
        Faction("environmental_group", "Environmental Groups", FactionType.CIVIL_RIGHTS, "Green advocacy",
            power = 35, ideology = "Environment", headquarters = "park"),
        Faction("gun_rights", "Gun Rights Groups", FactionType.POLITICAL, "2nd Amendment",
            power = 40, ideology = "Gun rights", headquarters = "construction_site"),
        Faction("gun_control", "Gun Control Groups", FactionType.POLITICAL, "Gun safety",
            power = 35, ideology = "Gun control", headquarters = "government_building"),
        Faction("civil_rights_org", "Civil Rights Organizations", FactionType.CIVIL_RIGHTS, "Equal rights",
            power = 45, ideology = "Equality", headquarters = "city_hall"),
        Faction("religious_right", "Religious Right", FactionType.POLITICAL, "Faith-based politics",
            power = 40, ideology = "Religious traditionalism", headquarters = "church"),
        Faction("teachers_union", "Teachers Union", FactionType.LABOR, "Education workers",
            power = 40, ideology = "Education", headquarters = "school_high"),
        Faction("police_union", "Police Union", FactionType.LABOR, "Law enforcement",
            power = 45, ideology = "Law and order", headquarters = "police_station"),
        Faction("healthcare_lobby", "Healthcare Lobby", FactionType.POLITICAL, "Medical industry",
            power = 50, ideology = "Healthcare", headquarters = "hospital"),
        Faction("pharma_lobby", "Pharmaceutical Lobby", FactionType.POLITICAL, "Drug companies",
            power = 45, ideology = "Pro-pharma", headquarters = "office_building"),
        Faction("defense_contractors", "Defense Contractors", FactionType.BUSINESS, "Military industry",
            power = 50, ideology = "National defense", headquarters = "military_base"),
        Faction("tech_industry", "Tech Industry", FactionType.BUSINESS, "Technology companies",
            power = 55, ideology = "Innovation", headquarters = "office_building")
    )
    
    // Criminal Factions
    val criminalFactions = listOf(
        Faction("street_gangs", "Street Gangs", FactionType.CRIMINAL, "Neighborhood criminals",
            power = 30, ideology = "Territory", headquarters = "slums"),
        Faction("mafia", "The Mafia", FactionType.CRIMINAL, "Organized crime syndicate",
            power = 70, ideology = "Power and profit", headquarters = "hideout"),
        Faction("cartels", "Drug Cartels", FactionType.CRIMINAL, "Drug trafficking",
            power = 65, ideology = "Drug trade", headquarters = "harbor"),
        Faction("russian_mob", "Russian Mob", FactionType.CRIMINAL, "Eastern European criminals",
            power = 55, ideology = "Crime", headquarters = "nightclub"),
        Faction("triads", "Triads", FactionType.CRIMINAL, "Asian crime syndicate",
            power = 60, ideology = "Various", headquarters = "restaurant"),
        Faction("yakuza", "Yakuza", FactionType.CRIMINAL, "Japanese organized crime",
            power = 55, ideology = "Honor among thieves", headquarters = "hotel"),
        Faction("biker_gangs", "Biker Gangs", FactionType.CRIMINAL, "Motorcycle clubs",
            power = 35, ideology = "Freedom", headquarters = "construction_site"),
        Faction("prison_gangs", "Prison Gangs", FactionType.CRIMINAL, "Incarcerated organizations",
            power = 40, ideology = "Survival", headquarters = "prison"),
        Faction("corrupt_cops", "Corrupt Police Network", FactionType.CRIMINAL, "Dirty law enforcement",
            power = 45, ideology = "Profit", headquarters = "police_station"),
        Faction("human_trafficking", "Human Trafficking Ring", FactionType.CRIMINAL, "People trading",
            power = 50, ideology = "Profit", isHidden = true),
        Faction("arms_dealers", "Arms Dealers", FactionType.CRIMINAL, "Weapon smuggling",
            power = 50, ideology = "Profit", headquarters = "harbor"),
        Faction("hackers", "Hacker Collective", FactionType.CRIMINAL, "Cyber criminals",
            power = 40, ideology = "Information", isHidden = true),
        Faction("money_launderers", "Money Launderers", FactionType.CRIMINAL, "Clean dirty money",
            power = 45, ideology = "Profit", headquarters = "casino")
    )
    
    // Business Factions
    val businessFactions = listOf(
        Faction("chamber_commerce", "Chamber of Commerce", FactionType.BUSINESS, "Business association",
            power = 55, ideology = "Free enterprise", headquarters = "office_building"),
        Faction("industry_association", "Industry Associations", FactionType.BUSINESS, "Sector groups",
            power = 50, ideology = "Industry interests", headquarters = "office_building"),
        Faction("better_business", "Better Business Bureau", FactionType.BUSINESS, "Consumer protection",
            power = 40, ideology = "Fair business", headquarters = "mall"),
        Faction("consumer_protection", "Consumer Protection Groups", FactionType.CIVIL_RIGHTS, "Buyer advocacy",
            power = 35, ideology = "Consumer rights", headquarters = "city_hall"),
        Faction("shareholder_activists", "Shareholder Activists", FactionType.POLITICAL, "Investor rights",
            power = 40, ideology = "Shareholder value", headquarters = "stock_exchange"),
        Faction("investors", "Venture Capital", FactionType.BUSINESS, "Startup investors",
            power = 50, ideology = "Growth", headquarters = "office_building")
    )
    
    val allFactions = royaltyFactions + politicalFactions + criminalFactions + businessFactions
    
    fun getFaction(id: String): Faction? = allFactions.find { it.id == id }
    
    fun getFactionsByType(type: FactionType): List<Faction> = allFactions.filter { it.type == type }
    
    fun getFactionsByPower(minPower: Int): List<Faction> = allFactions.filter { it.power >= minPower }
}

class FactionManager {
    private val factionRelationships = mutableMapOf<String, FactionRelationship>()
    
    init {
        FactionRegistry.allFactions.forEach { faction ->
            factionRelationships[faction.id] = FactionRelationship(faction.id)
        }
    }
    
    fun getRelationship(factionId: String): FactionRelationship? = factionRelationships[factionId]
    
    fun getAllRelationships(): Map<String, FactionRelationship> = factionRelationships
    
    fun modifyReputation(factionId: String, amount: Int) {
        factionRelationships[factionId]?.let { rel ->
            factionRelationships[factionId] = rel.copy(
                opinion = (rel.opinion + amount).coerceIn(-100, 100)
            )
        }
    }
    
    fun modifyTrust(factionId: String, amount: Int) {
        factionRelationships[factionId]?.let { rel ->
            factionRelationships[factionId] = rel.copy(
                trust = (rel.trust + amount).coerceIn(0, 100)
            )
        }
    }
    
    fun setAlliance(factionId: String, level: Int) {
        factionRelationships[factionId]?.let { rel ->
            factionRelationships[factionId] = rel.copy(allegiance = level)
        }
    }
    
    fun getAlliedFactions(factionId: String): List<Faction> {
        val faction = FactionRegistry.getFaction(factionId) ?: return emptyList()
        return faction.allies.mapNotNull { FactionRegistry.getFaction(it) }
    }
    
    fun getEnemyFactions(factionId: String): List<Faction> {
        val faction = FactionRegistry.getFaction(factionId) ?: return emptyList()
        return faction.enemies.mapNotNull { FactionRegistry.getFaction(it) }
    }
    
    fun canJoinFaction(factionId: String, reputation: Int): Boolean {
        val faction = FactionRegistry.getFaction(factionId) ?: return false
        return reputation >= faction.power / 2
    }
    
    fun getFactionBenefits(factionId: String): Map<String, Int> {
        val rel = factionRelationships[factionId] ?: return emptyMap()
        val faction = FactionRegistry.getFaction(factionId) ?: return emptyMap()
        
        return when {
            rel.opinion >= 80 -> mapOf("discount" to 20, "info" to 10, "protection" to 10)
            rel.opinion >= 50 -> mapOf("discount" to 10, "info" to 5)
            rel.opinion >= 0 -> mapOf("discount" to 5)
            rel.opinion >= -50 -> mapOf(" surcharge" to 10)
            else -> mapOf("surcharge" to 20, "hostility" to 10)
        }
    }
}
