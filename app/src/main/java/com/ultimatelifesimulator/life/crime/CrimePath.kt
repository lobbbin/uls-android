package com.ultimatelifesimulator.life.crime

import com.ultimatelifesimulator.life.*

data class CrimeStatus(
    val rank: String = "Street Level",
    val crewSize: Int = 0,
    val territory: String = "None",
    val heat: Int = 0,
    val arrestRecord: Int = 0,
    val prisonTime: Int = 0,
    val streetCred: Int = 10,
    val knownByPolice: Boolean = false
) : LifePathStatus(
    level = 1,
    title = "Street Criminal",
    resources = mapOf("heat" to heat.toDouble(), "cred" to streetCred.toDouble())
)

class CrimePath : LifePath {
    override val type = LifePathType.CRIME
    override val displayName = "Crime"
    override val description = "Build your criminal empire from the streets to becoming a crime lord."

    private var status = CrimeStatus()

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("petty_theft", "Petty Theft", "Steal small items", 15, 1),
        LifeAction("burglary", "Burglary", "Break into homes", 25, 2),
        LifeAction("drug_dealing", "Drug Dealing", "Sell illegal substances", 20, 2),
        LifeAction("extortion", "Extortion", "Threaten businesses for money", 20, 2),
        LifeAction("recruit", "Recruit Crew", "Add members to your organization", 25, 3),
        LifeAction("bribe", "Bribe Officials", "Corrupt law enforcement", 30, 2),
        LifeAction("territory_war", "Territory War", "Fight for control", 35, 3),
        LifeAction("launder", "Launder Money", "Clean your illicit gains", 20, 2)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return when (action.id) {
            "petty_theft" -> {
                val success = (stats.stealth + stats.cunning) > 60
                if (success) {
                    val loot = (50..200).random()
                    status = status.copy(heat = (status.heat + 5).coerceAtMost(100))
                    LifeActionResult(true, "You stole ${'$'}$loot.", moneyChange = loot.toDouble(), statChanges = mapOf("stealth" to 2))
                } else {
                    status = status.copy(heat = (status.heat + 10).coerceAtMost(100))
                    LifeActionResult(false, "You were caught!", statChanges = mapOf("heat" to 10))
                }
            }
            "burglary" -> {
                val success = (stats.stealth + stats.cunning) > 80
                if (success) {
                    val loot = (500..2000).random()
                    status = status.copy(heat = (status.heat + 10).coerceAtMost(100), streetCred = (status.streetCred + 2).coerceAtMost(100))
                    LifeActionResult(true, "Successful heist! ${'$'}$loot.", moneyChange = loot.toDouble(), statChanges = mapOf("stealth" to 3))
                } else {
                    status = status.copy(heat = (status.heat + 20).coerceAtMost(100))
                    LifeActionResult(false, "Alarm triggered!", statChanges = mapOf("heat" to 20))
                }
            }
            "extortion" -> {
                val success = stats.intimidation > 50
                if (success) {
                    val payment = (200..500).random()
                    status = status.copy(streetCred = (status.streetCred + 3).coerceAtMost(100))
                    LifeActionResult(true, "Business owner pays up.", moneyChange = payment.toDouble(), statChanges = mapOf("cunning" to 2))
                } else {
                    LifeActionResult(false, "They refused and went to police.")
                }
            }
            "recruit" -> {
                if (status.crewSize < 10) {
                    status = status.copy(crewSize = status.crewSize + 1)
                    LifeActionResult(true, "A new member joined your crew.")
                } else {
                    LifeActionResult(false, "Your crew is at maximum capacity.")
                }
            }
            "launder" -> {
                val fee = (50..100).random()
                status = status.copy(heat = (status.heat - 10).coerceAtLeast(0))
                LifeActionResult(true, "Money laundered. Cost: ${'$'}$fee", moneyChange = -fee.toDouble())
            }
            else -> LifeActionResult(true, "You continue your criminal activities.")
        }
    }

    override fun getStatus(): CrimeStatus = status

    fun setRank(rank: String) { status = status.copy(rank = rank, title = rank) }
    fun setTerritory(territory: String) { status = status.copy(territory = territory) }
    fun addArrest() { 
        status = status.copy(arrestRecord = status.arrestRecord + 1)
        if (status.arrestRecord >= 3) status = status.copy(knownByPolice = true)
    }
    fun addPrisonTime(days: Int) { status = status.copy(prisonTime = status.prisonTime + days) }
}
