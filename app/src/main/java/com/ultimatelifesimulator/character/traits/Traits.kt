package com.ultimatelifesimulator.character.traits

data class Trait(
    val id: String,
    val name: String,
    val description: String,
    val type: TraitType,
    val statModifiers: Map<String, Int> = emptyMap(),
    val skillModifiers: Map<String, Int> = emptyMap(),
    val isHereditary: Boolean = false,
    val isAcquirable: Boolean = true,
    val conflictWith: List<String> = emptyList(),
    val synergyWith: List<String> = emptyList()
)

enum class TraitType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL,
    HIDDEN
}

object TraitRegistry {
    val positiveTraits = listOf(
        // Character-defining positive traits (20)
        Trait("ambitious", "Ambitious", "Driven to succeed in all endeavors", TraitType.POSITIVE, 
            mapOf("willpower" to 5), isHereditary = true),
        Trait("charismatic", "Charismatic", "Natural charm draws others in", TraitType.POSITIVE, 
            mapOf("charisma" to 10), synergyWith = listOf("honest", "generous")),
        Trait("lucky", "Lucky", "Fortune seems to favor you", TraitType.POSITIVE),
        Trait("connected", "Connected", "Knows important people everywhere", TraitType.POSITIVE),
        Trait("resilient", "Resilient", "Bounces back from setbacks", TraitType.POSITIVE, 
            mapOf("willpower" to 10)),
        Trait("just", "Just", "Fair and impartial in all dealings", TraitType.POSITIVE),
        Trait("brave", "Brave", "Not afraid of danger or confrontation", TraitType.POSITIVE, 
            mapOf("violence" to 5)),
        Trait("diligent", "Diligent", "Hardworking and persistent", TraitType.POSITIVE),
        Trait("patient", "Patient", "Calm under pressure", TraitType.POSITIVE, 
            mapOf("willpower" to 5)),
        Trait("honest", "Honest", "Trustworthy and truthful", TraitType.POSITIVE,
            conflictWith = listOf("deceitful", "dishonest")),
        Trait("humble", "Humble", "Doesn't boast or brag", TraitType.POSITIVE),
        Trait("gregarious", "Gregarious", "Loves social gatherings", TraitType.POSITIVE,
            mapOf("charisma" to 5)),
        Trait("athletic", "Athletic", "Naturally physically fit", TraitType.POSITIVE, 
            mapOf("fitness" to 10, "athletics" to 10)),
        Trait("bookworm", "Bookworm", "Loves learning and reading", TraitType.POSITIVE, 
            mapOf("intellect" to 10)),
        Trait("green_thumb", "Green Thumb", "Good with plants and nature", TraitType.POSITIVE),
        Trait("polyglot", "Polyglot", "Languages come easily", TraitType.POSITIVE, 
            mapOf("intellect" to 5)),
        Trait("savant", "Savant", "Exceptional in a specific field", TraitType.POSITIVE, isHereditary = true),
        Trait("leader", "Leader", "Natural leader of people", TraitType.POSITIVE, 
            mapOf("leadership" to 15), synergyWith = listOf("charismatic", "brave")),
        Trait("inspiring", "Inspiring", "Motivates others to achieve", TraitType.POSITIVE),
        Trait("temperate", "Temperate", "Moderate in all things", TraitType.POSITIVE),
        
        // Additional positive traits
        Trait("wise", "Wise", "Shows great insight and judgment", TraitType.POSITIVE, 
            mapOf("intellect" to 10, "perception" to 5)),
        Trait("compassionate", "Compassionate", "Deep empathy for others", TraitType.POSITIVE,
            mapOf("charisma" to 5)),
        Trait("creative", "Creative", "Imaginative and innovative", TraitType.POSITIVE),
        Trait("disciplined", "Disciplined", "Strong self-control", TraitType.POSITIVE,
            mapOf("willpower" to 10)),
        Trait("resourceful", "Resourceful", "Good at finding solutions", TraitType.POSITIVE),
        Trait("optimist", "Optimist", "Always sees the bright side", TraitType.POSITIVE),
        Trait("protective", "Protective", "Defends loved ones fiercely", TraitType.POSITIVE),
        Trait("reliable", "Reliable", "Always follows through", TraitType.POSITIVE),
        Trait("thorough", "Thorough", "Pays attention to detail", TraitType.POSITIVE),
        Trait("adaptable", "Adaptable", "Adjusts well to change", TraitType.POSITIVE)
    )

    val negativeTraits = listOf(
        // Major negative traits (25)
        Trait("greedy", "Greedy", "Obsessed with wealth and possessions", TraitType.NEGATIVE, 
            mapOf("charisma" to -5), conflictWith = listOf("generous", "humble")),
        Trait("paranoid", "Paranoid", "Trusts no one, always suspicious", TraitType.NEGATIVE, 
            mapOf("perception" to 5)),
        Trait("addict", "Addict", "Prone to substance abuse", TraitType.NEGATIVE),
        Trait("hot_headed", "Hot-Headed", "Quick to anger and violence", TraitType.NEGATIVE, 
            mapOf("violence" to 5), conflictWith = listOf("patient", "calm")),
        Trait("cowardly", "Cowardly", "Avoids danger at all costs", TraitType.NEGATIVE, 
            mapOf("violence" to -10)),
        Trait("dishonest", "Dishonest", "Lies and deceives easily", TraitType.NEGATIVE,
            conflictWith = listOf("honest")),
        Trait("sickly", "Sickly", "Prone to illness and disease", TraitType.NEGATIVE, 
            mapOf("health" to -10)),
        Trait("lazy", "Lazy", "Avoids work and effort", TraitType.NEGATIVE, 
            mapOf("energy" to 5)),
        Trait("gluttonous", "Gluttonous", "Overeats compulsively", TraitType.NEGATIVE),
        Trait("envious", "Envious", "Jealous of others' success", TraitType.NEGATIVE),
        Trait("prideful", "Prideful", "Thinks highly of oneself", TraitType.NEGATIVE,
            conflictWith = listOf("humble")),
        Trait("wrathful", "Wrathful", "Quick to seek revenge", TraitType.NEGATIVE, 
            mapOf("violence" to 10)),
        Trait("slothful", "Slothful", "Profound laziness", TraitType.NEGATIVE),
        Trait("lustful", "Lustful", "Obsessed with sexual desires", TraitType.NEGATIVE),
        Trait("cruel", "Cruel", "Takes pleasure in others' suffering", TraitType.NEGATIVE),
        Trait("deceitful", "Deceitful", "Natural born liar", TraitType.NEGATIVE, 
            mapOf("cunning" to 5), conflictWith = listOf("honest")),
        Trait("arbitrary", "Arbitrary", "Unpredictable and inconsistent", TraitType.NEGATIVE),
        Trait("craven", "Craven", "Extremely cowardly", TraitType.NEGATIVE,
            mapOf("violence" to -15)),
        Trait("shy", "Shy", "Quiet and socially awkward", TraitType.NEGATIVE, 
            mapOf("charisma" to -10)),
        Trait("stubborn", "Stubborn", "Refuses to change opinions", TraitType.NEGATIVE),
        Trait("clumsy", "Clumsy", "Accident-prone and careless", TraitType.NEGATIVE),
        Trait("insomniac", "Insomniac", "Chronic trouble sleeping", TraitType.NEGATIVE, 
            mapOf("energy" to -10)),
        Trait("socially_awkward", "Socially Awkward", "Poor social skills", TraitType.NEGATIVE,
            mapOf("charisma" to -10)),
        Trait("addictive_personality", "Addictive Personality", "Prone to all addictions", TraitType.NEGATIVE),
        Trait("chronic_illness", "Chronic Illness", "Ongoing health problems", TraitType.NEGATIVE, 
            mapOf("health" to -20)),
        
        // Additional negative traits
        Trait("arrogant", "Arrogant", "Excessive self-importance", TraitType.NEGATIVE,
            mapOf("charisma" to -5)),
        Trait("pessimist", "Pessimist", "Always expects the worst", TraitType.NEGATIVE),
        Trait("imprudent", "Imprudent", "Reckless and careless", TraitType.NEGATIVE),
        Trait("vindictive", "Vindictive", "Holds grudges forever", TraitType.NEGATIVE),
        Trait("narcissistic", "Narcissistic", "Excessive self-love", TraitType.NEGATIVE),
        Trait("manipulative", "Manipulative", "Uses others for personal gain", TraitType.NEGATIVE,
            mapOf("cunning" to 5)),
        Trait("irritable", "Irritable", "Easily annoyed", TraitType.NEGATIVE),
        Trait("impatient", "Impatient", "Hates waiting", TraitType.NEGATIVE),
        Trait("gullible", "Gullible", "Easily fooled", TraitType.NEGATIVE),
        Trait("scatterbrained", "Scatterbrained", "Forgetful and disorganized", TraitType.NEGATIVE)
    )

    val neutralTraits = listOf(
        // Quirky/neutral traits (20)
        Trait("religious", "Religious", "Devout religious faith", TraitType.NEUTRAL, 
            mapOf("piety" to 10)),
        Trait("cynical", "Cynical", "Skeptical of others' motives", TraitType.NEUTRAL),
        Trait("romantic", "Romantic", "Dreamy idealist about love", TraitType.NEUTRAL),
        Trait("solitary", "Solitary", "Prefers spending time alone", TraitType.NEUTRAL),
        Trait("loves_rain", "Loves the Rain", "Finds peace in storms", TraitType.NEUTRAL),
        Trait("hates_small_talk", "Hates Small Talk", "Prefers deep conversations", TraitType.NEUTRAL),
        Trait("conspiracy_theorist", "Conspiracy Theorist", "Believes in hidden truths", TraitType.NEUTRAL),
        Trait("night_owl", "Night Owl", "Most active at night", TraitType.NEUTRAL),
        Trait("early_bird", "Early Bird", "Morning person", TraitType.NEUTRAL),
        Trait("superstitious", "Superstitious", "Believes in omens and luck", TraitType.NEUTRAL),
        Trait("pragmatic", "Pragmatic", "Practical and realistic", TraitType.NEUTRAL),
        Trait("idealistic", "Idealistic", "Believes in perfection", TraitType.NEUTRAL),
        Trait("traditionalist", "Traditionalist", "Values old ways and customs", TraitType.NEUTRAL),
        Trait("innovator", "Innovator", "Embraces new ideas", TraitType.NEUTRAL),
        Trait("eccentric", "Eccentric", "Peculiar or unusual", TraitType.NEUTRAL),
        Trait("stoic", "Stoic", "Emotionally reserved", TraitType.NEUTRAL),
        Trait("extrovert", "Extrovert", "Gains energy from people", TraitType.NEUTRAL),
        Trait("introvert", "Introvert", "Loses energy around people", TraitType.NEUTRAL),
        Trait("perfectionist", "Perfectionist", "Needs everything perfect", TraitType.NEUTRAL),
        Trait("risk_taker", "Risk Taker", "Thrives on danger", TraitType.NEUTRAL),
        
        // Hidden traits
        Trait("secretly_evil", "Secretly Evil", "Hidden malicious intent", TraitType.HIDDEN),
        Trait("spy", "Spy", "Working for foreign power", TraitType.HIDDEN),
        Trait("bastard", "Bastard", "Born out of wedlock", TraitType.HIDDEN, isHereditary = true),
        Trait("royal_blood", "Royal Blood", "Hidden noble lineage", TraitType.HIDDEN, isHereditary = true),
        Trait("prophet", "Prophet", "Touched by divine power", TraitType.HIDDEN)
    )

    val allTraits = positiveTraits + negativeTraits + neutralTraits

    fun getTrait(id: String): Trait? = allTraits.find { it.id == id }
    
    fun getTraitsByType(type: TraitType): List<Trait> = allTraits.filter { it.type == type }
    
    fun getHereditaryTraits(): List<Trait> = allTraits.filter { it.isHereditary }
    
    fun getConflictingTraits(traitId: String): List<Trait> {
        return getTrait(traitId)?.let { trait ->
            trait.conflictWith.mapNotNull { getTrait(it) }
        } ?: emptyList()
    }
    
    fun getSynergisticTraits(traitId: String): List<Trait> {
        return getTrait(traitId)?.let { trait ->
            trait.synergyWith.mapNotNull { getTrait(it) }
        } ?: emptyList()
    }
}

class TraitManager {
    private val traits = mutableSetOf<String>()
    private val hiddenTraits = mutableSetOf<String>()
    
    fun hasTrait(traitId: String): Boolean = traits.contains(traitId)
    
    fun addTrait(traitId: String): Boolean {
        if (traits.contains(traitId)) return false
        val trait = TraitRegistry.getTrait(traitId) ?: return false
        
        // Check conflicts
        trait.conflictWith.forEach { conflictId ->
            if (traits.contains(conflictId)) return false
        }
        
        if (trait.type == TraitType.HIDDEN) {
            hiddenTraits.add(traitId)
        } else {
            traits.add(traitId)
        }
        return true
    }
    
    fun removeTrait(traitId: String): Boolean {
        return traits.remove(traitId) || hiddenTraits.remove(traitId)
    }
    
    fun getAllTraits(): List<Trait> = traits.mapNotNull { TraitRegistry.getTrait(it) }
    
    fun getHiddenTraits(): List<Trait> = hiddenTraits.mapNotNull { TraitRegistry.getTrait(it) }
    
    fun getStatModifiers(): Map<String, Int> {
        val modifiers = mutableMapOf<String, Int>()
        getAllTraits().forEach { trait ->
            trait.statModifiers.forEach { (stat, mod) ->
                modifiers[stat] = (modifiers[stat] ?: 0) + mod
            }
        }
        return modifiers
    }
    
    fun canAddMoreTraits(maxTraits: Int = 5): Boolean = traits.size < maxTraits
    
    fun getTraitCount(): Int = traits.size + hiddenTraits.size
}
