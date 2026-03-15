package com.ultimatelifesimulator.relationships

import java.util.UUID

data class Relationship(
    val id: String = UUID.randomUUID().toString(),
    val npcId: String,
    val npcName: String,
    val type: RelationshipType,
    val score: Int = 0,
    val trust: Int = 50,
    val fear: Int = 0,
    val respect: Int = 50,
    val loyalty: Int = 50,
    val debt: Int = 0,
    val memories: List<RelationshipMemory> = emptyList(),
    val isRomantic: Boolean = false,
    val isFamily: Boolean = false,
    val isKnownSecret: Boolean = false,
    val lastInteraction: Long = System.currentTimeMillis(),
    val meetingCount: Int = 0,
    val romanticAffinity: Int = 0,
    val children: List<String> = emptyList(),
    val marriageYear: Int? = null,
    val isAffair: Boolean = false,
    val workplaceType: WorkplaceType? = null
)

data class RelationshipMemory(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val emotionalWeight: Int,
    val timestamp: Long = System.currentTimeMillis()
)

enum class RelationshipType(val displayName: String, val category: RelationshipCategory) {
    // Familial
    PARENT("Parent", RelationshipCategory.FAMILY),
    CHILD("Child", RelationshipCategory.FAMILY),
    SIBLING("Sibling", RelationshipCategory.FAMILY),
    EXTENDED_FAMILY("Extended Family", RelationshipCategory.FAMILY),
    IN_LAW("In-Law", RelationshipCategory.FAMILY),
    GRANDPARENT("Grandparent", RelationshipCategory.FAMILY),
    GRANDCHILD("Grandchild", RelationshipCategory.FAMILY),
    STEP_PARENT("Step-Parent", RelationshipCategory.FAMILY),
    STEP_CHILD("Step-Child", RelationshipCategory.FAMILY),
    ADOPTIVE_PARENT("Adoptive Parent", RelationshipCategory.FAMILY),
    ADOPTIVE_CHILD("Adoptive Child", RelationshipCategory.FAMILY),
    FOSTER_PARENT("Foster Parent", RelationshipCategory.FAMILY),
    FOSTER_CHILD("Foster Child", RelationshipCategory.FAMILY),
    GODPARENT("Godparent", RelationshipCategory.FAMILY),
    GODCHILD("Godchild", RelationshipCategory.FAMILY),
    
    // Romantic
    SPOUSE("Spouse", RelationshipCategory.ROMANTIC),
    LOVER("Lover", RelationshipCategory.ROMANTIC),
    EX_LOVER("Ex-Lover", RelationshipCategory.ROMANTIC),
    FIANCÉ("Fiancé(e)", RelationshipCategory.ROMANTIC),
    PARAMOUR("Paramour", RelationshipCategory.ROMANTIC),
    UNREQUITED_LOVE("Unrequited Love", RelationshipCategory.ROMANTIC),
    ARRANGED_PARTNER("Arranged Partner", RelationshipCategory.ROMANTIC),
    ONE_NIGHT_STAND("One-Night Stand", RelationshipCategory.ROMANTIC),
    CASUAL_DATING("Casual Dating", RelationshipCategory.ROMANTIC),
    LONG_DISTANCE("Long-Distance", RelationshipCategory.ROMANTIC),
    OPEN_RELATIONSHIP("Open Relationship", RelationshipCategory.ROMANTIC),
    POLYAMOROUS("Polyamorous Partner", RelationshipCategory.ROMANTIC),
    SUGAR_DADDY("Sugar Daddy/Momma", RelationshipCategory.ROMANTIC),
    BOYFRIEND("Boyfriend/Girlfriend", RelationshipCategory.ROMANTIC),
    
    // Professional
    MENTOR("Mentor", RelationshipCategory.PROFESSIONAL),
    PROTÉGÉ("Protégé", RelationshipCategory.PROFESSIONAL),
    BOSS("Boss", RelationshipCategory.PROFESSIONAL),
    EMPLOYEE("Employee", RelationshipCategory.PROFESSIONAL),
    COWORKER("Coworker", RelationshipCategory.PROFESSIONAL),
    BUSINESS_PARTNER("Business Partner", RelationshipCategory.PROFESSIONAL),
    CLIENT("Client", RelationshipCategory.PROFESSIONAL),
    SUPPLIER("Supplier", RelationshipCategory.PROFESSIONAL),
    COMPETITOR("Competitor", RelationshipCategory.PROFESSIONAL),
    INVESTOR("Investor", RelationshipCategory.PROFESSIONAL),
    ADVISOR("Advisor/Consultant", RelationshipCategory.PROFESSIONAL),
    TEACHER("Teacher", RelationshipCategory.PROFESSIONAL),
    STUDENT("Student", RelationshipCategory.PROFESSIONAL),
    COACH("Coach", RelationshipCategory.PROFESSIONAL),
    ATHLETE("Athlete", RelationshipCategory.PROFESSIONAL),
    DOCTOR("Doctor", RelationshipCategory.PROFESSIONAL),
    PATIENT("Patient", RelationshipCategory.PROFESSIONAL),
    LAWYER("Lawyer", RelationshipCategory.PROFESSIONAL),
    
    // Criminal
    CRIMINAL_PARTNER("Partner in Crime", RelationshipCategory.CRIMINAL),
    CELLMATE("Cellmate", RelationshipCategory.CRIMINAL),
    INFORMANT("Informant", RelationshipCategory.CRIMINAL),
    FENCE("Fence", RelationshipCategory.CRIMINAL),
    MARK("Mark/Victim", RelationshipCategory.CRIMINAL),
    GANG_LEADER("Gang Leader", RelationshipCategory.CRIMINAL),
    GANG_MEMBER("Gang Member", RelationshipCategory.CRIMINAL),
    SHOT_CALLER("Shot Caller", RelationshipCategory.CRIMINAL),
    ENFORCER("Enforcer", RelationshipCategory.CRIMINAL),
    DRUG_CONNECT("Drug Connect", RelationshipCategory.CRIMINAL),
    WEAPON_SUPPLIER("Weapon Supplier", RelationshipCategory.CRIMINAL),
    CORRUPT_COP("Corrupt Cop", RelationshipCategory.CRIMINAL),
    PRISON_GUARD("Prison Guard", RelationshipCategory.CRIMINAL),
    
    // Social
    FRIEND("Friend", RelationshipCategory.SOCIAL),
    BEST_FRIEND("Best Friend", RelationshipCategory.SOCIAL),
    CHILDHOOD_FRIEND("Childhood Friend", RelationshipCategory.SOCIAL),
    NEIGHBOR("Neighbor", RelationshipCategory.SOCIAL),
    GYM_BUDDY("Gym Buddy", RelationshipCategory.SOCIAL),
    CLUB_MEMBER("Club Member", RelationshipCategory.SOCIAL),
    FELLOW_HOBBYIST("Fellow Hobbyist", RelationshipCategory.SOCIAL),
    ONLINE_FRIEND("Online Friend", RelationshipCategory.SOCIAL),
    PEN_PAL("Pen Pal", RelationshipCategory.SOCIAL),
    ACQUAINTANCE("Acquaintance", RelationshipCategory.SOCIAL),
    
    // Rival/Enemy
    RIVAL("Rival", RelationshipCategory.RIVAL),
    ENEMY("Enemy", RelationshipCategory.RIVAL),
    NEMESIS("Nemesis", RelationshipCategory.RIVAL),
    
    // Unusual
    PET("Pet", RelationshipCategory.UNUSUAL),
    SPIRITUAL_GUIDE("Spiritual Guide", RelationshipCategory.UNUSUAL),
    CELEBRITY("Celebrity Idol", RelationshipCategory.UNUSUAL),
    STRANGER("Stranger", RelationshipCategory.SOCIAL)
}

enum class RelationshipCategory(val displayName: String) {
    FAMILY("Family"),
    ROMANTIC("Romantic"),
    PROFESSIONAL("Professional"),
    CRIMINAL("Criminal"),
    SOCIAL("Social"),
    RIVAL("Rival"),
    UNUSUAL("Unusual")
}

enum class WorkplaceType {
    SAME_COMPANY,
    COMPETITOR,
    CLIENT_COMPANY,
    FREELANCE,
    SAME_PROFESSION
}

class RelationshipManager {
    private val relationships = mutableMapOf<String, Relationship>()
    
    fun addRelationship(npcId: String, npcName: String, type: RelationshipType): Relationship {
        val relationship = Relationship(
            npcId = npcId,
            npcName = npcName,
            type = type,
            isFamily = isFamilyRelationship(type),
            isRomantic = isRomanticRelationship(type)
        )
        relationships[npcId] = relationship
        return relationship
    }
    
    fun getRelationship(npcId: String): Relationship? = relationships[npcId]
    
    fun getAllRelationships(): List<Relationship> = relationships.values.toList()
    
    fun getRelationshipsByType(type: RelationshipType): List<Relationship> =
        relationships.values.filter { it.type == type }
    
    fun getRelationshipsByCategory(category: RelationshipCategory): List<Relationship> =
        relationships.values.filter { it.type.category == category }
    
    fun modifyScore(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(score = (rel.score + amount).coerceIn(-100, 100))
        }
    }
    
    fun modifyTrust(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(trust = (rel.trust + amount).coerceIn(0, 100))
        }
    }
    
    fun modifyFear(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(fear = (rel.fear + amount).coerceIn(0, 100))
        }
    }
    
    fun modifyRespect(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(respect = (rel.respect + amount).coerceIn(0, 100))
        }
    }
    
    fun modifyLoyalty(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(loyalty = (rel.loyalty + amount).coerceIn(0, 100))
        }
    }
    
    fun addMemory(npcId: String, memory: String, emotionalWeight: Int = 50) {
        relationships[npcId]?.let { rel ->
            val newMemory = RelationshipMemory(description = memory, emotionalWeight = emotionalWeight)
            val newMemories = rel.memories + newMemory
            relationships[npcId] = rel.copy(memories = newMemories.takeLast(20))
        }
    }
    
    fun addDebt(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(debt = rel.debt + amount)
        }
    }
    
    fun repayDebt(npcId: String, amount: Int) {
        relationships[npcId]?.let { rel ->
            val newDebt = (rel.debt - amount).coerceAtLeast(0)
            relationships[npcId] = rel.copy(debt = newDebt)
        }
    }
    
    fun endRelationship(npcId: String) {
        relationships.remove(npcId)
    }
    
    fun changeRelationshipType(npcId: String, newType: RelationshipType) {
        relationships[npcId]?.let { rel ->
            relationships[npcId] = rel.copy(
                type = newType,
                isFamily = isFamilyRelationship(newType),
                isRomantic = isRomanticRelationship(newType)
            )
        }
    }
    
    private fun isFamilyRelationship(type: RelationshipType): Boolean =
        type.category == RelationshipCategory.FAMILY
    
    private fun isRomanticRelationship(type: RelationshipType): Boolean =
        type.category == RelationshipCategory.ROMANTIC
    
    fun getFamilyMembers(): List<Relationship> = relationships.values.filter { it.isFamily }
    
    fun getFriends(): List<Relationship> = relationships.values.filter { 
        it.type in listOf(RelationshipType.FRIEND, RelationshipType.BEST_FRIEND, RelationshipType.CHILDHOOD_FRIEND) 
    }
    
    fun getRivals(): List<Relationship> = relationships.values.filter { 
        it.type in listOf(RelationshipType.RIVAL, RelationshipType.ENEMY, RelationshipType.NEMESIS) 
    }
    
    fun getEnemies(): List<Relationship> = relationships.values.filter { 
        it.type in listOf(RelationshipType.ENEMY, RelationshipType.NEMESIS) 
    }
    
    fun getRomanticPartners(): List<Relationship> = relationships.values.filter { it.isRomantic }
    
    fun getCoworkers(): List<Relationship> = relationships.values.filter { 
        it.type.category == RelationshipCategory.PROFESSIONAL 
    }
    
    fun getCriminalContacts(): List<Relationship> = relationships.values.filter { 
        it.type.category == RelationshipCategory.CRIMINAL 
    }
    
    fun getTotalRelationshipScore(): Int = relationships.values.sumOf { it.score }
    
    fun getAverageTrust(): Int = if (relationships.isEmpty()) 0 else relationships.values.sumOf { it.trust } / relationships.size
    
    fun hasCloseRelationships(): Boolean = relationships.values.any { it.score >= 75 }
    
    fun hasEnemy(): Boolean = relationships.values.any { it.score <= -75 }
    
    fun getRelationshipStrength(npcId: String): RelationshipStrength {
        val rel = relationships[npcId] ?: return RelationshipStrength.STRANGER
        val avg = (rel.score + rel.trust + rel.loyalty + rel.respect) / 4
        return when {
            avg >= 80 -> RelationshipStrength.BEST
            avg >= 60 -> RelationshipStrength.CLOSE
            avg >= 40 -> RelationshipStrength.NORMAL
            avg >= 20 -> RelationshipStrength.DISTANT
            avg >= 0 -> RelationshipStrength.STRANGER
            avg >= -20 -> RelationshipStrength.ACQUAINTANCE
            avg >= -40 -> RelationshipStrength.UNFRIENDLY
            avg >= -60 -> RelationshipStrength.HOSTILE
            else -> RelationshipStrength.ENEMY
        }
    }
}

enum class RelationshipStrength {
    BEST, CLOSE, NORMAL, DISTANT, STRANGER, ACQUAINTANCE, UNFRIENDLY, HOSTILE, ENEMY
}

class NPCGenerator {
    private val firstNames = listOf("John", "Mary", "James", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Karen")
    private val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore")
    private val occupations = listOf("Farmer", "Merchant", "Soldier", "Scholar", "Craftsman", "Noble", "Clergy", "Criminal", "Artist", "Politician", "Doctor", "Lawyer", "Banker", "Worker", "Servant")
    
    fun generateName(): String {
        return "${firstNames.random()} ${lastNames.random()}"
    }
    
    fun generateBackground(): NPCBackground {
        return NPCBackground(
            name = generateName(),
            age = (18..70).random(),
            occupation = occupations.random(),
            birthplace = listOf("Capital City", "Small Town", "Village", "Foreign Land").random(),
            familyClass = listOf("Peasant", "Commoner", "Merchant", "Noble").random(),
            personalityTraits = listOf("Friendly", "Hostile", "Greedy", "Kind", "Suspicious", "Open").random(),
            goals = listOf("Wealth", "Power", "Love", "Peace", "Revenge", "Knowledge").random()
        )
    }
}

data class NPCBackground(
    val name: String,
    val age: Int,
    val occupation: String,
    val birthplace: String,
    val familyClass: String,
    val personalityTraits: String,
    val goals: String
)
