package com.ultimatelifesimulator.event

import com.ultimatelifesimulator.core.model.Condition
import java.util.UUID

data class GameEvent(
    val id: String,
    val title: String,
    val description: String,
    val category: EventCategory,
    val choices: List<EventChoice>,
    val conditions: List<Condition> = emptyList(),
    val cooldown: Int = 0,
    val lifetime: Int = -1,
    val weight: Int = 1,
    val minAge: Int = 0,
    val maxAge: Int = 100,
    val requiresLifePath: String? = null,
    val isRepeatable: Boolean = true,
    val notification: String = ""
)

data class EventChoice(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val successChance: Float = 1.0f,
    val effects: List<EventEffect> = emptyList(),
    val requirements: List<Condition> = emptyList(),
    val energyCost: Int = 0,
    val timeCost: Int = 1
)

data class EventEffect(
    val type: EffectType,
    val target: String,
    val value: Int,
    val description: String = ""
)

enum class EventCategory {
    RANDOM,
    STORY,
    LIFE,
    ROYALTY,
    POLITICS,
    CRIME,
    BUSINESS,
    CAREER,
    HEALTH,
    RELATIONSHIP,
    WORLD,
    COMBAT
}

enum class EffectType {
    STAT_CHANGE,
    SKILL_XP,
    MONEY_CHANGE,
    RELATIONSHIP_CHANGE,
    TRAIT_GAIN,
    TRAIT_LOSE,
    FLAG_SET,
    FLAG_CLEAR,
    LOCATION_CHANGE,
    DAMAGE,
    HEAL,
    STRESS_CHANGE,
    ENERGY_CHANGE,
    FACTION_REP_CHANGE,
    EVENT_TRIGGER,
    GAME_OVER,
    HEAT_CHANGE,
    STREET_CRED_CHANGE,
    PRESTIGE_CHANGE,
    ARREST,
    HOSPITALIZE,
    INJURY,
    ILLNESS
}

class EventProcessor {
    fun processEffects(effects: List<EventEffect>, context: EventContext): EventResult {
        val result = EventResult()
        effects.forEach { effect ->
            when (effect.type) {
                EffectType.STAT_CHANGE -> result.statChanges[effect.target] = effect.value
                EffectType.MONEY_CHANGE -> result.moneyChange += effect.value
                EffectType.STRESS_CHANGE -> result.stressChange += effect.value
                EffectType.ENERGY_CHANGE -> result.energyChange += effect.value
                EffectType.DAMAGE -> result.damage += effect.value
                EffectType.HEAL -> result.healing += effect.value
                EffectType.SKILL_XP -> result.skillXp[effect.target] = effect.value
                EffectType.FLAG_SET -> result.flagsToSet[effect.target] = effect.value.toString()
                EffectType.FACTION_REP_CHANGE -> result.factionChanges[effect.target] = effect.value
                EffectType.RELATIONSHIP_CHANGE -> result.relationshipChanges[effect.target] = effect.value
                EffectType.TRAIT_GAIN -> result.traitsToGain.add(effect.target)
                EffectType.TRAIT_LOSE -> result.traitsToLose.add(effect.target)
                EffectType.GAME_OVER -> result.isGameOver = true
                EffectType.ARREST -> result.isArrested = true
                EffectType.HOSPITALIZE -> result.hospitalDays = effect.value
                EffectType.HEAT_CHANGE -> result.heatChange += effect.value
                EffectType.STREET_CRED_CHANGE -> result.streetCredChange += effect.value
                EffectType.PRESTIGE_CHANGE -> result.prestigeChange += effect.value
                else -> {}
            }
        }
        return result
    }
}

data class EventContext(
    val playerStats: Map<String, Int> = emptyMap(),
    val playerWealth: Double = 0.0,
    val relationships: Map<String, Int> = emptyMap(),
    val factionRep: Map<String, Int> = emptyMap(),
    val flags: Map<String, String> = emptyMap(),
    val skills: Map<String, Int> = emptyMap(),
    val lifePath: String = "none",
    val location: String = "home",
    val age: Int = 18,
    val health: Int = 100
)

data class EventResult(
    val isSuccess: Boolean = true,
    val message: String = "",
    val statChanges: MutableMap<String, Int> = mutableMapOf(),
    val moneyChange: Double = 0.0,
    val stressChange: Int = 0,
    val energyChange: Int = 0,
    val damage: Int = 0,
    val healing: Int = 0,
    val skillXp: MutableMap<String, Int> = mutableMapOf(),
    val flagsToSet: MutableMap<String, String> = mutableMapOf(),
    val factionChanges: MutableMap<String, Int> = mutableMapOf(),
    val relationshipChanges: MutableMap<String, Int> = mutableMapOf(),
    val traitsToGain: MutableList<String> = mutableListOf(),
    val traitsToLose: MutableList<String> = mutableListOf(),
    val isGameOver: Boolean = false,
    val isArrested: Boolean = false,
    val hospitalDays: Int = 0,
    val heatChange: Int = 0,
    val streetCredChange: Int = 0,
    val prestigeChange: Int = 0
)

class RandomEventGenerator {
    private val randomEvents = mutableListOf<GameEvent>()
    private val eventCooldowns = mutableMapOf<String, Long>()
    
    init {
        initializeEvents()
    }
    
    private fun initializeEvents() {
        // ROYALTY EVENTS (20 events)
        addRoyaltyEvents()
        
        // POLITICAL EVENTS (20 events)
        addPoliticalEvents()
        
        // CRIME/PRISON EVENTS (20 events)
        addCrimeEvents()
        
        // CROSS-OVER EVENTS (15 events)
        addCrossoverEvents()
        
        // RANDOM LIFE EVENTS (20 events)
        addRandomLifeEvents()
    }
    
    private fun addRoyaltyEvents() {
        randomEvents.add(createEvent("noble_request", "Noble's Request", "A vassal requests a tax break", EventCategory.ROYALTY, listOf(
            createChoice("grant", "Grant tax break", mapOf("noble_houses" to 10, "treasury" to -500)),
            createChoice("deny", "Deny request", mapOf("noble_houses" to -5)),
            createChoice("delay", "Delay decision", mapOf("stress" to 5))
        )))
        
        randomEvents.add(createEvent("diplomat_arrives", "Foreign Diplomat", "A diplomat arrives with alliance proposal", EventCategory.ROYALTY, listOf(
            createChoice("accept", "Accept alliance", mapOf("reputation" to 10)),
            createChoice("reject", "Reject alliance", mapOf("reputation" to -5)),
            createChoice("negotiate", "Negotiate terms", mapOf(), 0.7f)
        )))
        
        randomEvents.add(createEvent("peasant_revolt", "Peasant Revolt", "Local uprising threatens your rule", EventCategory.ROYALTY, listOf(
            createChoice("crush", "Crush the revolt", mapOf("prestige" to 10, "reputation" to -10)),
            createChoice("negotiate", "Negotiate", mapOf("prestige" to 5, "treasury" to -200)),
            createChoice("ignore", "Ignore them", mapOf("prestige" to -15))
        )))
        
        randomEvents.add(createEvent("plague_outbreak", "Plague Outbreak", "Disease spreads through your kingdom", EventCategory.ROYALTY, listOf(
            createChoice("quarantine", "Implement quarantine", mapOf("prestige" to 5, "treasury" to -500)),
            createChoice("flee", "Flee to safe lands", mapOf("prestige" to -20)),
            createChoice("prayer", "Organize prayers", mapOf("piety" to 10, "prestige" to -5))
        )))
        
        randomEvents.add(createEvent("heir_misbehavior", "Heir's Misbehavior", "Your heir causes a scandal", EventCategory.ROYALTY, listOf(
            createChoice("punish", "Punish severely", mapOf("prestige" to 5, "family" to -10)),
            createChoice("cover", "Cover it up", mapOf("prestige" to -5, "stress" to 10)),
            createChoice("forgive", "Forgive them", mapOf("prestige" to -10, "family" to 10))
        )))
        
        randomEvents.add(createEvent("assassination_attempt", "Assassination Attempt", "Someone tries to kill you", EventCategory.ROYALTY, listOf(
            createChoice("capture", "Capture assassin", mapOf("prestige" to 10, "intelligence" to 5)),
            createChoice("execute", "Execute on spot", mapOf("prestige" to 5, "reputation" to -10)),
            createChoice("flee", "Flee for safety", mapOf("prestige" to -10, "stress" to 20))
        )))
        
        randomEvents.add(createEvent("royal_hunt", "Royal Hunt", "Host a hunting expedition", EventCategory.ROYALTY, listOf(
            createChoice("hunt", "Participate", mapOf("violence" to 5, "prestige" to 5)),
            createChoice("spectate", "Watch from afar", mapOf("prestige" to 5)),
            createChoice("skip", "Skip the event", mapOf("prestige" to -5))
        )))
        
        randomEvents.add(createEvent("prophet_appears", "Prophet Appears", "A religious figure claims divine messages", EventCategory.ROYALTY, listOf(
            createChoice("support", "Support the prophet", mapOf("piety" to 10, "prestige" to 5)),
            createChoice("skeptical", "Remain skeptical", mapOf("piety" to -5)),
            createChoice("imprison", "Imprison as fraud", mapOf("prestige" to -10, "piety" to -10))
        )))
        
        randomEvents.add(createEvent("treasure_found", "Treasure Found", "Miners discover gold in your lands", EventCategory.ROYALTY, listOf(
            createChoice("claim", "Claim for treasury", mapOf("treasury" to 1000, "prestige" to 5)),
            createChoice("reward", "Reward the miners", mapOf("prestige" to 10, "treasury" to -200)),
            createChoice("invest", "Invest in mining", mapOf("treasury" to -500, "prestige" to 10))
        )))
        
        randomEvents.add(createEvent("marriage_proposal", "Marriage Proposal", "Foreign royalty proposes marriage", EventCategory.ROYALTY, listOf(
            createChoice("accept", "Accept marriage", mapOf("prestige" to 15, "family" to 10)),
            createChoice("reject", "Reject proposal", mapOf("prestige" to -5)),
            createChoice("counter", "Counter-offer", mapOf("prestige" to 5))
        )))
        
        randomEvents.add(createEvent("drought", "Drought", "Crop failure threatens famine", EventCategory.ROYALTY, listOf(
            createChoice("import", "Import food", mapOf("treasury" to -1000, "prestige" to 10)),
            createChoice("ration", "Implement rationing", mapOf("prestige" to -5, "stress" to 10)),
            createChoice("pray", "Hold prayer ceremonies", mapOf("piety" to 10, "prestige" to -10))
        )))
        
        randomEvents.add(createEvent("tournament", "Tournament", "Host a grand tournament", EventCategory.ROYALTY, listOf(
            createChoice("compete", "Compete yourself", mapOf("violence" to 10, "prestige" to 15)),
            createChoice("sponsor", "Sponsor competitors", mapOf("treasury" to -300, "prestige" to 10)),
            createChoice("attend", "Attend as spectator", mapOf("prestige" to 5))
        )))
        
        randomEvents.add(createEvent("spy_discovered", "Spy Discovered", "A foreign spy is caught in court", EventCategory.ROYALTY, listOf(
            createChoice("torture", "Torture for info", mapOf("prestige" to -5, "intelligence" to 10)),
            createChoice("exchange", "Exchange for prisoner", mapOf("prestige" to 5)),
            createChoice("execute", "Execute publicly", mapOf("prestige" to 10, "reputation" to -10))
        )))
        
        randomEvents.add(createEvent("heir_rivalry", "Heir's Rivalry", "Your children compete for succession", EventCategory.ROYALTY, listOf(
            createChoice("favor", "Favor one heir", mapOf("family" to 10, "family" to -10)),
            createChoice("neutral", "Remain neutral", mapOf("stress" to 10)),
            createChoice("establish", "Establish clear rules", mapOf("prestige" to 5, "stress" to 5))
        )))
        
        randomEvents.add(createEvent("mysterious_stranger", "Mysterious Stranger", "A stranger offers a dark bargain", EventCategory.ROYALTY, listOf(
            createChoice("accept", "Accept bargain", mapOf("prestige" to 15, "soul" to -10)),
            createChoice("reject", "Reject stranger", mapOf("prestige" to -5)),
            createChoice("investigate", "Investigate them", mapOf("intelligence" to 10))
        )))
        
        randomEvents.add(createEvent("royal_insult", "Royal Insult", "A foreign ambassador insults you", EventCategory.ROYALTY, listOf(
            createChoice("war", "Declare war", mapOf("prestige" to 10, "military" to -20)),
            createChoice("protest", "Formal protest", mapOf("prestige" to 5)),
            createChoice("forgive", "Forgive and forget", mapOf("prestige" to -10))
        )))
        
        randomEvents.add(createEvent("bastard_born", "Bastard Born", "An illegitimate child is born to nobility", EventCategory.ROYALTY, listOf(
            createChoice("recognize", "Recognize child", mapOf("family" to 10, "prestige" to -10)),
            createChoice("hide", "Hide the scandal", mapOf("stress" to 10)),
            createChoice("disown", "Disown completely", mapOf("prestige" to 5, "family" to -15))
        )))
        
        randomEvents.add(createEvent("crusade", "Holy War", "The church calls for a crusade", EventCategory.ROYALTY, listOf(
            createChoice("join", "Join the crusade", mapOf("piety" to 20, "prestige" to 15, "military" to -10)),
            createChoice("refuse", "Refuse to join", mapOf("piety" to -15, "prestige" to -5)),
            createChoice("send", "Send soldiers only", mapOf("military" to -5, "piety" to 10))
        )))
        
        randomEvents.add(createEvent("courtier_scheme", "Courtier's Scheme", "A courtier plots against you", EventCategory.ROYALTY, listOf(
            createChoice("uncover", "Uncover the plot", mapOf("prestige" to 10, "intelligence" to 10)),
            createChoice("pretend", "Pretend ignorance", mapOf("stress" to 15)),
            createChoice("exile", "Exile the courtier", mapOf("prestige" to 5, "reputation" to -5))
        )))
        
        randomEvents.add(createEvent("aging_king", "Aging King", "Your health declines as you age", EventCategory.ROYALTY, listOf(
            createChoice("heir", "Prepare heir for rule", mapOf("stress" to 10)),
            createChoice("enjoy", "Enjoy remaining years", mapOf("prestige" to 5, "stress" to -10)),
            createChoice("search", "Search for cure", mapOf("treasury" to -500))
        )))
    }
    
    private fun addPoliticalEvents() {
        randomEvents.add(createEvent("opposition_leak", "Opposition Leak", "Opposition releases damning emails", EventCategory.POLITICS, listOf(
            createChoice("deny", "Deny everything", mapOf("reputation" to -5)),
            createChoice("apologize", "Apologize", mapOf("reputation" to -10)),
            createChoice("attack", "Attack opponent", mapOf("reputation" to 5, "stress" to 10))
        )))
        
        randomEvents.add(createEvent("town_hall_heckler", "Town Hall Heckler", "Audience member heckles you", EventCategory.POLITICS, listOf(
            createChoice("ignore", "Ignore and continue", mapOf("charisma" to 5)),
            createChoice("confront", "Confront heckler", mapOf(), 0.6f, mapOf("charisma" to 10)),
            createChoice("respond", "Witty comeback", mapOf(), 0.5f, mapOf("charisma" to 15))
        )))
        
        randomEvents.add(createEvent("fundraiser_scandal", "Fundraiser Scandal", "Donor caught in scandal", EventCategory.POLITICS, listOf(
            createChoice("return_money", "Return donations", mapOf("campaign_funds" to -10000, "reputation" to 5)),
            createChoice("keep_money", "Keep donations", mapOf("campaign_funds" to 10000, "reputation" to -10)),
            createChoice("ignore", "Ignore controversy", mapOf("reputation" to -5))
        )))
        
        randomEvents.add(createEvent("debate_gaffe", "Debate Gaffe", "You make a mistake during debate", EventCategory.POLITICS, listOf(
            createChoice("laugh", "Laugh it off", mapOf("charisma" to 5)),
            createChoice("explain", "Explain the context", mapOf("intellect" to 5)),
            createChoice("ignore", "Move on", mapOf("reputation" to -5))
        )))
        
        randomEvents.add(createEvent("endorsement_offer", "Endorsement Offer", "Controversial figure offers support", EventCategory.POLITICS, listOf(
            createChoice("accept", "Accept endorsement", mapOf("reputation" to -10, "polls" to 10)),
            createChoice("reject", "Reject endorsement", mapOf("reputation" to 5, "polls" to -5)),
            createChoice("neutral", "Stay neutral", mapOf("polls" to -2))
        )))
        
        randomEvents.add(createEvent("staff_resignation", "Staff Resignation", "Key aide resigns", EventCategory.POLITICS, listOf(
            createChoice("accept", "Accept resignation", mapOf("stress" to 10)),
            createChoice("negotiate", "Negotiate to keep", mapOf("charisma" to 5)),
            createChoice("publicize", "Make it public", mapOf("reputation" to -5))
        )))
        
        randomEvents.add(createEvent("poll_plummet", "Poll Numbers Plummet", "Your polling numbers drop", EventCategory.POLITICS, listOf(
            createChoice("pivot", "Pivot strategy", mapOf("stress" to 15)),
            createChoice("attack", "Attack opposition", mapOf("reputation" to -5, "polls" to 5)),
            createChoice("ignore", "Ignore the polls", mapOf("stress" to 10))
        )))
        
        randomEvents.add(createEvent("rival_attack_ad", "Rival's Attack Ad", "Opponent runs negative ad", EventCategory.POLITICS, listOf(
            createChoice("respond", "Run response ad", mapOf("campaign_funds" to -5000, "reputation" to 5)),
            createChoice("ignore", "Ignore the ad", mapOf("polls" to -10)),
            createChoice("counter", "Counter-attack", mapOf("campaign_funds" to -3000, "reputation" to -2))
        )))
        
        randomEvents.add(createEvent("natural_disaster", "Natural Disaster", "Crisis hits your area", EventCategory.POLITICS, listOf(
            createChoice("lead", "Lead relief efforts", mapOf("reputation" to 15, "stress" to 10)),
            createChoice("fund", "Send funding", mapOf("campaign_funds" to -10000, "reputation" to 10)),
            createChoice("visit", "Visit affected area", mapOf("reputation" to 8))
        )))
        
        randomEvents.add(createEvent("whistleblower", "Whistleblower", "Insider exposes wrongdoing", EventCategory.POLITICS, listOf(
            createChoice("investigate", "Launch investigation", mapOf("reputation" to 5)),
            createChoice("cover", "Cover it up", mapOf("reputation" to -15, "stress" to 20)),
            createChoice("deny", "Deny allegations", mapOf("reputation" to -5))
        )))
        
        randomEvents.add(createEvent("primary_challenger", "Primary Challenger", "Party member challenges you", EventCategory.POLITICS, listOf(
            createChoice("debate", "Challenge to debate", mapOf("charisma" to 10)),
            createChoice("attack", "Attack their record", mapOf("reputation" to -5)),
            createChoice("unite", "Offer unity ticket", mapOf("reputation" to 5, "stress" to 5))
        )))
        
        randomEvents.add(createEvent("late_night_call", "Late Night Call", "Foreign crisis demands attention", EventCategory.POLITICS, listOf(
            createChoice("engage", "Engage immediately", mapOf("reputation" to 10, "stress" to 15)),
            createChoice("delegate", "Delegate to staff", mapOf("reputation" to -5)),
            createChoice("delay", "Handle in morning", mapOf("reputation" to -3))
        )))
        
        randomEvents.add(createEvent("media_interview", "Media Interview", "Hostile interview request", EventCategory.POLITICS, listOf(
            createChoice("accept", "Accept interview", mapOf("charisma" to 5)),
            createChoice("reject", "Decline interview", mapOf("reputation" to -5)),
            createChoice("condition", "Negotiate conditions", mapOf("charisma" to 3))
        )))
        
        randomEvents.add(createEvent("grassroots_movement", "Grassroots Movement", "Volunteer surge energizes campaign", EventCategory.POLITICS, listOf(
            createChoice("mobilize", "Fully mobilize", mapOf("polls" to 10, "stress" to 5)),
            createChoice("channel", "Channel energy", mapOf("polls" to 5)),
            createChoice("cautious", "Stay cautious", mapOf("polls" to 2))
        )))
        
        randomEvents.add(createEvent("budget_crisis", "Budget Crisis", "Government shutdown threat", EventCategory.POLITICS, listOf(
            createChoice("compromise", "Compromise with opposition", mapOf("reputation" to 5, "stress" to 10)),
            createChoice("stand", "Hold your ground", mapOf("reputation" to -10)),
            createChoice("blame", "Blame opposition", mapOf("reputation" to -5, "polls" to -5))
        )))
        
        randomEvents.add(createEvent("supreme_court", "Supreme Court Vacancy", "Judicial nomination opportunity", EventCategory.POLITICS, listOf(
            createChoice("nominate", "Nominate aligned judge", mapOf("reputation" to 10)),
            createChoice("moderate", "Nominate moderate", mapOf("reputation" to 5)),
            createChoice("delay", "Delay nomination", mapOf("stress" to 10))
        )))
        
        randomEvents.add(createEvent("scandal_photo", "Scandalous Photo", "Old photo resurfaces", EventCategory.POLITICS, listOf(
            createChoice("admit", "Admit and apologize", mapOf("reputation" to -5)),
            createChoice("deny", "Deny authenticity", mapOf("reputation" to -8)),
            createChoice("pivot", "Pivot to issues", mapOf("polls" to -3))
        )))
        
        randomEvents.add(createEvent("union_strike", "Union Strike", "Major labor dispute", EventCategory.POLITICS, listOf(
            createChoice("side_labor", "Side with workers", mapOf("reputation" to 10, "business" to -10)),
            createChoice("side_business", "Side with business", mapOf("reputation" to -10, "business" to 10)),
            createChoice("mediate", "Mediate dispute", mapOf("reputation" to 5, "stress" to 10))
        )))
        
        randomEvents.add(createEvent("foreign_crisis", "Foreign Policy Crisis", "Hostage situation abroad", EventCategory.POLITICS, listOf(
            createChoice("military", "Deploy military", mapOf("reputation" to 10, "stress" to 15)),
            createChoice("negotiate", "Negotiate release", mapOf("reputation" to 5)),
            createChoice("ignore", "Let others handle", mapOf("reputation" to -10))
        )))
        
        randomEvents.add(createEvent("retirement_rumors", "Retirement Rumors", "Speculation about your future", EventCategory.POLITICS, listOf(
            createChoice("deny", "Deny rumors", mapOf("polls" to 5)),
            createChoice("confirm", "Confirm retirement", mapOf("polls" to -15)),
            createChoice("ignore", "No comment", mapOf("stress" to 5))
        )))
    }
    
    private fun addCrimeEvents() {
        randomEvents.add(createEvent("cop_pull_over", "Police Pull Over", "Police signals you to stop", EventCategory.CRIME, listOf(
            createChoice("comply", "Comply calmly", mapOf("heat" to -5), 0.7f),
            createChoice("bribe", "Offer bribe", mapOf("money" to -500, "heat" to -10), 0.5f),
            createChoice("flee", "Attempt flee", mapOf("heat" to 30), 0.3f, mapOf("violence" to 10))
        )))
        
        randomEvents.add(createEvent("rival_gang_turf", "Rival Gang Challenge", "Rivals move on your turf", EventCategory.CRIME, listOf(
            createChoice("fight", "Fight back", mapOf("street_cred" to 10, "health" to -20), 0.5f),
            createChoice("negotiate", "Negotiate peace", mapOf("money" to -1000, "street_cred" to -5), 0.6f),
            createChoice("retreat", "Retreat", mapOf("street_cred" to -10))
        )))
        
        randomEvents.add(createEvent("new_crew_member", "New Crew Member", "Skilled but untrustworthy recruit", EventCategory.CRIME, listOf(
            createChoice("hire", "Hire them", mapOf("crew" to 1), 0.6f),
            createChoice("test", "Test their skills", mapOf("street_cred" to 5), 0.4f),
            createChoice("reject", "Reject application", mapOf())
        )))
        
        randomEvents.add(createEvent("heist_goes_wrong", "Heist Goes Wrong", "Alarm triggers during job", EventCategory.CRIME, listOf(
            createChoice("abort", "Abort mission", mapOf("street_cred" to -5)),
            createChoice("push", "Push through", mapOf("money" to 1000, "heat" to 20), 0.5f),
            createChoice("escape", "Quick escape", mapOf("heat" to 10, "street_cred" to -3))
        )))
        
        randomEvents.add(createEvent("informer_in_crew", "Informer in Crew", "Rat discovered among members", EventCategory.CRIME, listOf(
            createChoice("eliminate", "Eliminate informer", mapOf("street_cred" to 10, "heat" to 15)),
            createChoice("exile", "Exile quietly", mapOf("street_cred" to -5)),
            createChoice("turn", "Turn them", mapOf("intelligence" to 10), 0.4f)
        )))
        
        randomEvents.add(createEvent("drug_lab_explosion", "Drug Lab Explosion", "Lab explodes, evidence everywhere", EventCategory.CRIME, listOf(
            createChoice("flee", "Flee immediately", mapOf("heat" to 20, "money" to -500)),
            createChoice("cleanup", "Clean up evidence", mapOf("heat" to 5, "street_cred" to 5), 0.6f),
            createChoice("hide", "Hide the evidence", mapOf("heat" to 10), 0.7f)
        )))
        
        randomEvents.add(createEvent("loan_shark_call", "Loan Shark Calls", "Payment is due", EventCategory.CRIME, listOf(
            createChoice("pay", "Pay the debt", mapOf("money" to -1000)),
            createChoice("delay", "Ask for extension", mapOf("street_cred" to -5, "stress" to 10)),
            createChoice("default", "Default on debt", mapOf("heat" to 30, "street_cred" to -15))
        )))
        
        randomEvents.add(createEvent("prison_shanking", "Prison Shanking", "Targeted attack in prison", EventCategory.CRIME, listOf(
            createChoice("defend", "Defend yourself", mapOf("health" to -30, "respect" to 10)),
            createChoice("submit", "Submit to attack", mapOf("respect" to -10, "health" to -20)),
            createChoice("report", "Report to guards", mapOf("respect" to -15))
        )))
        
        randomEvents.add(createEvent("guard_shakedown", "Guard Shakedown", "Guard finds contraband", EventCategory.CRIME, listOf(
            createChoice("bribe", "Bribe the guard", mapOf("money" to -200, "heat" to -5), 0.5f),
            createChoice("accept", "Accept punishment", mapOf("health" to -10, "stress" to 10)),
            createChoice("resist", "Resist arrest", mapOf("heat" to 25, "health" to -20))
        )))
        
        randomEvents.add(createEvent("riot_erupts", "Riot Erupts", "Violent uprising in prison", EventCategory.CRIME, listOf(
            createChoice("lead", "Lead the riot", mapOf("respect" to 15, "heat" to 20), 0.4f),
            createChoice("hide", "Hide for safety", mapOf("stress" to 15)),
            createChoice("side", "Side with guards", mapOf("respect" to -20, "heat" to -10))
        )))
        
        randomEvents.add(createEvent("cellmate_suicide", "Cellmate Suicide", "Cellmate kills themselves", EventCategory.CRIME, listOf(
            createChoice("comfort", "Try to help", mapOf("mental_health" to -10, "stress" to 15)),
            createChoice("ignore", "Ignore incident", mapOf("stress" to 5)),
            createChoice("report", "Report to authorities", mapOf("respect" to -10))
        )))
        
        randomEvents.add(createEvent("new_gang_alliance", "New Gang Alliance", "Protection offer from rivals", EventCategory.CRIME, listOf(
            createChoice("accept", "Accept alliance", mapOf("money" to 500, "street_cred" to 5)),
            createChoice("reject", "Reject offer", mapOf("street_cred" to -5)),
            createChoice("counter", "Counter-offer", mapOf("street_cred" to 3), 0.5f)
        )))
        
        randomEvents.add(createEvent("parole_hearing", "Parole Hearing", "Board reviews your case", EventCategory.CRIME, listOf(
            createChoice("plead", "Plead your case", mapOf("stress" to 10), 0.6f),
            createChoice("bribe", "Bribe board member", mapOf("money" to -2000, "heat" to -5), 0.4f),
            createChoice("wait", "Wait for next hearing", mapOf("stress" to 5))
        )))
        
        randomEvents.add(createEvent("escape_tunnel", "Escape Tunnel Found", "Tunnel discovered in cell", EventCategory.CRIME, listOf(
            createChoice("join", "Join escape attempt", mapOf("heat" to 30, "stress" to 20), 0.4f),
            createChoice("report", "Report to guards", mapOf("respect" to -15, "heat" to -10)),
            createChoice("wait", "Wait for better time", mapOf("stress" to 10))
        )))
        
        randomEvents.add(createEvent("visitation_day", "Visitation Day", "Family visits from outside", EventCategory.CRIME, listOf(
            createChoice("meet", "Meet with family", mapOf("stress" to -15, "mental_health" to 10)),
            createChoice("refuse", "Refuse visit", mapOf("stress" to 5)),
            createChoice("information", "Get information", mapOf("intelligence" to 5))
        )))
        
        randomEvents.add(createEvent("prison_job_promotion", "Prison Job Promotion", "Better position offered", EventCategory.CRIME, listOf(
            createChoice("accept", "Accept promotion", mapOf("stress" to 5, "respect" to 5)),
            createChoice("reject", "Reject promotion", mapOf("stress" to -5)),
            createChoice("negotiate", "Negotiate terms", mapOf("charisma" to 5), 0.5f)
        )))
        
        randomEvents.add(createEvent("solitary_confinement", "Solitary Confinement", "Placed in solitary", EventCategory.CRIME, listOf(
            createChoice("endure", "Endure the isolation", mapOf("mental_health" to -15, "stress" to 20)),
            createChoice("break", "Break down", mapOf("mental_health" to -25)),
            createChoice("meditate", "Meditate to cope", mapOf("willpower" to 10, "stress" to 5))
        )))
        
        randomEvents.add(createEvent("rival_gang_leader_killed", "Rival Leader Killed", "Power vacuum emerges", EventCategory.CRIME, listOf(
            createChoice("seize", "Seize power", mapOf("street_cred" to 15, "heat" to 20), 0.4f),
            createChoice("stay", "Stay low", mapOf("street_cred" to -5)),
            createChoice("negotiate", "Negotiate with factions", mapOf("charisma" to 10))
        )))
        
        randomEvents.add(createEvent("smuggled_phone", "Smuggled Phone", "Contact outside world", EventCategory.CRIME, listOf(
            createChoice("use", "Use for coordination", mapOf("heat" to 15, "street_cred" to 5)),
            createChoice("sell", "Sell to others", mapOf("money" to 300)),
            createChoice("destroy", "Destroy evidence", mapOf("heat" to -5))
        )))
        
        randomEvents.add(createEvent("death_in_family", "Death in Family", "Family member passes away", EventCategory.CRIME, listOf(
            createChoice("grieve", "Take time to grieve", mapOf("stress" to 25, "mental_health" to -15)),
            createChoice("continue", "Continue as normal", mapOf("stress" to 10)),
            createChoice("escape", "Use for escape attempt", mapOf("heat" to 30), 0.2f)
        )))
    }
    
    private fun addCrossoverEvents() {
        randomEvents.add(createEvent("noble_ties_underworld", "Noble Ties to Underworld", "Criminal past surfaces", EventCategory.CRIME, listOf(
            createChoice("blackmail", "Use for leverage", mapOf("noble_standing" to 10, "heat" to 10)),
            createChoice("expose", "Expose the noble", mapOf("reputation" to 15, "noble_standing" to -10)),
            createChoice("ignore", "Ignore the information", mapOf())
        ), requiresLifePath = "royalty"))
        
        randomEvents.add(createEvent("politician_secret", "Politician's Secret", "Blackmail opportunity", EventCategory.POLITICS, listOf(
            createChoice("blackmail", "Blackmail for favors", mapOf("political_capital" to 20)),
            createChoice("expose", "Expose publicly", mapOf("reputation" to 15)),
            createChoice("ignore", "Let it go", mapOf("stress" to 5))
        )))
        
        randomEvents.add(createEvent("prison_political_tool", "Prison Political Tool", "Rival arrests you falsely", EventCategory.CRIME, listOf(
            createChoice("fight", "Fight the charges", mapOf("money" to -2000, "stress" to 20)),
            createChoice("bribe", "Bribe officials", mapOf("money" to -3000, "heat" to -10)),
            createChoice("endure", "Endure imprisonment", mapOf("stress" to 25))
        )))
        
        randomEvents.add(createEvent("royal_pardon", "Royal Pardon", "Freedom for a favor", EventCategory.ROYALTY, listOf(
            createChoice("accept", "Accept the deal", mapOf("freedom" to 1, "stress" to 10)),
            createChoice("reject", "Reject and endure", mapOf("stress" to 20)),
            createChoice("negotiate", "Negotiate better terms", mapOf("charisma" to 10), 0.5f)
        ), requiresLifePath = "crime"))
        
        randomEvents.add(createEvent("gang_endorsement", "Gang Endorsement", "Crime lord backs politician", EventCategory.POLITICS, listOf(
            createChoice("accept", "Accept endorsement", mapOf("reputation" to -15, "polls" to 10)),
            createChoice("reject", "Reject criminal backing", mapOf("reputation" to 10, "street_cred" to -15)),
            createChoice("secret", "Secretly accept", mapOf("reputation" to -5, "stress" to 10))
        )))
        
        randomEvents.add(createEvent("crime_lord_funds", "Crime Lord Funds Campaign", "Dirty money offered", EventCategory.POLITICS, listOf(
            createChoice("accept", "Accept money", mapOf("campaign_funds" to 20000, "reputation" to -20)),
            createChoice("reject", "Reject dirty money", mapOf("reputation" to 10)),
            createChoice("launder", "Launder the money", mapOf("money" to 15000, "heat" to 15), 0.5f)
        )))
        
        randomEvents.add(createEvent("witness_protection", "Witness Protection", "New identity offered", EventCategory.CRIME, listOf(
            createChoice("accept", "Accept protection", mapOf("stress" to 15, "freedom" to 1)),
            createChoice("reject", "Stay in current life", mapOf("stress" to 20)),
            createChoice("use", "Use as leverage", mapOf("street_cred" to 10, "heat" to 10))
        )))
        
        randomEvents.add(createEvent("royal_relative_prison", "Royal Relative in Prison", "Family member jailed", EventCategory.ROYALTY, listOf(
            createChoice("bribe", "Bribe for release", mapOf("money" to -5000, "prestige" to -10)),
            createChoice("ignore", "Disown relative", mapOf("prestige" to 5, "family" to -20)),
            createChoice("help", "Help behind scenes", mapOf("money" to -2000, "family" to 10))
        )))
        
        randomEvents.add(createEvent("political_prisoner", "Political Prisoner", "Jailed for dissent", EventCategory.POLITICS, listOf(
            createChoice("advocate", "Advocate for release", mapOf("reputation" to 15, "stress" to 10)),
            createChoice("ignore", "Ignore the case", mapOf("reputation" to -10)),
            createChoice("visit", "Visit in prison", mapOf("reputation" to 8)
        )))
        
        randomEvents.add(createEvent("underworld_informant", "Underworld Informant", "Spy for the crown", EventCategory.CRIME, listOf(
            createChoice("become", "Become informant", mapOf("street_cred" to -20, "heat" to -10)),
            createChoice("refuse", "Refuse to cooperate", mapOf("heat" to 15)),
            createChoice("double", "Double agent", mapOf("stress" to 25), 0.3f)
        ), requiresLifePath = "royalty"))
    }
    
    private fun addRandomLifeEvents() {
        randomEvents.add(createEvent("windfall", "Unexpected Windfall", "Find money on street", EventCategory.RANDOM, listOf(
            createChoice("keep", "Keep the money", mapOf("money" to 500)),
            createChoice("donate", "Donate to charity", mapOf("money" to -500, "reputation" to 10, "piety" to 5))
        )))
        
        randomEvents.add(createEvent("lucky_break", "Lucky Break", "Fortune smiles upon you", EventCategory.RANDOM, listOf(
            createChoice("capitalize", "Capitalize on luck", mapOf("money" to 1000, "reputation" to 5)),
            createChoice("share", "Share with others", mapOf("money" to -300, "reputation" to 10))
        )))
        
        randomEvents.add(createEvent("accident", "Accident", "You get into an accident", EventCategory.LIFE, listOf(
            createChoice("hospital", "Go to hospital", mapOf("money" to -500, "health" to -20)),
            createChoice("home", "Treat at home", mapOf("health" to -15)),
            createChoice("ignore", "Ignore injuries", mapOf("health" to -30))
        )))
        
        randomEvents.add(createEvent("opportunity", "Opportunity Knocks", "Chance for advancement", EventCategory.LIFE, listOf(
            createChoice("seize", "Seize the opportunity", mapOf("stress" to 15, "reputation" to 10), 0.6f),
            createChoice("pass", "Pass on it", mapOf("reputation" to -5)),
            createChoice("delay", "Delay decision", mapOf("stress" to 5))
        )))
        
        randomEvents.add(createEvent("stranger_kindness", "Stranger's Kindness", "Someone helps you", EventCategory.LIFE, listOf(
            createChoice("accept", "Accept help graciously", mapOf("mental_health" to 10)),
            createChoice("reciprocate", "Return the favor", mapOf("money" to -100, "reputation" to 5)),
            createChoice("suspicious", "Remain suspicious", mapOf("stress" to 5))
        )))
        
        randomEvents.add(createEvent("old_friend", "Old Friend Returns", "Friend from past reappears", EventCategory.RELATIONSHIP, listOf(
            createChoice("reconnect", "Reconnect", mapOf("mental_health" to 10, "relationship" to 10)),
            createChoice("cautious", "Be cautious", mapOf("stress" to 5)),
            createChoice("ignore", "Ignore them", mapOf("stress" to -5))
        )))
        
        randomEvents.add(createEvent("unexpected_bill", "Unexpected Bill", "Bill arrives unexpectedly", EventCategory.LIFE, listOf(
            createChoice("pay", "Pay immediately", mapOf("money" to -500, "reputation" to 5)),
            createChoice("delay", "Delay payment", mapOf("stress" to 10)),
            createChoice("dispute", "Dispute the bill", mapOf("stress" to 15), 0.5f)
        )))
        
        randomEvents.add(createEvent("news_shock", "Shocking News", "Dramatic news changes everything", EventCategory.LIFE, listOf(
            createChoice("absorb", "Take time to absorb", mapOf("stress" to 20)),
            createChoice("act", "Act immediately", mapOf("stress" to 15, "reputation" to 5)),
            createChoice("share", "Share with others", mapOf("stress" to 10, "relationship" to 5))
        )))
        
        randomEvents.add(createEvent("new_beginning", "New Beginning", "Start fresh somewhere new", EventCategory.LIFE, listOf(
            createChoice("move", "Move to new place", mapOf("money" to -1000, "stress" to 10)),
            createChoice("stay", "Stay where you are", mapOf("stress" to 5)),
            createChoice("travel", "Travel briefly", mapOf("money" to -300, "mental_health" to 10))
        )))
        
        randomEvents.add(createEvent("unexpected_guest", "Unexpected Guest", "Guest arrives unannounced", EventCategory.RELATIONSHIP, listOf(
            createChoice("welcome", "Welcome them", mapOf("relationship" to 10, "stress" to 5)),
            createChoice("turn", "Turn them away", mapOf("relationship" to -5, "stress" to -5)),
            createChoice("prepare", "Prepare for visit", mapOf("stress" to 10, "relationship" to 5))
        )))
        
        randomEvents.add(createEvent("competition", "Competition", "Enter a competition", EventCategory.LIFE, listOf(
            createChoice("compete", "Compete fiercely", mapOf("stress" to 15, "reputation" to 10), 0.5f),
            createChoice("participate", "Just participate", mapOf("stress" to 5)),
            createChoice("skip", "Skip the event", mapOf("reputation" to -5))
        )))
        
        randomEvents.add(createEvent("surprise_party", "Surprise Party", "Friends throw party", EventCategory.RELATIONSHIP, listOf(
            createChoice("enjoy", "Enjoy the party", mapOf("mental_health" to 15, "relationship" to 10)),
            createChoice("awkward", "Feel awkward", mapOf("stress" to 5)),
            createChoice("leave", "Leave early", mapOf("relationship" to -10))
        )))
        
        randomEvents.add(createEvent("inheritance", "Inheritance", "Relative leaves you something", EventCategory.LIFE, listOf(
            createChoice("accept", "Accept inheritance", mapOf("money" to 5000, "stress" to 5)),
            createChoice("donate", "Donate to charity", mapOf("money" to -5000, "reputation" to 15, "piety" to 10)),
            createChoice("invest", "Invest wisely", mapOf("money" to 3000, "intellect" to 5))
        )))
        
        randomEvents.add(createEvent("promotion", "Promotion", "You get promoted", EventCategory.CAREER, listOf(
            createChoice("accept", "Accept promotion", mapOf("money" to 2000, "reputation" to 10, "stress" to 10)),
            createChoice("decline", "Decline promotion", mapOf("money" to -1000, "stress" to -5)),
            createChoice("negotiate", "Negotiate terms", mapOf("money" to 1500, "charisma" to 5), 0.6f)
        )))
        
        randomEvents.add(createEvent("fired", "You're Fired", "Losing your job", EventCategory.CAREER, listOf(
            createChoice("shock", "React with shock", mapOf("stress" to 25, "reputation" to -5)),
            createChoice("professional", "Stay professional", mapOf("reputation" to 5, "stress" to 15)),
            createChoice("fight", "Fight the termination", mapOf("money" to 1000, "stress" to 20), 0.4f)
        )))
        
        randomEvents.add(createEvent("business_success", "Business Success", "Your business thrives", EventCategory.BUSINESS, listOf(
            createChoice("expand", "Expand operations", mapOf("money" to -2000, "reputation" to 10), 0.6f),
            createChoice("maintain", "Maintain current state", mapOf("money" to 1000)),
            createChoice("celebrate", "Celebrate success", mapOf("money" to -500, "mental_health" to 10))
        )))
        
        randomEvents.add(createEvent("business_failure", "Business Failure", "Your business struggles", EventCategory.BUSINESS, listOf(
            createChoice("pivot", "Pivot to new market", mapOf("money" to -3000, "stress" to 20), 0.5f),
            createChoice("cut", "Cut costs", mapOf("money" to -1000, "reputation" to -5)),
            createChoice("close", "Close the business", mapOf("money" to -5000, "stress" to 15)
        )))
        
        randomEvents.add(createEvent("romantic_encounter", "Romantic Encounter", "Meet someone special", EventCategory.RELATIONSHIP, listOf(
            createChoice("pursue", "Pursue romance", mapOf("relationship" to 10, "stress" to 5), 0.5f),
            createChoice("friend", "Be friends first", mapOf("relationship" to 5)),
            createChoice("ignore", "Ignore the chemistry", mapOf("stress" to -5))
        )))
        
        randomEvents.add(createEvent("breakup", "Breakup", "Relationship ends", EventCategory.RELATIONSHIP, listOf(
            createChoice("grieve", "Take time to grieve", mapOf("stress" to 25, "mental_health" to -15)),
            createChoice("move", "Move on quickly", mapOf("stress" to 10, "relationship" to -10)),
            createChoice("fight", "Fight for relationship", mapOf("stress" to 20, "relationship" to 5), 0.4f)
        )))
        
        randomEvents.add(createEvent("family_gathering", "Family Gathering", "Extended family reunites", EventCategory.RELATIONSHIP, listOf(
            createChoice("participate", "Participate fully", mapOf("relationship" to 15, "stress" to 10)),
            createChoice("observe", "Observe quietly", mapOf("relationship" to 5)),
            createChoice("avoid", "Avoid drama", mapOf("stress" to -5, "relationship" to -5))
        )))
    }
    
    private fun createEvent(
        id: String,
        title: String,
        description: String,
        category: EventCategory,
        choices: List<EventChoice>,
        requiresLifePath: String? = null
    ): GameEvent {
        return GameEvent(
            id = id,
            title = title,
            description = description,
            category = category,
            choices = choices,
            requiresLifePath = requiresLifePath
        )
    }
    
    private fun createChoice(
        id: String,
        text: String,
        effects: Map<String, Int> = emptyMap(),
        successChance: Float = 1.0f,
        skillEffects: Map<String, Int> = emptyMap()
    ): EventChoice {
        val eventEffects = effects.map { (target, value) ->
            val effectType = when {
                target == "money" -> EffectType.MONEY_CHANGE
                target == "health" || target == "mental_health" -> if (value < 0) EffectType.DAMAGE else EffectType.HEAL
                target == "stress" -> EffectType.STRESS_CHANGE
                target == "energy" -> EffectType.ENERGY_CHANGE
                target == "heat" -> EffectType.HEAT_CHANGE
                target == "street_cred" -> EffectType.STREET_CRED_CHANGE
                target == "prestige" -> EffectType.PRESTIGE_CHANGE
                target == "treasury" || target == "campaign_funds" -> EffectType.MONEY_CHANGE
                target == "reputation" || target == "noble_standing" || target == "family" -> EffectType.STAT_CHANGE
                target == "charisma" || target == "intellect" || target == "violence" || target == "willpower" || target == "intelligence" -> EffectType.STAT_CHANGE
                else -> EffectType.STAT_CHANGE
            }
            EventEffect(effectType, target, value)
        }
        
        return EventChoice(
            id = id,
            text = text,
            successChance = successChance,
            effects = eventEffects
        )
    }
    
    fun getRandomEvent(context: EventContext): GameEvent? {
        val availableEvents = randomEvents.filter { event ->
            (event.requiresLifePath == null || event.requiresLifePath == context.lifePath) &&
            context.age in event.minAge..event.maxAge &&
            (event.id !in eventCooldowns || System.currentTimeMillis() - (eventCooldowns[event.id] ?: 0) > event.cooldown * 1000L)
        }
        
        return availableEvents.randomOrNull()?.also {
            eventCooldowns[it.id] = System.currentTimeMillis()
        }
    }
    
    fun getEventById(id: String): GameEvent? = randomEvents.find { it.id == id }
    
    fun getEventsByCategory(category: EventCategory): List<GameEvent> = 
        randomEvents.filter { it.category == category }
    
    fun getAllEvents(): List<GameEvent> = randomEvents.toList()
    
    fun getEventCount(): Int = randomEvents.size
}
