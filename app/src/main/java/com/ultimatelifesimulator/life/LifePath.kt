package com.ultimatelifesimulator.life

import com.ultimatelifesimulator.character.stats.PrimaryStats
import com.ultimatelifesimulator.character.traits.Trait

interface LifePath {
    val type: LifePathType
    val displayName: String
    val description: String
    fun getAvailableActions(): List<LifeAction>
    fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult
    fun getStatus(): LifePathStatus
}

enum class LifePathType {
    NONE,
    ROYALTY,
    POLITICS,
    CRIME,
    BUSINESS,
    CAREER
}

data class LifeAction(
    val id: String,
    val name: String,
    val description: String,
    val energyCost: Int,
    val timeCost: Int = 1,
    val requirements: Map<String, Int> = emptyMap()
)

data class LifeActionResult(
    val success: Boolean,
    val message: String,
    val statChanges: Map<String, Int> = emptyMap(),
    val moneyChange: Double = 0.0,
    val skillXp: Map<String, Int> = emptyMap(),
    val newFlags: Map<String, String> = emptyMap()
)

data class LifePathStatus(
    val level: Int = 1,
    val experience: Int = 0,
    val title: String = "None",
    val resources: Map<String, Double> = emptyMap()
)

class NoLifePath : LifePath {
    override val type = LifePathType.NONE
    override val displayName = "Unemployed"
    override val description = "You have no particular career path."

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("job_search", "Search for Job", "Look for employment", 10)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return LifeActionResult(true, "You continue without a clear path.")
    }

    override fun getStatus(): LifePathStatus = LifePathStatus()
}
