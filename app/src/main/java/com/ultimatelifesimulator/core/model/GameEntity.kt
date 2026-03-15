package com.ultimatelifesimulator.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
abstract class GameEntity(
    open val id: Long = 0,
    open val name: String = ""
) : Parcelable

@Parcelize
data class StatModifier(
    val statName: String,
    val value: Int,
    val duration: Int = -1,
    val description: String = "",
    val source: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    fun isExpired(): Boolean = duration > 0 && (System.currentTimeMillis() - timestamp) > duration * 1000L
}

@Parcelize
data class Condition(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val target: String,
    val operator: String,
    val value: Int,
    val description: String = ""
) : Parcelable {
    fun evaluate(stats: Map<String, Int>): Boolean {
        val currentValue = stats[target] ?: 0
        return when (operator) {
            ">" -> currentValue > value
            "<" -> currentValue < value
            ">=" -> currentValue >= value
            "<=" -> currentValue <= value
            "==" -> currentValue == value
            "!=" -> currentValue != value
            else -> false
        }
    }
}

@Parcelize
data class GameFlag(
    val key: String,
    val value: String,
    val timestamp: Long = System.currentTimeMillis(),
    val expiresAt: Long = -1
) : Parcelable {
    fun isExpired(): Boolean = expiresAt > 0 && System.currentTimeMillis() > expiresAt
}

@Parcelize
data class ResourceChange(
    val resourceType: String,
    val amount: Double,
    val reason: String = ""
) : Parcelable

@Parcelize
data class SkillCheck(
    val skillId: String,
    val difficulty: Int,
    val statBonus: String? = null,
    val statBonusValue: Int = 0
) : Parcelable {
    fun calculateSuccess(totalSkill: Int): Boolean {
        val adjusted = totalSkill + statBonusValue
        return adjusted >= difficulty
    }
}

@Parcelize
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val progress: Int = 0,
    val target: Int = 1
) : Parcelable

@Parcelize
data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val category: String = "general"
) : Parcelable

object GameConstants {
    const val MAX_STAT_VALUE = 100
    const val MIN_STAT_VALUE = 0
    const val STARTING_AGE = 18
    const val MAX_AGE = 100
    const val DAYS_PER_YEAR = 365
    const val HOURS_PER_DAY = 24
    
    const val CRITICAL_STAT_THRESHOLD = 20
    const val LOW_STAT_THRESHOLD = 40
    const val HIGH_STAT_THRESHOLD = 80
    
    const val SKILL_XP_PER_LEVEL = 100
    const val TRAIT_SLOTS = 5
    
    const val AUTO_SAVE_INTERVAL_MINUTES = 5
    
    const val RELATIONSHIP_MAX = 100
    const val RELATIONSHIP_MIN = -100
    const val TRUST_MAX = 100
    const val FEAR_MAX = 100
    
    const val WEALTH_RICH_THRESHOLD = 100000.0
    const val WEALTH_MIDDLE_THRESHOLD = 10000.0
    
    const val HEALTH_CRITICAL = 20
    const val HEALTH_LOW = 40
    const val ENERGY_LOW = 30
    const val STRESS_CRITICAL = 80
    
    const val HEAT_MAX = 100
    const val HEAT_ARREST_THRESHOLD = 80
    
    const val PRESTIGE_MAX = 100
    const val POLITICAL_CAPITAL_MAX = 100
}
