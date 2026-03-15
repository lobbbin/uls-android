package com.ultimatelifesimulator.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: Long = 1,
    val name: String = "Player",
    val age: Int = 18,
    val gender: String = "Male",
    val day: Int = 1,
    val year: Int = 2024,
    
    // Primary Stats
    val health: Int = 100,
    val energy: Int = 100,
    val stress: Int = 0,
    val charisma: Int = 50,
    val intellect: Int = 50,
    val cunning: Int = 50,
    val violence: Int = 50,
    val stealth: Int = 50,
    val perception: Int = 50,
    val willpower: Int = 50,
    
    // Secondary Stats
    val reputation: Int = 0,
    val wealth: Double = 1000.0,
    val piety: Int = 0,
    val heat: Int = 0,
    val addiction: Int = 0,
    val streetCred: Int = 0,
    val nobleStanding: Int = 0,
    val politicalCapital: Int = 0,
    
    // Life Path
    val lifePath: String = "none",
    
    // Timestamps
    val createdAt: Long = System.currentTimeMillis(),
    val lastPlayedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey val id: String,
    val playerId: Long = 1,
    val name: String,
    val category: String,
    val level: Int = 0,
    val xp: Int = 0
)

@Entity(tableName = "traits")
data class TraitEntity(
    @PrimaryKey val id: String,
    val playerId: Long = 1,
    val name: String,
    val description: String,
    val type: String
)

@Entity(tableName = "relationships")
data class RelationshipEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playerId: Long = 1,
    val npcId: String,
    val npcName: String,
    val type: String,
    val score: Int = 0,
    val trust: Int = 50,
    val fear: Int = 0,
    val respect: Int = 50,
    val loyalty: Int = 50
)

@Entity(tableName = "inventory_items")
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playerId: Long = 1,
    val itemId: String,
    val name: String,
    val category: String,
    val quantity: Int = 1,
    val value: Double = 0.0
)

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val region: String,
    val description: String,
    val isUnlocked: Boolean = true
)

@Entity(tableName = "factions")
data class FactionEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val power: Int = 50,
    val playerReputation: Int = 0,
    val description: String
)

@Entity(tableName = "game_flags")
data class GameFlagEntity(
    @PrimaryKey val key: String,
    val value: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players WHERE id = 1")
    fun getPlayer(): Flow<PlayerEntity?>
    
    @Query("SELECT * FROM players WHERE id = 1")
    suspend fun getPlayerSync(): PlayerEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)
    
    @Update
    suspend fun updatePlayer(player: PlayerEntity)
    
    @Query("UPDATE players SET lastPlayedAt = :timestamp WHERE id = 1")
    suspend fun updateLastPlayed(timestamp: Long)
}

@Dao
interface SkillDao {
    @Query("SELECT * FROM skills WHERE playerId = 1")
    fun getSkills(): Flow<List<SkillEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkill(skill: SkillEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<SkillEntity>)
    
    @Query("DELETE FROM skills WHERE playerId = 1")
    suspend fun deleteAllSkills()
}

@Dao
interface TraitDao {
    @Query("SELECT * FROM traits WHERE playerId = 1")
    fun getTraits(): Flow<List<TraitEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrait(trait: TraitEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraits(traits: List<TraitEntity>)
    
    @Query("DELETE FROM traits WHERE playerId = 1")
    suspend fun deleteAllTraits()
}

@Dao
interface RelationshipDao {
    @Query("SELECT * FROM relationships WHERE playerId = 1")
    fun getRelationships(): Flow<List<RelationshipEntity>>
    
    @Query("SELECT * FROM relationships WHERE playerId = 1 AND type = :type")
    fun getRelationshipsByType(type: String): Flow<List<RelationshipEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelationship(relationship: RelationshipEntity)
    
    @Update
    suspend fun updateRelationship(relationship: RelationshipEntity)
}

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_items WHERE playerId = 1")
    fun getInventory(): Flow<List<InventoryItemEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItemEntity)
    
    @Update
    suspend fun updateItem(item: InventoryItemEntity)
    
    @Query("DELETE FROM inventory_items WHERE id = :id")
    suspend fun deleteItem(id: Long)
}

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getLocations(): Flow<List<LocationEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)
}

@Dao
interface FactionDao {
    @Query("SELECT * FROM factions")
    fun getFactions(): Flow<List<FactionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaction(faction: FactionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFactions(factions: List<FactionEntity>)
}

@Dao
interface GameFlagDao {
    @Query("SELECT * FROM game_flags")
    fun getFlags(): Flow<List<GameFlagEntity>>
    
    @Query("SELECT * FROM game_flags WHERE `key` = :key")
    suspend fun getFlag(key: String): GameFlagEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlag(flag: GameFlagEntity)
    
    @Query("DELETE FROM game_flags WHERE `key` = :key")
    suspend fun deleteFlag(key: String)
}

@Database(
    entities = [
        PlayerEntity::class,
        SkillEntity::class,
        TraitEntity::class,
        RelationshipEntity::class,
        InventoryItemEntity::class,
        LocationEntity::class,
        FactionEntity::class,
        GameFlagEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun skillDao(): SkillDao
    abstract fun traitDao(): TraitDao
    abstract fun relationshipDao(): RelationshipDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun locationDao(): LocationDao
    abstract fun factionDao(): FactionDao
    abstract fun gameFlagDao(): GameFlagDao
}
