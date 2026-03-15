package com.ultimatelifesimulator.character.skills

data class Skill(
    val id: String,
    val name: String,
    val category: SkillCategory,
    val level: Int = 1,
    val xp: Int = 0,
    val description: String = "",
    val isSpecialized: Boolean = false,
    val specialization: String? = null
) {
    fun xpToNextLevel(): Int = (level + 1) * 100
    
    fun xpProgress(): Float = xp.toFloat() / xpToNextLevel()
    
    fun addXp(amount: Int): Skill {
        var newXp = xp + amount
        var newLevel = level
        while (newXp >= xpToNextLevel()) {
            newXp -= xpToNextLevel()
            newLevel++
        }
        return copy(level = newLevel, xp = newXp)
    }
    
    fun canSpecialize(): Boolean = level >= 25 && !isSpecialized
    
    fun getEffectiveLevel(): Int = if (isSpecialized) level + 10 else level
}

enum class SkillCategory(val displayName: String) {
    SOCIAL("Social"),
    INTELLECTUAL("Intellectual"),
    CRIMINAL("Criminal"),
    COMBAT("Combat"),
    PHYSICAL("Physical"),
    PROFESSIONAL("Professional"),
    CREATIVE("Creative"),
    TECHNICAL("Technical"),
    MEDICAL("Medical"),
    LEGAL("Legal"),
    TRADES("Trades"),
    ENTERTAINMENT("Entertainment")
}

enum class SkillDifficulty(val difficulty: Int, val description: String) {
    TRIVIAL(10, "Anyone can do this"),
    EASY(25, "Simple task"),
    NORMAL(50, "Average difficulty"),
    HARD(70, "Requires skill"),
    EXPERT(85, "Master level"),
    LEGENDARY(95, "World class")
}

object SkillRegistry {
    val allSkills = listOf(
        // SOCIAL (6)
        Skill("public_speaking", "Public Speaking", SkillCategory.SOCIAL, description = "Speak to crowds effectively"),
        Skill("persuasion", "Persuasion", SkillCategory.SOCIAL, description = "Convince others to see your view"),
        Skill("deception", "Deception", SkillCategory.SOCIAL, description = "Lie convincingly"),
        Skill("intimidation", "Intimidation", SkillCategory.SOCIAL, description = "Frighten others"),
        Skill("leadership", "Leadership", SkillCategory.SOCIAL, description = "Command and inspire others"),
        Skill("negotiation", "Negotiation", SkillCategory.SOCIAL, description = "Strike deals"),
        Skill("gossip", "Gossip", SkillCategory.SOCIAL, description = "Spread and gather rumors"),
        Skill("seduction", "Seduction", SkillCategory.SOCIAL, description = "Romantic manipulation"),
        
        // INTELLECTUAL (10)
        Skill("learning", "Learning", SkillCategory.INTELLECTUAL, description = "Acquire knowledge quickly"),
        Skill("research", "Research", SkillCategory.INTELLECTUAL, description = "Find information"),
        Skill("writing", "Writing", SkillCategory.INTELLECTUAL, description = "Written communication"),
        Skill("mathematics", "Mathematics", SkillCategory.INTELLECTUAL, description = "Numbers and calculations"),
        Skill("history", "History", SkillCategory.INTELLECTUAL, description = "Knowledge of past events"),
        Skill("science", "Science", SkillCategory.INTELLECTUAL, description = "Scientific knowledge"),
        Skill("philosophy", "Philosophy", SkillCategory.INTELLECTUAL, description = "Deep thinking"),
        Skill("politics_knowledge", "Politics", SkillCategory.INTELLECTUAL, description = "Understanding government"),
        Skill("economics", "Economics", SkillCategory.INTELLECTUAL, description = "Understanding markets"),
        Skill("psychology", "Psychology", SkillCategory.INTELLECTUAL, description = "Understanding minds"),
        
        // CRIMINAL (12)
        Skill("lockpicking", "Lockpicking", SkillCategory.CRIMINAL, description = "Open locks without keys"),
        Skill("stealth", "Stealth", SkillCategory.CRIMINAL, description = "Move unnoticed"),
        Skill("pickpocketing", "Pickpocketing", SkillCategory.CRIMINAL, description = "Steal from pockets"),
        Skill("forgery", "Forgery", SkillCategory.CRIMINAL, description = "Create fake documents"),
        Skill("hacking", "Hacking", SkillCategory.CRIMINAL, description = "Computer intrusion"),
        Skill("fencing", "Fencing", SkillCategory.CRIMINAL, description = "Sell stolen goods"),
        Skill("surveillance", "Surveillance", SkillCategory.CRIMINAL, description = "Monitor targets"),
        Skill("counter_surveillance", "Counter-Surveillance", SkillCategory.CRIMINAL, description = "Avoid being watched"),
        Skill("escape", "Escape", SkillCategory.CRIMINAL, description = "Break out of confinement"),
        Skill("disguise", "Disguise", SkillCategory.CRIMINAL, description = "Change appearance"),
        Skill("street_survival", "Street Survival", SkillCategory.CRIMINAL, description = "Survive in criminal underworld"),
        Skill("drug_crafting", "Drug Crafting", SkillCategory.CRIMINAL, description = "Create illegal substances"),
        
        // COMBAT (8)
        Skill("melee_combat", "Melee Combat", SkillCategory.COMBAT, description = "Fight with weapons"),
        Skill("ranged_combat", "Ranged Combat", SkillCategory.COMBAT, description = "Use ranged weapons"),
        Skill("martial_arts", "Martial Arts", SkillCategory.COMBAT, description = "Unarmed fighting"),
        Skill("tactics", "Tactics", SkillCategory.COMBAT, description = "Battle strategy"),
        Skill("weaponry", "Weaponry", SkillCategory.COMBAT, description = "Weapon expertise"),
        Skill("archery", "Archery", SkillCategory.COMBAT, description = "Bow and arrow"),
        Skill("firearms", "Firearms", SkillCategory.COMBAT, description = "Guns"),
        Skill("explosives", "Explosives", SkillCategory.COMBAT, description = "Bombs and explosives"),
        
        // PHYSICAL (10)
        Skill("athletics", "Athletics", SkillCategory.PHYSICAL, description = "General physical ability"),
        Skill("fitness", "Fitness", SkillCategory.PHYSICAL, description = "Physical conditioning"),
        Skill("strength", "Strength", SkillCategory.PHYSICAL, description = "Raw physical power"),
        Skill("endurance", "Endurance", SkillCategory.PHYSICAL, description = "Physical stamina"),
        Skill("agility", "Agility", SkillCategory.PHYSICAL, description = "Speed and flexibility"),
        Skill("coordination", "Coordination", SkillCategory.PHYSICAL, description = "Body control"),
        Skill("swimming", "Swimming", SkillCategory.PHYSICAL, description = "Water activities"),
        Skill("climbing", "Climbing", SkillCategory.PHYSICAL, description = "Scale surfaces"),
        Skill("driving", "Driving", SkillCategory.PHYSICAL, description = "Vehicle operation"),
        Skill("piloting", "Piloting", SkillCategory.PHYSICAL, description = "Fly aircraft"),
        
        // PROFESSIONAL (15)
        Skill("medicine", "Medicine", SkillCategory.PROFESSIONAL, description = "Medical knowledge"),
        Skill("teaching", "Teaching", SkillCategory.PROFESSIONAL, description = "Educate others"),
        Skill("management", "Management", SkillCategory.PROFESSIONAL, description = "Lead organizations"),
        Skill("accounting", "Accounting", SkillCategory.PROFESSIONAL, description = "Financial management"),
        Skill("marketing", "Marketing", SkillCategory.PROFESSIONAL, description = "Promote products"),
        Skill("sales", "Sales", SkillCategory.PROFESSIONAL, description = "Sell products"),
        Skill("entrepreneurship", "Entrepreneurship", SkillCategory.PROFESSIONAL, description = "Start businesses"),
        Skill("project_management", "Project Management", SkillCategory.PROFESSIONAL, description = "Organize projects"),
        Skill("human_resources", "Human Resources", SkillCategory.PROFESSIONAL, description = "Manage people"),
        Skill("logistics", "Logistics", SkillCategory.PROFESSIONAL, description = "Supply chain management"),
        Skill("consulting", "Consulting", SkillCategory.PROFESSIONAL, description = "Expert advice"),
        Skill("trading", "Trading", SkillCategory.PROFESSIONAL, description = "Buy and sell"),
        Skill("investing", "Investing", SkillCategory.PROFESSIONAL, description = "Grow wealth"),
        Skill("real_estate", "Real Estate", SkillCategory.PROFESSIONAL, description = "Property deals"),
        Skill("tourism", "Tourism", SkillCategory.PROFESSIONAL, description = "Travel industry"),
        
        // CREATIVE (12)
        Skill("cooking", "Cooking", SkillCategory.CREATIVE, description = "Prepare food"),
        Skill("painting", "Painting", SkillCategory.CREATIVE, description = "Visual art"),
        Skill("music", "Music", SkillCategory.CREATIVE, description = "Musical ability"),
        Skill("sculpting", "Sculpting", SkillCategory.CREATIVE, description = "3D art"),
        Skill("photography", "Photography", SkillCategory.CREATIVE, description = "Capture images"),
        Skill("writing_creative", "Creative Writing", SkillCategory.CREATIVE, description = "Fiction writing"),
        Skill("poetry", "Poetry", SkillCategory.CREATIVE, description = "Write poetry"),
        Skill("dance", "Dance", SkillCategory.CREATIVE, description = "Movement art"),
        Skill("theater", "Theater", SkillCategory.CREATIVE, description = "Performance"),
        Skill("film_making", "Film Making", SkillCategory.CREATIVE, description = "Create films"),
        Skill("design", "Design", SkillCategory.CREATIVE, description = "Visual design"),
        Skill("crafts", "Crafts", SkillCategory.CREATIVE, description = "Manual arts"),
        
        // TECHNICAL (12)
        Skill("programming", "Programming", SkillCategory.TECHNICAL, description = "Code computers"),
        Skill("repair", "Repair", SkillCategory.TECHNICAL, description = "Fix things"),
        Skill("electronics", "Electronics", SkillCategory.TECHNICAL, description = "Circuit work"),
        Skill("mechanics", "Mechanics", SkillCategory.TECHNICAL, description = "Vehicle repair"),
        Skill("construction", "Construction", SkillCategory.TECHNICAL, description = "Build structures"),
        Skill("welding", "Welding", SkillCategory.TECHNICAL, description = "Metal joining"),
        Skill("electrical_work", "Electrical Work", SkillCategory.TECHNICAL, description = "Electrical systems"),
        Skill("plumbing", "Plumbing", SkillCategory.TECHNICAL, description = "Pipe work"),
        Skill("carpentry", "Carpentry", SkillCategory.TECHNICAL, description = "Wood work"),
        Skill("masonry", "Masonry", SkillCategory.TECHNICAL, description = "Stone work"),
        Skill("hvac", "HVAC", SkillCategory.TECHNICAL, description = "Climate control"),
        Skill("machining", "Machining", SkillCategory.TECHNICAL, description = "Machine work"),
        
        // MEDICAL (10)
        Skill("diagnosis", "Diagnosis", SkillCategory.MEDICAL, description = "Identify diseases"),
        Skill("surgery", "Surgery", SkillCategory.MEDICAL, description = "Perform operations"),
        Skill("pharmacology", "Pharmacology", SkillCategory.MEDICAL, description = "Drug knowledge"),
        Skill("psychiatry", "Psychiatry", SkillCategory.MEDICAL, description = "Mental health"),
        Skill("pediatrics", "Pediatrics", SkillCategory.MEDICAL, description = "Children's health"),
        Skill("emergency", "Emergency Medicine", SkillCategory.MEDICAL, description = "Urgent care"),
        Skill("anatomy", "Anatomy", SkillCategory.MEDICAL, description = "Body knowledge"),
        Skill("physiology", "Physiology", SkillCategory.MEDICAL, description = "Body functions"),
        Skill("pathology", "Pathology", SkillCategory.MEDICAL, description = "Disease study"),
        Skill("research_med", "Medical Research", SkillCategory.MEDICAL, description = "Medical science"),
        
        // LEGAL (6)
        Skill("law", "Law", SkillCategory.LEGAL, description = "Legal knowledge"),
        Skill("trial_advocacy", "Trial Advocacy", SkillCategory.LEGAL, description = "Courtroom skills"),
        Skill("legal_research", "Legal Research", SkillCategory.LEGAL, description = "Find laws"),
        Skill("contract_drafting", "Contract Drafting", SkillCategory.LEGAL, description = "Write contracts"),
        Skill("negotiation_legal", "Legal Negotiation", SkillCategory.LEGAL, description = "Settle disputes"),
        Skill("corporate_law", "Corporate Law", SkillCategory.LEGAL, description = "Business law"),
        
        // TRADES (8)
        Skill("farming", "Farming", SkillCategory.TRADES, description = "Agriculture"),
        Skill("gardening", "Gardening", SkillCategory.TRADES, description = "Plant cultivation"),
        Skill("mining", "Mining", SkillCategory.TRADES, description = "Extract resources"),
        Skill("logging", "Logging", SkillCategory.TRADES, description = "Timber work"),
        Skill("fishing", "Fishing", SkillCategory.TRADES, description = "Catch fish"),
        Skill("hunting", "Hunting", SkillCategory.TRADES, description = "Hunt game"),
        Skill("cooking_pro", "Professional Cooking", SkillCategory.TRADES, description = "Chef work"),
        Skill("baking", "Baking", SkillCategory.TRADES, description = "Bake goods"),
        
        // ENTERTAINMENT (6)
        Skill("sports", "Sports", SkillCategory.ENTERTAINMENT, description = "Athletic games"),
        Skill("gaming", "Gaming", SkillCategory.ENTERTAINMENT, description = "Video games"),
        Skill("gambling", "Gambling", SkillCategory.ENTERTAINMENT, description = "Bet games"),
        Skill("storytelling", "Storytelling", SkillCategory.ENTERTAINMENT, description = "Tell stories"),
        Skill("comedy", "Comedy", SkillCategory.ENTERTAINMENT, description = "Make people laugh"),
        Skill("hosting", "Hosting", SkillCategory.ENTERTAINMENT, description = "Event hosting")
    )
    
    val coreSkills = allSkills.filter { skill ->
        listOf(
            "public_speaking", "persuasion", "deception", "intimidation", "leadership", "negotiation",
            "learning", "research", "writing", "mathematics",
            "lockpicking", "stealth", "pickpocketing", "forgery", "hacking",
            "melee_combat", "ranged_combat", "martial_arts",
            "athletics", "fitness",
            "medicine", "law", "accounting", "programming", "repair",
            "cooking", "painting", "music"
        ).contains(skill.id)
    }
    
    fun getSkillById(id: String): Skill? = allSkills.find { it.id == id }
    
    fun getSkillsByCategory(category: SkillCategory): List<Skill> = allSkills.filter { it.category == category }
    
    fun getSkillCategories(): List<SkillCategory> = SkillCategory.values().toList()
}

class SkillManager {
    private val skills = mutableMapOf<String, Skill>()
    
    init {
        SkillRegistry.coreSkills.forEach { skill ->
            skills[skill.id] = skill.copy(level = 10)
        }
    }
    
    fun getSkill(id: String): Skill? = skills[id]
    
    fun getAllSkills(): List<Skill> = skills.values.toList()
    
    fun getSkillsByCategory(category: SkillCategory): List<Skill> = 
        skills.values.filter { it.category == category }
    
    fun addXp(skillId: String, amount: Int): Skill? {
        return skills[skillId]?.let { skill ->
            val updated = skill.addXp(amount)
            skills[skillId] = updated
            updated
        }
    }
    
    fun setLevel(skillId: String, level: Int) {
        skills[skillId] = skills[skillId]?.copy(level = level) ?: 
            SkillRegistry.getSkillById(skillId)?.copy(level = level) ?: return
    }
    
    fun canLearnSkill(skillId: String): Boolean = skills.containsKey(skillId) || SkillRegistry.getSkillById(skillId) != null
    
    fun learnSkill(skillId: String): Boolean {
        if (skills.containsKey(skillId)) return false
        SkillRegistry.getSkillById(skillId)?.let {
            skills[skillId] = it.copy(level = 1)
            return true
        }
        return false
    }
    
    fun getTotalSkillLevels(): Int = skills.values.sumOf { it.level }
    
    fun getSkillPoints(): Int = getTotalSkillLevels() / 10
}
