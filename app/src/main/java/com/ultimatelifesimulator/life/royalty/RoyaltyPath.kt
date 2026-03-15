package com.ultimatelifesimulator.life.royalty

import com.ultimatelifesimulator.life.*

data class RoyaltyStatus(
    val claimStrength: Int = 50,
    val treasury: Double = 10000.0,
    val armySize: Int = 1000,
    val councilLoyalty: Map<String, Int> = emptyMap(),
    val vassalCount: Int = 5,
    val prestige: Int = 50,
    val hasCrown: Boolean = false,
    val isMarried: Boolean = false,
    val heirAge: Int = 0
) : LifePathStatus(
    level = 1,
    title = "Noble",
    resources = mapOf("treasury" to treasury, "army" to armySize.toDouble())
)

class RoyaltyPath : LifePath {
    override val type = LifePathType.ROYALTY
    override val displayName = "Royalty"
    override val description = "Rise through the ranks of nobility to claim the throne itself."

    private var status = RoyaltyStatus()

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("hold_court", "Hold Court", "Meet with advisors and petitioners", 20, 4),
        LifeAction("collect_taxes", "Collect Taxes", "Gather revenue from your lands", 15, 2),
        LifeAction("military_review", "Review Military", "Inspect and train your army", 25, 3),
        LifeAction("diplomatic_mission", "Diplomatic Mission", "Negotiate with other kingdoms", 30, 5),
        LifeAction("hunt", "Royal Hunt", "Hunt for sport and exercise", 10, 2),
        LifeAction("intrigue", "Court Intrigue", "Scheme and plot against rivals", 20, 2),
        LifeAction("manage_council", "Manage Council", "Appoint and manage advisors", 15, 2),
        LifeAction("attend_ball", "Attend Royal Ball", "Socialize with other nobles", 15, 3)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return when (action.id) {
            "hold_court" -> {
                val prestigeGain = (stats.charisma / 10) + 1
                status = status.copy(prestige = (status.prestige + prestigeGain).coerceAtMost(100))
                LifeActionResult(
                    true, "The court is pleased with your leadership.",
                    statChanges = mapOf("charisma" to 2)
                )
            }
            "collect_taxes" -> {
                val taxRevenue = (status.vassalCount * 100) + (stats.cunning * 10)
                status = status.copy(treasury = status.treasury + taxRevenue)
                LifeActionResult(
                    true, "You collected ${'$'}$taxRevenue in taxes.",
                    moneyChange = taxRevenue.toDouble()
                )
            }
            "military_review" -> {
                val armyGain = (stats.violence / 20) + 1
                status = status.copy(armySize = status.armySize + armyGain)
                LifeActionResult(
                    true, "Your army grows stronger.",
                    statChanges = mapOf("violence" to 3)
                )
            }
            "intrigue" -> {
                val success = (stats.cunning + stats.charisma) > 80
                if (success) {
                    status = status.copy(prestige = (status.prestige + 5).coerceAtMost(100))
                    LifeActionResult(true, "Your schemes yield results.", statChanges = mapOf("cunning" to 5))
                } else {
                    LifeActionResult(false, "Your plots were discovered!", statChanges = mapOf("reputation" to -10))
                }
            }
            else -> LifeActionResult(true, "You perform your royal duties.")
        }
    }

    override fun getStatus(): RoyaltyStatus = status

    fun setTreasury(amount: Double) { status = status.copy(treasury = amount) }
    fun setPrestige(value: Int) { status = status.copy(prestige = value.coerceIn(0, 100)) }
    fun setClaimStrength(value: Int) { status = status.copy(claimStrength = value.coerceIn(0, 100)) }
}
