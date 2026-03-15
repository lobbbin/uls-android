package com.ultimatelifesimulator.life.career

import com.ultimatelifesimulator.life.*

data class CareerStatus(
    val profession: String = "None",
    val employer: String = "",
    val position: String = "Entry Level",
    val salary: Double = 0.0,
    val experience: Int = 0,
    val education: Int = 0,
    val skills: Map<String, Int> = emptyMap(),
    val satisfaction: Int = 50,
    val workHours: Int = 40,
    val promotions: Int = 0,
    val certifications: List<String> = emptyList()
) : LifePathStatus(
    level = 1 + promotions,
    title = position,
    resources = mapOf("salary" to salary, "experience" to experience.toDouble())
)

class CareerPath : LifePath {
    override val type = LifePathType.CAREER
    override val displayName = "Career"
    override val description = "Build a professional career in your chosen field."

    private var status = CareerStatus()

    override fun getAvailableActions(): List<LifeAction> = listOf(
        LifeAction("work", "Do Work", "Complete your job duties", 20, 8),
        LifeAction("overtime", "Work Overtime", "Extra hours for extra pay", 30, 4),
        LifeAction("train", "Professional Development", "Learn new skills", 20, 2),
 LifeAction("network", "Network", "Connect with colleagues", 15, 2),
        LifeAction("apply_promotion", "Seek Promotion", "Aim for advancement", 25, 1),
        LifeAction("certify", "Get Certification", "Earn professional credentials", 30, 3),
        LifeAction("switch_job", "Job Search", "Look for new opportunities", 25, 3),
        LifeAction("negotiate_salary", "Negotiate Salary", "Request a raise", 20, 1)
    )

    override fun processAction(action: LifeAction, stats: PrimaryStats): LifeActionResult {
        return when (action.id) {
            "work" -> {
                val performance = (stats.intellect / 10) + (stats.cunning / 10)
                val bonus = if (performance > 15) (status.salary * 0.1) else 0.0
                status = status.copy(experience = status.experience + 1)
                LifeActionResult(
                    true, "Work completed. Experience +1",
                    skillXp = mapOf("professional" to 10),
                    moneyChange = bonus
                )
            }
            "overtime" -> {
                val pay = (status.salary / 2000) * 50
                status = status.copy(experience = status.experience + 2)
                LifeActionResult(
                    true, "Overtime completed.",
                    moneyChange = pay,
                    statChanges = mapOf("stress" to 10, "energy" to -20)
                )
            }
            "train" -> {
                val skillGain = (stats.intellect / 10) + 1
                status = status.copy(skills = status.skills + ("general" to (status.skills["general"] ?: 0) + skillGain))
                LifeActionResult(true, "Skills improved.", statChanges = mapOf("intellect" to 2))
            }
            "apply_promotion" -> {
                val success = (stats.intellect + stats.charisma + status.experience) > 150
                if (success && status.promotions < 10) {
                    val newSalary = status.salary * 1.2
                    status = status.copy(
                        promotions = status.promotions + 1,
                        salary = newSalary,
                        position = getPromotionTitle(status.promotions + 1)
                    )
                    LifeActionResult(true, "Promoted to ${status.position}!", moneyChange = newSalary - status.salary)
                } else {
                    LifeActionResult(false, "Promotion denied.")
                }
            }
            "negotiate_salary" -> {
                val success = stats.negotiation > 40
                if (success) {
                    val raise = status.salary * 0.1
                    status = status.copy(salary = status.salary + raise)
                    LifeActionResult(true, "Salary increased by 10%!", moneyChange = raise)
                } else {
                    LifeActionResult(false, "Salary negotiation failed.")
                }
            }
            else -> LifeActionResult(true, "You continue your career.")
        }
    }

    override fun getStatus(): CareerStatus = status

    private fun getPromotionTitle(level: Int): String = when (level) {
        1 -> "Junior"
        2 -> "Associate"
        3 -> "Senior"
        4 -> "Lead"
        5 -> "Manager"
        6 -> "Senior Manager"
        7 -> "Director"
        8 -> "Vice President"
        9 -> "Senior Vice President"
        10 -> "C-Level Executive"
        else -> "Executive"
    }

    fun setJob(profession: String, employer: String, salary: Double) {
        status = status.copy(profession = profession, employer = employer, salary = salary, title = profession)
    }
    fun addCertification(cert: String) {
        status = status.copy(certifications = status.certifications + cert)
    }
}
