package com.ultimatelifesimulator.world.locations

import com.ultimatelifesimulator.character.stats.PrimaryStats

data class Location(
    val id: String,
    val name: String,
    val type: LocationType,
    val region: String,
    val description: String,
    val isUnlocked: Boolean = true,
    val isAccessible: Boolean = true,
    val requiredStats: Map<String, Int> = emptyMap(),
    val requiredItems: List<String> = emptyList(),
    val requiredMoney: Double = 0.0,
    val requiredFactionRep: Map<String, Int> = emptyMap(),
    val dangerLevel: Int = 0,
    val entryFee: Double = 0.0,
    val activities: List<LocationActivity> = emptyList(),
    val NPCs: List<String> = emptyList(),
    val resources: Map<String, Int> = emptyMap(),
    val isIndoor: Boolean = true,
    val isOpen24Hours: Boolean = false,
    val openHour: Int = 0,
    val closeHour: Int = 24
)

data class LocationActivity(
    val id: String,
    val name: String,
    val description: String,
    val energyCost: Int,
    val timeCost: Int = 1,
    val moneyCost: Double = 0.0,
    val moneyGain: Double = 0.0,
    val statRequirements: Map<String, Int> = emptyMap(),
    val skillRequirements: Map<String, Int> = emptyMap(),
    val rewards: Map<String, Int> = emptyMap(),
    val xpRewards: Map<String, Int> = emptyMap(),
    val isAvailable: (PrimaryStats) -> Boolean = { true }
)

enum class LocationType(val displayName: String) {
    PALACE("Palace/Castle"),
    COURT("Royal Court"),
    NOBLE_ESTATE("Noble Estate"),
    SLAUDS("Slums"),
    PRISON("Prison/Jail"),
    POLICE_STATION("Police Station"),
    HOSPITAL("Hospital/Clinic"),
    BANK("Bank"),
    CHURCH("Church/Temple"),
    UNIVERSITY("University"),
    LIBRARY("Library"),
    MARKETPLACE("Marketplace"),
    SHOP("Shop/Store"),
    FACTORY("Factory"),
    OFFICE("Office Building"),
    COURTHOUSE("Courthouse"),
    LAW_FIRM("Law Firm"),
    RESTAURANT("Restaurant"),
    HOTEL("Hotel"),
    NIGHTCLUB("Nightclub"),
    CASINO("Casino"),
    GYM("Gym"),
    PARK("Park"),
    CEMETERY("Cemetery"),
    HARBOR("Harbor/Port"),
    AIRPORT("Airport"),
    TRAIN_STATION("Train Station"),
    MILITARY_BASE("Military Base"),
    EMBASSY("Embassy"),
    GOVERNMENT_BUILDING("Government Building"),
    CITY_HALL("City Hall"),
    SCHOOL("School"),
    DAYCARE("Daycare"),
    NURSING_HOME("Nursing Home"),
    HOMELESS_SHELTER("Homeless Shelter"),
    FOOD_BANK("Food Bank"),
    CONSTRUCTION_SITE("Construction Site"),
    FARM("Farm/Ranch")
}

object LocationRegistry {
    val allLocations = listOf(
        // Royalty Locations
        Location("palace", "Royal Palace", LocationType.PALACE, "Capital", "The seat of royal power and governance"),
        Location("throne_room", "Throne Room", LocationType.COURT, "Capital", "Where the monarch holds court"),
        Location("royal_court", "Royal Court", LocationType.COURT, "Capital", "Nobles and courtiers gather here"),
        Location("noble_estate", "Noble Estate", LocationType.NOBLE_ESTATE, "Various", "Aristocratic residence"),
        
        // Government Locations
        Location("city_hall", "City Hall", LocationType.CITY_HALL, "City", "Local government headquarters"),
        Location("courthouse", "Courthouse", LocationType.COURTHOUSE, "City", "Legal proceedings take place"),
        Location("government_building", "Government Building", LocationType.GOVERNMENT_BUILDING, "Capital", "Bureaucratic offices"),
        Location("military_base", "Military Base", LocationType.MILITARY_BASE, "Outskirts", "Armed forces installation"),
        Location("embassy", "Embassy", LocationType.EMBASSY, "Capital", "Foreign diplomatic mission"),
        
        // Law Enforcement
        Location("prison", "State Prison", LocationType.PRISON, "Outskirts", "Maximum security correctional facility"),
        Location("jail", "City Jail", LocationType.PRISON, "City", "Local detention facility"),
        Location("police_station", "Police Station", LocationType.POLICE_STATION, "City", "Law enforcement headquarters"),
        Location("police_precinct", "Police Precinct", LocationType.POLICE_STATION, "District", "Neighborhood police office"),
        
        // Medical
        Location("hospital", "General Hospital", LocationType.HOSPITAL, "City", "Full-service medical facility"),
        Location("clinic", "Medical Clinic", LocationType.HOSPITAL, "District", "Outpatient healthcare"),
        Location("emergency_room", "Emergency Room", LocationType.HOSPITAL, "City", "24/7 urgent care"),
        
        // Educational
        Location("university", "State University", LocationType.UNIVERSITY, "City", "Higher education institution"),
        Location("library", "Public Library", LocationType.LIBRARY, "City", "Knowledge repository"),
        Location("school_elementary", "Elementary School", LocationType.SCHOOL, "District", "Primary education"),
        Location("school_high", "High School", LocationType.SCHOOL, "City", "Secondary education"),
        
        // Financial
        Location("bank", "City Bank", LocationType.BANK, "City", "Financial institution"),
        Location("stock_exchange", "Stock Exchange", LocationType.BANK, "Capital", "Trading floor"),
        
        // Religious
        Location("church", "The Church", LocationType.CHURCH, "City", "House of worship"),
        Location("temple", "Temple", LocationType.CHURCH, "Various", "Religious sanctuary"),
        Location("cemetery", "Cemetery", LocationType.CEMETERY, "Outskirts", "Final resting place"),
        
        // Residential
        Location("slums", "The Slums", LocationType.SLAUDS, "District", "Poor neighborhood"),
        Location("apartment", "Apartment Complex", LocationType.NOBLE_ESTATE, "District", "Housing complex"),
        Location("neighborhood", "Suburban Neighborhood", LocationType.NOBLE_ESTATE, "Suburbs", "Residential area"),
        
        // Commercial
        Location("marketplace", "Marketplace", LocationType.MARKETPLACE, "City", "Bustling trade hub"),
        Location("mall", "Shopping Mall", LocationType.SHOP, "Suburbs", "Retail paradise"),
        Location("store", "General Store", LocationType.SHOP, "District", "Supplies and goods"),
        
        // Industrial
        Location("factory", "Manufacturing Plant", LocationType.FACTORY, "Industrial", "Production facility"),
        Location("office_building", "Office Tower", LocationType.OFFICE, "Downtown", "Corporate headquarters"),
        Location("construction_site", "Construction Site", LocationType.CONSTRUCTION_SITE, "City", "Building in progress"),
        
        // Entertainment
        Location("restaurant", "Fine Dining", LocationType.RESTAURANT, "City", "Upscale restaurant"),
        Location("diner", "Diner", LocationType.RESTAURANT, "District", "Casual eatery"),
        Location("nightclub", "Nightclub", LocationType.NIGHTCLUB, "City", "Nightlife venue"),
        Location("casino", "Casino", LocationType.CASINO, "Entertainment District", "Gambling establishment"),
        Location("gym", "Fitness Center", LocationType.GYM, "City", "Work out facility"),
        Location("park", "City Park", LocationType.PARK, "City", "Recreation area"),
        
        // Hospitality
        Location("hotel", "Luxury Hotel", LocationType.HOTEL, "City", "Upscale lodging"),
        Location("motel", "Motel", LocationType.HOTEL, "Highway", "Budget lodging"),
        
        // Transportation
        Location("airport", "International Airport", LocationType.AIRPORT, "Outskirts", "Air travel hub"),
        Location("train_station", "Train Station", LocationType.TRAIN_STATION, "City", "Railway hub"),
        Location("harbor", "Harbor", LocationType.HARBOR, "Coast", "Port facility"),
        
        // Social Services
        Location("homeless_shelter", "Homeless Shelter", LocationType.HOMELESS_SHELTER, "Slums", "Charity housing"),
        Location("food_bank", "Food Bank", LocationType.FOOD_BANK, "District", "Charity food distribution"),
        Location("daycare", "Daycare Center", LocationType.DAYCARE, "District", "Child care"),
        Location("nursing_home", "Nursing Home", LocationType.NURSING_HOME, "Suburbs", "Elder care facility"),
        
        // Professional Services
        Location("law_firm", "Law Offices", LocationType.LAW_FIRM, "Downtown", "Legal services"),
        
        // Agriculture
        Location("farm", "Family Farm", LocationType.FARM, "Countryside", "Agricultural land"),
        Location("ranch", "Ranch", LocationType.FARM, "Countryside", "Livestock farm"),
        
        // Special
        Location("hideout", "Criminal Hideout", LocationType.SLAUDS, "Unknown", "Secret criminal base"),
        Location("den", "Den of Inequity", LocationType.NIGHTCLUB, "Underground", "Illegal activities hub")
    )
    
    val royalLocations = allLocations.filter { it.type in listOf(LocationType.PALACE, LocationType.COURT, LocationType.NOBLE_ESTATE) }
    val governmentLocations = allLocations.filter { it.type in listOf(LocationType.GOVERNMENT_BUILDING, LocationType.CITY_HALL, LocationType.MILITARY_BASE) }
    val criminalLocations = allLocations.filter { it.type in listOf(LocationType.SLAUDS, LocationType.PRISON) || it.id in listOf("hideout", "den") }
    val businessLocations = allLocations.filter { it.type in listOf(LocationType.BANK, LocationType.OFFICE, LocationType.FACTORY, LocationType.SHOP, LocationType.MARKETPLACE) }
    val medicalLocations = allLocations.filter { it.type == LocationType.HOSPITAL }
    
    fun getLocation(id: String): Location? = allLocations.find { it.id == id }
    
    fun getLocationsByRegion(region: String): List<Location> = allLocations.filter { it.region == region }
    
    fun getLocationsByType(type: LocationType): List<Location> = allLocations.filter { it.type == type }
    
    fun getAccessibleLocations(stats: PrimaryStats, money: Double, items: List<String>, factionRep: Map<String, Int>): List<Location> {
        return allLocations.filter { location ->
            location.isAccessible &&
            location.requiredStats.all { (stat, min) -> stats.getStat(stat) >= min } &&
            location.requiredMoney <= money &&
            location.requiredItems.all { items.contains(it) } &&
            location.requiredFactionRep.all { (faction, rep) -> (factionRep[faction] ?: 0) >= rep }
        }
    }
}

class LocationManager {
    private val visitedLocations = mutableSetOf<String>()
    private val unlockedLocations = mutableSetOf("palace", "slums", "marketplace", "park")
    private val currentLocation: String = "palace"
    
    fun getCurrentLocation(): Location? = LocationRegistry.getLocation(currentLocation)
    
    fun visitLocation(locationId: String): Boolean {
        val location = LocationRegistry.getLocation(locationId) ?: return false
        visitedLocations.add(locationId)
        if (location.isUnlocked) {
            unlockedLocations.add(locationId)
        }
        return true
    }
    
    fun getVisitedLocations(): List<Location> = visitedLocations.mapNotNull { LocationRegistry.getLocation(it) }
    
    fun getUnlockedLocations(): List<Location> = unlockedLocations.mapNotNull { LocationRegistry.getLocation(it) }
    
    fun unlockLocation(locationId: String) {
        unlockedLocations.add(locationId)
    }
    
    fun isLocationUnlocked(locationId: String): Boolean = unlockedLocations.contains(locationId)
    
    fun getLocationsByType(type: LocationType): List<Location> = 
        LocationRegistry.getLocationsByType(type).filter { unlockedLocations.contains(it.id) }
}
