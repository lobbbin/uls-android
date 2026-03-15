package com.ultimatelifesimulator.life.business

import com.ultimatelifesimulator.life.*

data class BusinessStatus(
    val companyName: String = "",
    val industry: String = "None",
    val companyValue: Double = 0.0,
    val revenue: Double = 0.0,
    val expenses: Double = 0.0,
    val employees: Int = 0,
    val reputation: Int = 50,
    val marketShare: Float = 0f,
    val isPublic: Boolean = false,
    val stockPrice: Double = 0.0
) : LifePathStatus(
    level = 1,
    title = "Entrepreneur",
    resources = mapOf("value" to companyValue, "revenue" to revenue, "employees" to employees.toDouble())
)

class BusinessPath : LifePath {
    override val type = LifePathType.BUSINESS
    override val displayName = "Business"
    override val description = "Build a business empire from startup to corporate conglomerate."

    private var status = BusinessStatus()

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("manage", "Manage Operations", "Oversee daily operations", 20, 2),
        LifeAction("market", "Marketing Campaign", "Promote your products", 25, 2),
        LifeAction("hire", "Hire Employees", "Expand your workforce", 20, 2),
        LifeAction("research", "R&D", "Develop new products", 30, 3),
        LifeAction("negotiate", "Negotiate Deal", "Close big contracts", 25, 2),
        LifeAction("invest", "Investment", "Put money to work", 15, 1),
        LifeAction("expansion", "Expand Business", "Open new locations", 35, 4),
        LifeAction("crisis", "Handle Crisis", "Deal with problems", 30, 2)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return when (action.id) {
            "manage" -> {
                val efficiency = (stats.intellect / 10) + 5
                val profit = (status.revenue - status.expenses) * (1 + efficiency / 100)
                status = status.copy(companyValue = status.companyValue + profit)
                LifeActionResult(true, "Operations running smoothly. Profit: ${'$'}${"%.0f".format(profit)}")
            }
            "market" -> {
                val cost = 1000.0
                val reach = (stats.charisma * 2) + 50
                status = status.copy(
                    revenue = status.revenue + reach * 10,
                    marketShare = (status.marketShare + 0.5f).coerceAtMost(100f),
                    expenses = status.expenses + cost
                )
                LifeActionResult(true, "Marketing successful. Cost: ${'$'}${"%.0f".format(cost)}", moneyChange = -cost)
            }
            "hire" -> {
                val cost = (status.employees + 1) * 500.0
                if (cost <= 10000) {
                    status = status.copy(
                        employees = status.employees + 1,
                        expenses = status.expenses + cost / 12
                    )
                    LifeActionResult(true, "New employee hired.")
                } else {
                    LifeActionResult(false, "Cannot afford more employees.")
                }
            }
            "research" -> {
                val cost = 5000.0
                val success = stats.intellect > 40
                if (success) {
                    status = status.copy(companyValue = status.companyValue + 10000)
                    LifeActionResult(true, "New product developed!", moneyChange = -cost, statChanges = mapOf("intellect" to 3))
                } else {
                    LifeActionResult(false, "R&D failed.", moneyChange = -cost)
                }
            }
            "negotiate" -> {
                val success = stats.negotiation > 40
                if (success) {
                    val dealValue = (10000..50000).random()
                    status = status.copy(revenue = status.revenue + dealValue / 12)
                    LifeActionResult(true, "Deal worth ${'$'}$dealValue secured!", statChanges = mapOf("charisma" to 2))
                } else {
                    LifeActionResult(false, "Negotiation fell through.")
                }
            }
            else -> LifeActionResult(true, "Business as usual.")
        }
    }

    override fun getStatus(): BusinessStatus = status

    fun foundCompany(name: String, industry: String) {
        status = status.copy(companyName = name, industry = industry, companyValue = 10000.0, title = "CEO")
    }
    fun setRevenue(amount: Double) { status = status.copy(revenue = amount) }
    fun setExpenses(amount: Double) { status = status.copy(expenses = amount) }
}
