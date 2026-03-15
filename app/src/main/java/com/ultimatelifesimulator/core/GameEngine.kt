package com.ultimatelifesimulator.core

import com.ultimatelifesimulator.character.stats.PrimaryStats
import com.ultimatelifesimulator.character.stats.SecondaryStats
import com.ultimatelifesimulator.core.database.PlayerEntity
import com.ultimatelifesimulator.core.repository.GameRepository
import com.ultimatelifesimulator.event.EventEffect
import com.ultimatelifesimulator.event.EventEffectType
import com.ultimatelifesimulator.event.GameEvent
import com.ultimatelifesimulator.health.HealthManager
import com.ultimatelifesimulator.inventory.InventoryManager
import com.ultimatelifesimulator.life.*
import com.ultimatelifesimulator.relationships.RelationshipManager
import com.ultimatelifesimulator.world.time.TimeManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEngine @Inject constructor(
    private val repository: GameRepository,
    private val timeManager: TimeManager,
    private val healthManager: HealthManager,
    private val inventoryManager: InventoryManager,
    private val relationshipManager: RelationshipManager
) {
    private var currentLifePath: LifePath = NoLifePath()
    
    suspend fun initializeNewGame(playerName: String) {
        repository.initializeNewGame(playerName)
        inventoryManager.addMoney(1000.0)
    }
    
    suspend fun loadGame() {
        val player = repository.getPlayerSync()
        if (player != null) {
            loadFromPlayer(player)
        }
    }
    
    private fun loadFromPlayer(player: PlayerEntity) {
        currentLifePath = when (player.lifePath) {
            "royalty" -> life.royalty.RoyaltyPath()
            "politics" -> life.politics.PoliticsPath()
            "crime" -> life.crime.CrimePath()
            "business" -> life.business.BusinessPath()
            "career" -> life.career.CareerPath()
            else -> NoLifePath()
        }
    }
    
    suspend fun saveGame() {
        repository.updateLastPlayed()
    }
    
    fun getPrimaryStats(): PrimaryStats {
        return PrimaryStats()
    }
    
    fun getSecondaryStats(): SecondaryStats {
        return SecondaryStats(
            wealth = inventoryManager.getMoney()
        )
    }
    
    fun getLifePath(): LifePath = currentLifePath
    
    fun setLifePath(type: LifePathType) {
        currentLifePath = when (type) {
            LifePathType.ROYALTY -> life.royalty.RoyaltyPath()
            LifePathType.POLITICS -> life.politics.PoliticsPath()
            LifePathType.CRIME -> life.crime.CrimePath()
            LifePathType.BUSINESS -> life.business.BusinessPath()
            LifePathType.CAREER -> life.career.CareerPath()
            LifePathType.NONE -> NoLifePath()
        }
    }
    
    fun processLifeAction(action: LifeAction): LifeActionResult {
        val result = currentLifePath.processAction(action, getPrimaryStats())
        
        if (result.moneyChange != 0.0) {
            if (result.moneyChange > 0) {
                inventoryManager.addMoney(result.moneyChange)
            } else {
                inventoryManager.removeMoney(-result.moneyChange)
            }
        }
        
        timeManager.advance(action.timeCost)
        
        return result
    }
    
    fun advanceTime(hours: Int = 1) {
        timeManager.advance(hours)
        healthManager.changeEnergy(-hours)
        
        if (healthManager.getHealthStatus().hunger > 80) {
            healthManager.takeDamage(2)
        }
    }
    
    fun getTimeString(): String = timeManager.getTime().format()
    
    fun getDay(): Int = timeManager.getDay()
    fun getYear(): Int = timeManager.getYear()
    
    fun getInventoryManager(): InventoryManager = inventoryManager
    fun getHealthManager(): HealthManager = healthManager
    fun getRelationshipManager(): RelationshipManager = relationshipManager
    fun getTimeManager(): TimeManager = timeManager
}
