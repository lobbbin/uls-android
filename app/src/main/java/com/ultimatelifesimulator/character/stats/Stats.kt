package com.ultimatelifesimulator.character.stats

import com.ultimatelifesimulator.core.model.GameConstants

data class PrimaryStats(
    val health: Int = 100,
    val energy: Int = 100,
    val stress: Int = 0,
    val charisma: Int = 50,
    val intellect: Int = 50,
    val cunning: Int = 50,
    val violence: Int = 50,
    val stealth: Int = 50,
    val perception: Int = 50,
    val willpower: Int = 50
) {
    fun clamp() = copy(
        health = health.coerceIn(0, 100),
        energy = energy.coerceIn(0, 100),
        stress = stress.coerceIn(0, 100),
        charisma = charisma.coerceIn(0, 100),
        intellect = intellect.coerceIn(0, 100),
        cunning = cunning.coerceIn(0, 100),
        violence = violence.coerceIn(0, 100),
        stealth = stealth.coerceIn(0, 100),
        perception = perception.coerceIn(0, 100),
        willpower = willpower.coerceIn(0, 100)
    )
    
    fun toMap(): Map<String, Int> = mapOf(
        "health" to health,
        "energy" to energy,
        "stress" to stress,
        "charisma" to charisma,
        "intellect" to intellect,
        "cunning" to cunning,
        "violence" to violence,
        "stealth" to stealth,
        "perception" to perception,
        "willpower" to willpower
    )
    
    fun modifyStat(statName: String, amount: Int): PrimaryStats {
        return when (statName) {
            "health" -> copy(health = (health + amount).coerceIn(0, 100))
            "energy" -> copy(energy = (energy + amount).coerceIn(0, 100))
            "stress" -> copy(stress = (stress + amount).coerceIn(0, 100))
            "charisma" -> copy(charisma = (charisma + amount).coerceIn(0, 100))
            "intellect" -> copy(intellect = (intellect + amount).coerceIn(0, 100))
            "cunning" -> copy(cunning = (cunning + amount).coerceIn(0, 100))
            "violence" -> copy(violence = (violence + amount).coerceIn(0, 100))
            "stealth" -> copy(stealth = (stealth + amount).coerceIn(0, 100))
            "perception" -> copy(perception = (perception + amount).coerceIn(0, 100))
            "willpower" -> copy(willpower = (willpower + amount).coerceIn(0, 100))
            else -> this
        }
    }
    
    fun getStat(statName: String): Int = toMap()[statName] ?: 0
    
    fun isCritical(): Boolean = health <= GameConstants.HEALTH_CRITICAL || stress >= GameConstants.STRESS_CRITICAL
    
    fun getStatColor(statName: String): StatColor {
        val value = getStat(statName)
        return when {
            statName == "stress" -> when {
                value >= 80 -> StatColor.CRITICAL
                value >= 60 -> StatColor.POOR
                value >= 40 -> StatColor.AVERAGE
                value >= 20 -> StatColor.GOOD
                else -> StatColor.EXCELLENT
            }
            else -> when {
                value <= 20 -> StatColor.CRITICAL
                value <= 40 -> StatColor.POOR
                value <= 60 -> StatColor.AVERAGE
                value <= 80 -> StatColor.GOOD
                else -> StatColor.EXCELLENT
            }
        }
    }
}

enum class StatColor {
    EXCELLENT, GOOD, AVERAGE, POOR, CRITICAL
}

data class SecondaryStats(
    val reputation: Int = 0,
    val wealth: Double = 1000.0,
    val piety: Int = 0,
    val heat: Int = 0,
    val addiction: Int = 0,
    val streetCred: Int = 0,
    val nobleStanding: Int = 0,
    val politicalCapital: Int = 0,
    val loyalty: Int = 50,
    val education: Int = 0,
    val streetReputation: Int = 0,
    val socialStatus: Int = 50,
    val honor: Int = 50,
    val corruption: Int = 0,
    val influence: Int = 0
) {
    fun clamp() = copy(
        reputation = reputation.coerceIn(-100, 100),
        piety = piety.coerceIn(0, 100),
        heat = heat.coerceIn(0, 100),
        addiction = addiction.coerceIn(0, 100),
        streetCred = streetCred.coerceIn(0, 100),
        nobleStanding = nobleStanding.coerceIn(0, 100),
        politicalCapital = politicalCapital.coerceIn(0, 100),
        loyalty = loyalty.coerceIn(0, 100),
        education = education.coerceIn(0, 100),
        socialStatus = socialStatus.coerceIn(0, 100),
        honor = honor.coerceIn(0, 100),
        corruption = corruption.coerceIn(0, 100),
        influence = influence.coerceIn(0, 100)
    )
    
    fun toMap(): Map<String, Int> = mapOf(
        "reputation" to reputation,
        "piety" to piety,
        "heat" to heat,
        "addiction" to addiction,
        "streetCred" to streetCred,
        "nobleStanding" to nobleStanding,
        "politicalCapital" to politicalCapital,
        "loyalty" to loyalty,
        "education" to education,
        "socialStatus" to socialStatus,
        "honor" to honor,
        "corruption" to corruption,
        "influence" to influence
    )
    
    fun getWealthTier(): WealthTier = when {
        wealth >= 10000000.0 -> WealthTier.BILLIONAIRE
        wealth >= 1000000.0 -> WealthTier.MILLIONAIRE
        wealth >= 100000.0 -> WealthTier.RICH
        wealth >= 10000.0 -> WealthTier.UPPER_MIDDLE
        wealth >= 1000.0 -> WealthTier.MIDDLE
        wealth >= 0 -> WealthTier.LOWER
        else -> WealthTier.POOR
    }
}

enum class WealthTier {
    BILLIONAIRE, MILLIONAIRE, RICH, UPPER_MIDDLE, MIDDLE, LOWER, POOR
}

data class DerivedStats(
    val attackPower: Int = 50,
    val defensePower: Int = 50,
    val speed: Int = 50,
    val luck: Int = 50,
    val carryCapacity: Int = 50,
    val socialPower: Int = 50,
    val economicPower: Int = 50,
    val magicalPower: Int = 0
) {
    companion object {
        fun calculateFrom(primary: PrimaryStats, secondary: SecondaryStats): DerivedStats {
            return DerivedStats(
                attackPower = ((primary.violence * 0.6) + (primary.strength * 0.4)).toInt(),
                defensePower = ((primary.health * 0.5) + (primary.willpower * 0.5)).toInt(),
                speed = ((100 - primary.energy) + primary.agility).toInt().coerceIn(0, 100),
                luck = (50 + (secondary.reputation / 4)).toInt().coerceIn(0, 100),
                carryCapacity = (50 + (primary.health / 4)).toInt().coerceIn(0, 100),
                socialPower = ((primary.charisma * 0.6) + (secondary.reputation * 0.4)).toInt(),
                economicPower = ((secondary.wealth / 1000).toInt() + primary.intellect).coerceIn(0, 100),
                magicalPower = 0
            )
        }
    }
}

data class CharacterBackground(
    val birthPlace: String = "Unknown",
    val familyClass: String = "Commoner",
    val familyWealth: Double = 1000.0,
    val fatherOccupation: String = "Farmer",
    val motherOccupation: String = "Housewife",
    val siblingsCount: Int = 0,
    val birthOrder: Int = 1,
    val childhoodEvents: List<String> = emptyList(),
    val earlyTraumas: List<String> = emptyList(),
    val earlyAchievements: List<String> = emptyList()
) {
    companion object {
        val familyClasses = listOf("Peasant", "Commoner", "Merchant", "Petty Noble", "Noble", "Royal", "Criminal", "Religious")
        val occupations = listOf("Farmer", "Merchant", "Soldier", "Craftsman", "Scholar", "Noble", "Criminal", "Clergy", "Official", "Artist")
        val childhoodEventTypes = listOf("Accident", "Illness", "Witnessed Crime", "Religious Experience", "Natural Disaster", "War", "Family Death", "Found Treasure", "Abuse", "Adoption")
    }
}

data class Character(
    val id: Long = 0,
    val name: String = "Player",
    val surname: String = "",
    val gender: String = "Male",
    val age: Int = 18,
    val dayOfBirth: Int = 1,
    val monthOfBirth: Int = 1,
    val yearOfBirth: Int = 2008,
    val isPlayer: Boolean = true,
    val primaryStats: PrimaryStats = PrimaryStats(),
    val secondaryStats: SecondaryStats = SecondaryStats(),
    val derivedStats: DerivedStats = DerivedStats(),
    val background: CharacterBackground = CharacterBackground(),
    val isAlive: Boolean = true,
    val isPlayerCharacter: Boolean = true
) {
    fun getFullName(): String = if (surname.isNotEmpty()) "$name $surname" else name
    
    fun getDisplayAge(currentYear: Int): Int = age + (currentYear - yearOfBirth)
}
