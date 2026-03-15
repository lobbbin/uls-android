package com.ultimatelifesimulator.life.politics

import com.ultimatelifesimulator.life.*

data class PoliticsStatus(
    val office: String = "None",
    val party: String = "Independent",
    val campaignFunds: Double = 0.0,
    val pollingNumbers: Int = 50,
    val term: Int = 0,
    val billsSponsored: Int = 0,
    val votesMissed: Int = 0,
    val scandalLevel: Int = 0
) : LifePathStatus(
    level = 1,
    title = "Citizen",
    resources = mapOf("campaign_funds" to campaignFunds, "polls" to pollingNumbers.toDouble())
)

class PoliticsPath : LifePath {
    override val type = LifePathType.POLITICS
    override val displayName = "Politics"
    override val description = "Climb the political ladder from local office to national leadership."

    private var status = PoliticsStatus()

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("campaign", "Campaign", "Travel and meet voters", 30, 4),
        LifeAction("fundraise", "Fundraise", "Solicit campaign donations", 20, 2),
        LifeAction("debate", "Debate", "Participate in political debates", 25, 3),
        LifeAction("legislation", "Propose Legislation", "Draft and sponsor bills", 20, 3),
        LifeAction("network", "Network", "Meet with political allies", 15, 2),
        LifeAction("research", "Policy Research", "Study issues and prepare proposals", 15, 2),
        LifeAction("town_hall", "Town Hall", "Hold public meetings", 20, 3),
        LifeAction("media_appearance", "Media Appearance", "Speak on TV or radio", 15, 2)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return when (action.id) {
            "campaign" -> {
                val supportGain = (stats.charisma / 10) + 2
                status = status.copy(pollingNumbers = (status.pollingNumbers + supportGain).coerceAtMost(100))
                val cost = 500.0
                status = status.copy(campaignFunds = status.campaignFunds - cost)
                LifeActionResult(
                    true, "Your campaign resonates with voters.",
                    statChanges = mapOf("charisma" to 2),
                    moneyChange = -cost
                )
            }
            "fundraise" -> {
                val fundsRaised = (stats.charisma * 50) + (stats.cunning * 20) + 500
                status = status.copy(campaignFunds = status.campaignFunds + fundsRaised)
                LifeActionResult(
                    true, "You raised ${'$'}$fundsRaised for your campaign.",
                    moneyChange = fundsRaised
                )
            }
            "debate" -> {
                val success = (stats.intellect + stats.charisma) > 100
                if (success) {
                    status = status.copy(pollingNumbers = (status.pollingNumbers + 5).coerceAtMost(100))
                    LifeActionResult(true, "You won the debate!", statChanges = mapOf("charisma" to 5, "intellect" to 2))
                } else {
                    status = status.copy(pollingNumbers = (status.pollingNumbers - 3).coerceAtLeast(0))
                    LifeActionResult(false, "The debate didn't go well.", statChanges = mapOf("charisma" to -2))
                }
            }
            "legislation" -> {
                val success = (stats.intellect + stats.cunning) > 80
                if (success) {
                    status = status.copy(billsSponsored = status.billsSponsored + 1)
                    LifeActionResult(true, "Your bill gains support.", statChanges = mapOf("intellect" to 3))
                } else {
                    LifeActionResult(false, "Your bill lacks support.")
                }
            }
            else -> LifeActionResult(true, "You continue your political work.")
        }
    }

    override fun getStatus(): PoliticsStatus = status

    fun setParty(party: String) { status = status.copy(party = party) }
    fun setOffice(office: String) { status = status.copy(office = office, title = office) }
    fun addScandal(level: Int) { status = status.copy(scandalLevel = (status.scandalLevel + level).coerceAtMost(100)) }
}
