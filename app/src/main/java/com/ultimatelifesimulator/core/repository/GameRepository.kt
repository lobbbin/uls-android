package com.ultimatelifesimulator.core.repository

import com.ultimatelifesimulator.core.database.*
import com.ultimatelifesimulator.character.skills.SkillRegistry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val playerDao: PlayerDao,
    private val skillDao: SkillDao,
    private val traitDao: TraitDao,
    private val relationshipDao: RelationshipDao,
    private val inventoryDao: InventoryDao,
    private val locationDao: LocationDao,
    private val factionDao: FactionDao,
    private val gameFlagDao: GameFlagDao
) {
    // Player operations
    fun getPlayer(): Flow<PlayerEntity?> = playerDao.getPlayer()
    
    suspend fun getPlayerSync(): PlayerEntity? = playerDao.getPlayerSync()
    
    suspend fun savePlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }
    
    suspend fun updateLastPlayed() {
        playerDao.updateLastPlayed(System.currentTimeMillis())
    }

    // Skills operations
    fun getSkills(): Flow<List<SkillEntity>> = skillDao.getSkills()
    
    suspend fun initializeSkills() {
        val existingSkills = skillDao.getSkills().first()
        if (existingSkills.isEmpty()) {
            val skills = SkillRegistry.coreSkills.map { skill ->
                SkillEntity(
                    id = skill.id,
                    name = skill.name,
                    category = skill.category.name,
                    level = 10,
                    xp = 0
                )
            }
            skillDao.insertSkills(skills)
        }
    }

    // Traits operations
    fun getTraits(): Flow<List<TraitEntity>> = traitDao.getTraits()
    
    suspend fun addTrait(trait: TraitEntity) {
        traitDao.insertTrait(trait)
    }

    // Relationships operations
    fun getRelationships(): Flow<List<RelationshipEntity>> = relationshipDao.getRelationships()
    
    fun getRelationshipsByType(type: String): Flow<List<RelationshipEntity>> = 
        relationshipDao.getRelationshipsByType(type)
    
    suspend fun saveRelationship(relationship: RelationshipEntity) {
        relationshipDao.insertRelationship(relationship)
    }

    // Inventory operations
    fun getInventory(): Flow<List<InventoryItemEntity>> = inventoryDao.getInventory()
    
    suspend fun addItem(item: InventoryItemEntity) {
        inventoryDao.insertItem(item)
    }

    // Locations operations
    fun getLocations(): Flow<List<LocationEntity>> = locationDao.getLocations()
    
    suspend fun initializeLocations() {
        val existingLocations = locationDao.getLocations().first()
        if (existingLocations.isEmpty()) {
            val locations = listOf(
                LocationEntity("palace", "Royal Palace", "royalty", "capital", "The seat of royal power"),
                LocationEntity("court", "Royal Court", "royalty", "capital", "Where nobles gather"),
                LocationEntity("slums", "The Slums", "criminal", "city", "Poor neighborhood"),
                LocationEntity("prison", "Prison", "government", "city", "Correctional facility"),
                LocationEntity("police_station", "Police Station", "government", "city", "Law enforcement HQ"),
                LocationEntity("hospital", "Hospital", "medical", "city", "Healthcare facility"),
                LocationEntity("bank", "Bank", "business", "city", "Financial institution"),
                LocationEntity("church", "Church", "religious", "city", "House of worship"),
                LocationEntity("university", "University", "education", "city", "Higher education"),
                LocationEntity("marketplace", "Marketplace", "commercial", "city", "Bustling market"),
                LocationEntity("factory", "Factory", "business", "industrial", "Manufacturing plant"),
                LocationEntity("office", "Office Building", "business", "city", "Corporate headquarters"),
                LocationEntity("restaurant", "Restaurant", "service", "city", "Dining establishment"),
                LocationEntity("nightclub", "Nightclub", "entertainment", "city", "Nightlife venue"),
                LocationEntity("gym", "Gym", "recreation", "city", "Fitness center")
            )
            locationDao.insertLocations(locations)
        }
    }

    // Factions operations
    fun getFactions(): Flow<List<FactionEntity>> = factionDao.getFactions()
    
    suspend fun initializeFactions() {
        val existingFactions = factionDao.getFactions().first()
        if (existingFactions.isEmpty()) {
            val factions = listOf(
                FactionEntity("royal_family", "Royal Family", "royalty", 100, 0, "The ruling dynasty"),
                FactionEntity("noble_houses", "Noble Houses", "royalty", 80, 0, "Aristocratic families"),
                FactionEntity("church", "The Church", "religious", 70, 0, "Religious authority"),
                FactionEntity("military", "Military Command", "government", 75, 0, "Armed forces"),
                FactionEntity("merchants", "Merchant Guild", "business", 60, 0, "Trade organization"),
                FactionEntity("street_gangs", "Street Gangs", "criminal", 40, 0, "Underworld organizations"),
                FactionEntity("police", "Police Department", "government", 70, 0, "Law enforcement"),
                FactionEntity("politicians", "Politicians", "political", 50, 0, "Government officials"),
                FactionEntity("business_owners", "Business Owners", "business", 55, 0, "Corporate leaders"),
                FactionEntity("labor_unions", "Labor Unions", "political", 45, 0, "Worker organizations")
            )
            factionDao.insertFactions(factions)
        }
    }

    // Game flags
    fun getFlags(): Flow<List<GameFlagEntity>> = gameFlagDao.getFlags()
    
    suspend fun setFlag(key: String, value: String) {
        gameFlagDao.insertFlag(GameFlagEntity(key, value))
    }
    
    suspend fun getFlag(key: String): String? = gameFlagDao.getFlag(key)?.value

    // Initialize new game
    suspend fun initializeNewGame(playerName: String) {
        val player = PlayerEntity(name = playerName)
        playerDao.insertPlayer(player)
        initializeSkills()
        initializeLocations()
        initializeFactions()
    }
}
