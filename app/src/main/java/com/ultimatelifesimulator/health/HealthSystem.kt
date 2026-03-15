package com.ultimatelifesimulator.health

data class HealthStatus(
    val physicalHealth: Int = 100,
    val mentalHealth: Int = 100,
    val energy: Int = 100,
    val hunger: Int = 0,
    val thirst: Int = 0,
    val pain: Int = 0,
    val immunity: Int = 100,
    val hygiene: Int = 100,
    val sleepQuality: Int = 100,
    val bodyTemperature: Float = 98.6f,
    val socialHealth: Int = 100,
    val selfEsteem: Int = 100,
    val resilience: Int = 50
)

data class Injury(
    val id: String,
    val name: String,
    val description: String,
    val severity: InjurySeverity,
    val bodyPart: BodyPart,
    val healingTimeDays: Int,
    val isChronic: Boolean = false,
    val effects: Map<String, Int> = emptyMap(),
    val isTreated: Boolean = false,
    val scars: Boolean = false,
    val painLevel: Int = 0
)

enum class InjurySeverity(val displayName: String, val damageMultiplier: Float) {
    MINOR("Minor", 0.5f),
    MODERATE("Moderate", 1.0f),
    SEVERE("Severe", 2.0f),
    CRITICAL("Critical", 3.0f)
}

enum class BodyPart(val displayName: String) {
    HEAD("Head"),
    FACE("Face"),
    NECK("Neck"),
    TORSO("Torso"),
    UPPER_BACK("Upper Back"),
    LOWER_BACK("Lower Back"),
    LEFT_ARM("Left Arm"),
    RIGHT_ARM("Right Arm"),
    LEFT_HAND("Left Hand"),
    RIGHT_HAND("Right Hand"),
    ABDOMEN("Abdomen"),
    LEFT_LEG("Left Leg"),
    RIGHT_LEG("Right Leg"),
    LEFT_FOOT("Left Foot"),
    RIGHT_FOOT("Right Foot")
}

data class Illness(
    val id: String,
    val name: String,
    val description: String,
    val category: IllnessCategory,
    val symptoms: List<String>,
    val durationDays: Int,
    val isContagious: Boolean = false,
    val transmissionRate: Float = 0.5f,
    val mortalityRate: Float = 0f,
    val treatments: List<String> = emptyList(),
    val isChronic: Boolean = false,
    val stages: List<IllnessStage> = emptyList(),
    val currentStage: Int = 0
)

data class IllnessStage(
    val name: String,
    val symptomMultiplier: Float = 1.0f,
    val durationDays: Int = 7
)

enum class IllnessCategory(val displayName: String) {
    INFECTION("Infection"),
    CHRONIC("Chronic"),
    MENTAL("Mental Health"),
    INJURY("Injury"),
    ENVIRONMENTAL("Environmental"),
    AUTOIMMUNE("Autoimmune"),
    CANCER("Cancer"),
    CARDIAC("Cardiac"),
    RESPIRATORY("Respiratory"),
    DIGESTIVE("Digestive"),
    NEUROLOGICAL("Neurological"),
    DERMATOLOGICAL("Skin"),
    SEXUALLY_TRANSMITTED("STI")
}

class HealthManager {
    private var healthStatus = HealthStatus()
    private val injuries = mutableListOf<Injury>()
    private val illnesses = mutableListOf<Illness>()
    private val treatments = mutableListOf<Treatment>()
    private val addiction = mutableMapOf<String, Int>()
    private val medication = mutableListOf<String>()
    private var isHospitalized = false
    private var hospitalDaysRemaining = 0
    
    fun getHealthStatus(): HealthStatus = healthStatus
    
    fun takeDamage(amount: Int) {
        healthStatus = healthStatus.copy(
            physicalHealth = (healthStatus.physicalHealth - amount).coerceAtLeast(0)
        )
        if (healthStatus.physicalHealth <= 0) {
            // Handle death
        }
    }
    
    fun heal(amount: Int) {
        healthStatus = healthStatus.copy(
            physicalHealth = (healthStatus.physicalHealth + amount).coerceAtMost(100)
        )
    }
    
    fun changeEnergy(amount: Int) {
        healthStatus = healthStatus.copy(
            energy = (healthStatus.energy + amount).coerceIn(0, 100)
        )
    }
    
    fun changeHunger(amount: Int) {
        healthStatus = healthStatus.copy(
            hunger = (healthStatus.hunger + amount).coerceIn(0, 100)
        )
        processHunger()
    }
    
    fun changeThirst(amount: Int) {
        healthStatus = healthStatus.copy(
            thirst = (healthStatus.thirst + amount).coerceIn(0, 100)
        )
        processThirst()
    }
    
    fun changeStress(amount: Int) {
        val stressReduction = amount * (healthStatus.resilience / 100f)
        healthStatus = healthStatus.copy(
            mentalHealth = (healthStatus.mentalHealth - amount + stressReduction.toInt()).coerceIn(0, 100)
        )
    }
    
    fun improveMentalHealth(amount: Int) {
        healthStatus = healthStatus.copy(
            mentalHealth = (healthStatus.mentalHealth + amount).coerceAtMost(100)
        )
    }
    
    fun changePain(amount: Int) {
        healthStatus = healthStatus.copy(
            pain = (healthStatus.pain + amount).coerceIn(0, 100)
        )
    }
    
    fun changeHygiene(amount: Int) {
        healthStatus = healthStatus.copy(
            hygiene = (healthStatus.hygiene + amount).coerceIn(0, 100)
        )
    }
    
    fun changeImmunity(amount: Int) {
        healthStatus = healthStatus.copy(
            immunity = (healthStatus.immunity + amount).coerceIn(0, 100)
        )
    }
    
    fun changeSleepQuality(amount: Int) {
        healthStatus = healthStatus.copy(
            sleepQuality = (healthStatus.sleepQuality + amount).coerceIn(0, 100)
        )
    }
    
    fun changeSocialHealth(amount: Int) {
        healthStatus = healthStatus.copy(
            socialHealth = (healthStatus.socialHealth + amount).coerceIn(0, 100)
        )
    }
    
    fun changeSelfEsteem(amount: Int) {
        healthStatus = healthStatus.copy(
            selfEsteem = (healthStatus.selfEsteem + amount).coerceIn(0, 100)
        )
    }
    
    fun changeResilience(amount: Int) {
        healthStatus = healthStatus.copy(
            resilience = (healthStatus.resilience + amount).coerceIn(0, 100)
        )
    }
    
    private fun processHunger() {
        when {
            healthStatus.hunger >= 100 -> takeDamage(5)
            healthStatus.hunger >= 80 -> changeEnergy(-5)
            healthStatus.hunger >= 60 -> changeStress(5)
        }
    }
    
    private fun processThirst() {
        when {
            healthStatus.thirst >= 100 -> takeDamage(10)
            healthStatus.thirst >= 80 -> takeDamage(5)
            healthStatus.thirst >= 60 -> changeEnergy(-10)
        }
    }
    
    fun addInjury(injury: Injury) {
        injuries.add(injury)
        if (injury.severity == InjurySeverity.CRITICAL) {
            takeDamage(30)
        } else if (injury.severity == InjurySeverity.SEVERE) {
            takeDamage(20)
        } else if (injury.severity == InjurySeverity.MODERATE) {
            takeDamage(10)
        }
    }
    
    fun treatInjury(injuryId: String, treatment: String) {
        injuries.find { it.id == injuryId }?.let { injury ->
            val index = injuries.indexOf(injury)
            injuries[index] = injury.copy(isTreated = true)
            heal(injury.severity.damageMultiplier.toInt() * 10)
        }
    }
    
    fun removeInjury(injuryId: String) {
        injuries.removeAll { it.id == injuryId }
    }
    
    fun getInjuries(): List<Injury> = injuries.toList()
    
    fun getActiveInjuries(): List<Injury> = injuries.filter { !it.isTreated }
    
    fun addIllness(illness: Illness) {
        illnesses.add(illness)
        if (illness.isContagious) {
            changeImmunity(-10)
        }
    }
    
    fun treatIllness(illnessId: String, treatment: String) {
        illnesses.find { it.id == illnessId }?.let { illness ->
            val index = illnesses.indexOf(illness)
            if (illness.treatments.contains(treatment)) {
                illnesses.removeAt(index)
                heal(10)
            }
        }
    }
    
    fun removeIllness(illnessId: String) {
        illnesses.removeAll { it.id == illnessId }
    }
    
    fun getIllnesses(): List<Illness> = illnesses.toList()
    
    fun getActiveIllnesses(): List<Illness> = illnesses.filter { !it.isChronic || it.currentStage < it.stages.size - 1 }
    
    fun addAddiction(substance: String, level: Int = 10) {
        addiction[substance] = (addiction[substance] ?: 0) + level
        if (addiction[substance]!! >= 100) {
            addiction[substance] = 100
        }
    }
    
    fun reduceAddiction(substance: String, amount: Int) {
        addiction[substance] = (addiction[substance] ?: 0) - amount
        if ((addiction[substance] ?: 0) <= 0) {
            addiction.remove(substance)
        }
    }
    
    fun getAddictionLevel(substance: String): Int = addiction[substance] ?: 0
    
    fun hasAddiction(): Boolean = addiction.isNotEmpty()
    
    fun takeMedication(medicationName: String) {
        medication.add(medicationName)
    }
    
    fun stopMedication(medicationName: String) {
        medication.remove(medicationName)
    }
    
    fun getMedications(): List<String> = medication.toList()
    
    fun hospitalize(days: Int) {
        isHospitalized = true
        hospitalDaysRemaining = days
    }
    
    fun discharge() {
        isHospitalized = false
        hospitalDaysRemaining = 0
        heal(50)
    }
    
    fun processDaily() {
        if (isHospitalized) {
            hospitalDaysRemaining--
            heal(10)
            if (hospitalDaysRemaining <= 0) {
                discharge()
            }
        }
        
        // Process injuries healing
        injuries.filter { !it.isTreated && !it.isChronic }.forEach { injury ->
            val index = injuries.indexOf(injury)
            injuries[index] = injury.copy(healingTimeDays = injury.healingTimeDays - 1)
            if (injuries[index].healingTimeDays <= 0) {
                injuries[index] = injuries[index].copy(scars = true)
            }
        }
        
        // Process illnesses progression
        illnesses.forEach { illness ->
            if (illness.stages.isNotEmpty() && illness.currentStage < illness.stages.size - 1) {
                val index = illnesses.indexOf(illness)
                illnesses[index] = illness.copy(currentStage = illness.currentStage + 1)
            }
        }
        
        // Daily health effects
        if (healthStatus.hygiene < 20) {
            changeImmunity(-5)
        }
        
        if (healthStatus.sleepQuality < 30) {
            changeEnergy(-10)
            changeStress(5)
        }
    }
    
    fun isDead(): Boolean = healthStatus.physicalHealth <= 0
    
    fun getOverallHealth(): Int = (healthStatus.physicalHealth + healthStatus.mentalHealth + healthStatus.socialHealth) / 3
    
    fun getHealthGrade(): HealthGrade {
        val overall = getOverallHealth()
        return when {
            overall >= 90 -> HealthGrade.EXCELLENT
            overall >= 70 -> HealthGrade.GOOD
            overall >= 50 -> HealthGrade.FAIR
            overall >= 30 -> HealthGrade.POOR
            else -> HealthGrade.CRITICAL
        }
    }
}

enum class HealthGrade(val displayName: String) {
    EXCELLENT("Excellent"),
    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor"),
    CRITICAL("Critical")
}

data class Treatment(
    val id: String,
    val name: String,
    val type: TreatmentType,
    val cost: Double,
    val effectiveness: Int,
    val sideEffects: List<String> = emptyList(),
    val duration: Int = 1,
    val requiresProfessional: Boolean = true
)

enum class TreatmentType(val displayName: String) {
    MEDICATION("Medication"),
    SURGERY("Surgery"),
    THERAPY("Therapy"),
    LIFESTYLE("Lifestyle Change"),
    ALTERNATIVE("Alternative Medicine"),
    HOME_REMEDY("Home Remedy"),
    EMERGENCY("Emergency Care"),
    REHABILITATION("Rehabilitation"),
    HOSPITALIZATION("Hospitalization")
}

object IllnessRegistry {
    val allIllnesses = listOf(
        // Infections (10)
        Illness("cold", "Common Cold", "A viral infection of the upper respiratory tract", 
            IllnessCategory.INFECTION, listOf("cough", "sneeze", "fatigue", "runny_nose"), 7, true, 0.8f, 0f,
            treatments = listOf("rest", "fluids", "vitamin_c")),
        Illness("flu", "Influenza", "Serious viral respiratory illness", 
            IllnessCategory.INFECTION, listOf("fever", "body_aches", "fatigue", "cough"), 10, true, 0.6f, 0.01f,
            treatments = listOf("rest", "fluids", "tamiflu")),
        Illness("pneumonia", "Pneumonia", "Lung infection causing inflammation", 
            IllnessCategory.INFECTION, listOf("cough", "fever", "difficulty_breathing", "chest_pain"), 21, true, 0.4f, 0.1f,
            treatments = listOf("antibiotics", "hospitalization"), stages = listOf(
                IllnessStage("Mild", 0.5f, 7),
                IllnessStage("Moderate", 1.0f, 7),
                IllnessStage("Severe", 1.5f, 7)
            )),
        Illness("strep_throat", "Strep Throat", "Bacterial throat infection", 
            IllnessCategory.INFECTION, listOf("sore_throat", "fever", "swollen_glands"), 7, true, 0.5f, 0f,
            treatments = listOf("antibiotics")),
        Illness("tuberculosis", "Tuberculosis", "Serious bacterial lung infection", 
            IllnessCategory.INFECTION, listOf("cough", "fever", "night_sweats", "weight_loss"), 180, true, 0.3f, 0.15f,
            treatments = listOf("long_term_antibiotics", "hospitalization"), isChronic = true),
        Illness("covid19", "COVID-19", "Coronavirus disease 2019", 
            IllnessCategory.INFECTION, listOf("fever", "cough", "loss_of_taste", "fatigue"), 14, true, 0.5f, 0.02f,
            treatments = listOf("rest", "isolation", "ventilator")),
        Illness("hiv", "HIV/AIDS", "Human Immunodeficiency Virus", 
            IllnessCategory.INFECTION, listOf("flu_symptoms", "fatigue", "weight_loss"), -1, true, 0.1f, 0.05f,
            treatments = listOf("antiretroviral_therapy"), isChronic = true),
        Illness("hepatitis_b", "Hepatitis B", "Liver infection", 
            IllnessCategory.INFECTION, listOf("jaundice", "fatigue", "nausea"), 90, true, 0.2f, 0.01f,
            treatments = listOf("antiviral_medication", "rest")),
        Illness("malaria", "Malaria", "Parasitic disease from mosquito bites", 
            IllnessCategory.INFECTION, listOf("fever", "chills", "sweats"), 14, true, 0.3f, 0.01f,
            treatments = listOf("antimalarial_drugs")),
        Illness("cholera", "Cholera", "Severe diarrheal disease", 
            IllnessCategory.INFECTION, listOf("diarrhea", "vomiting", "dehydration"), 7, true, 0.4f, 0.05f,
            treatments = listOf("rehydration", "antibiotics")),
        
        // Chronic Conditions (15)
        Illness("depression", "Depression", "Persistent feelings of sadness", 
            IllnessCategory.MENTAL, listOf("sadness", "fatigue", "insomnia", "loss_of_interest"), -1, false, 0f, 0.05f,
            treatments = listOf("therapy", "antidepressants", "exercise"), isChronic = true,
            stages = listOf(
                IllnessStage("Mild", 0.5f, 30),
                IllnessStage("Moderate", 1.0f, 60),
                IllnessStage("Severe", 1.5f, 90)
            )),
        Illness("anxiety", "Anxiety Disorder", "Persistent worry and fear", 
            IllnessCategory.MENTAL, listOf("worry", "restlessness", "insomnia", "panic"), -1, false, 0f, 0.01f,
            treatments = listOf("therapy", "anxiolytics", "meditation"), isChronic = true),
        Illness("bipolar", "Bipolar Disorder", "Mood swings between depression and mania", 
            IllnessCategory.MENTAL, listOf("mood_swings", "energy_changes", "impulsivity"), -1, false, 0f, 0.02f,
            treatments = listOf("mood_stabilizers", "therapy"), isChronic = true),
        Illness("ptsd", "PTSD", "Post-traumatic stress disorder", 
            IllnessCategory.MENTAL, listOf("flashbacks", "nightmares", "avoidance", "hypervigilance"), -1, false, 0f, 0.02f,
            treatments = listOf("therapy", "medication"), isChronic = true),
        Illness("schizophrenia", "Schizophrenia", "Psychotic disorder", 
            IllnessCategory.MENTAL, listOf("hallucinations", "delusions", "disorganized_thinking"), -1, false, 0f, 0.02f,
            treatments = listOf("antipsychotics", "therapy"), isChronic = true),
        Illness("diabetes_type1", "Type 1 Diabetes", "Autoimmune diabetes", 
            IllnessCategory.CHRONIC, listOf("thirst", "fatigue", "weight_loss"), -1, false, 0f, 0.03f,
            treatments = listOf("insulin", "diet_management"), isChronic = true),
        Illness("diabetes_type2", "Type 2 Diabetes", "Metabolic diabetes", 
            IllnessCategory.CHRONIC, listOf("thirst", "fatigue", "slow_healing"), -1, false, 0f, 0.05f,
            treatments = listOf("medication", "diet", "exercise"), isChronic = true),
        Illness("hypertension", "Hypertension", "High blood pressure", 
            IllnessCategory.CARDIAC, listOf("headache", "dizziness", "nosebleeds"), -1, false, 0f, 0.02f,
            treatments = listOf("blood_pressure_medication", "diet", "exercise"), isChronic = true),
        Illness("heart_disease", "Heart Disease", "Various heart conditions", 
            IllnessCategory.CARDIAC, listOf("chest_pain", "shortness_breath", "fatigue"), -1, false, 0f, 0.1f,
            treatments = listOf("medication", "surgery", "lifestyle_change"), isChronic = true,
            stages = listOf(
                IllnessStage("Early", 0.5f, 365),
                IllnessStage("Moderate", 1.0f, 365),
                IllnessStage("Advanced", 1.5f, 365),
                IllnessStage("Severe", 2.0f, 365)
            )),
        Illness("asthma", "Asthma", "Respiratory condition", 
            IllnessCategory.RESPIRATORY, listOf("shortness_breath", "wheezing", "coughing"), -1, false, 0f, 0.01f,
            treatments = listOf("inhaler", "avoid_allergens"), isChronic = true),
        Illness("arthritis", "Arthritis", "Joint inflammation", 
            IllnessCategory.CHronic, listOf("joint_pain", "stiffness", "swelling"), -1, false, 0f, 0.01f,
            treatments = listOf("pain_medication", "physical_therapy"), isChronic = true),
        Illness("cancer", "Cancer", "Malignant cell growth", 
            IllnessCategory.CANCER, listOf("various_symptoms", "weight_loss", "fatigue"), -1, false, 0f, 0.3f,
            treatments = listOf("chemotherapy", "radiation", "surgery"), isChronic = true,
            stages = listOf(
                IllnessStage("Stage 1", 0.5f, 90),
                IllnessStage("Stage 2", 1.0f, 90),
                IllnessStage("Stage 3", 1.5f, 90),
                IllnessStage("Stage 4", 2.0f, 90)
            )),
        Illness("copd", "COPD", "Chronic obstructive pulmonary disease", 
            IllnessCategory.RESPIRATORY, listOf("shortness_breath", "chronic_cough", "wheezing"), -1, false, 0f, 0.05f,
            treatments = listOf("inhaler", "oxygen_therapy"), isChronic = true),
        Illness("lupus", "Lupus", "Autoimmune disease", 
            IllnessCategory.AUTOIMMUNE, listOf("fatigue", "joint_pain", "rash"), -1, false, 0f, 0.02f,
            treatments = listOf("immunosuppressants", "anti-inflammatories"), isChronic = true),
        Illness("alzheimer", "Alzheimer's Disease", "Progressive memory loss", 
            IllnessCategory.NEUROLOGICAL, listOf("memory_loss", "confusion", "personality_changes"), -1, false, 0f, 0.1f,
            treatments = listOf("medication", "care"), isChronic = true)
    )
    
    val commonIllnesses = allIllnesses.filter { it.duration > 0 && it.duration < 30 }
    val chronicIllnesses = allIllnesses.filter { it.isChronic }
    val mentalIllnesses = allIllnesses.filter { it.category == IllnessCategory.MENTAL }
    
    fun getIllness(id: String): Illness? = allIllnesses.find { it.id == id }
    
    fun getIllnessesByCategory(category: IllnessCategory): List<Illness> = 
        allIllnesses.filter { it.category == category }
}

object TreatmentRegistry {
    val treatments = listOf(
        // Medical Treatments
        Treatment("checkup", "General Checkup", TreatmentType.MEDICATION, 100.0, 20, listOf()),
        Treatment("antibiotics", "Antibiotics", TreatmentType.MEDICATION, 50.0, 60, listOf("upset_stomach", "allergic_reaction")),
        Treatment("surgery", "Surgery", TreatmentType.SURGERY, 10000.0, 90, listOf("infection", "complications"), 30, true),
        Treatment("therapy", "Talk Therapy", TreatmentType.THERAPY, 150.0, 50, listOf(), 1, true),
        Treatment("psychiatry", "Psychiatric Care", TreatmentType.THERAPY, 200.0, 60, listOf("medication_side_effects"), 1, true),
        Treatment("physical_therapy", "Physical Therapy", TreatmentType.REHABILITATION, 100.0, 70, listOf("soreness"), 14, true),
        Treatment("emergency_room", "Emergency Room", TreatmentType.EMERGENCY, 1000.0, 80, listOf(), 1, true),
        Treatment("hospitalization", "Hospitalization", TreatmentType.HOSPITALIZATION, 2000.0, 85, listOf("infection"), 7, true),
        Treatment("rehabilitation", "Rehabilitation", TreatmentType.REHABILITATION, 3000.0, 75, listOf("exhaustion"), 30, true),
        
        // Medications
        Treatment("painkillers", "Painkillers", TreatmentType.MEDICATION, 30.0, 40, listOf("addiction", "drowsiness")),
        Treatment("antidepressants", "Antidepressants", TreatmentType.MEDICATION, 50.0, 55, listOf("weight_gain", "sexual_dysfunction"), 30),
        Treatment("anxiolytics", "Anti-Anxiety Meds", TreatmentType.MEDICATION, 40.0, 50, listOf("drowsiness", "addiction"), 30),
        Treatment("insulin", "Insulin", TreatmentType.MEDICATION, 100.0, 80, listOf("hypoglycemia"), 30, true),
        Treatment("blood_pressure", "Blood Pressure Meds", TreatmentType.MEDICATION, 40.0, 70, listOf("dizziness"), 30),
        
        // Alternative Medicine
        Treatment("acupuncture", "Acupuncture", TreatmentType.ALTERNATIVE, 80.0, 30, listOf("pain"), 1),
        Treatment("chiropractic", "Chiropractic Care", TreatmentType.ALTERNATIVE, 60.0, 35, listOf("pain"), 1),
        Treatment("massage", "Massage Therapy", TreatmentType.ALTERNATIVE, 70.0, 40, listOf("soreness"), 1),
        Treatment("herbal", "Herbal Remedies", TreatmentType.ALTERNATIVE, 30.0, 25, listOf("allergic_reaction")),
        Treatment("meditation", "Meditation", TreatmentType.ALTERNATIVE, 0.0, 35, listOf()),
        Treatment("yoga", "Yoga", TreatmentType.ALTERNATIVE, 0.0, 40, listOf("injury")),
        
        // Home Remedies
        Treatment("rest", "Rest", TreatmentType.HOME_REMEDY, 0.0, 30, listOf()),
        Treatment("fluids", "Fluid Intake", TreatmentType.HOME_REMEDY, 0.0, 25, listOf()),
        Treatment("hot_soup", "Hot Soup", TreatmentType.HOME_REMEDY, 0.0, 20, listOf()),
        Treatment("vitamin_c", "Vitamin C", TreatmentType.HOME_REMEDY, 10.0, 20, listOf()),
        
        // Lifestyle Changes
        Treatment("exercise", "Exercise", TreatmentType.LIFESTYLE, 0.0, 45, listOf("injury"), 1),
        Treatment("diet", "Diet Change", TreatmentType.LIFESTYLE, 0.0, 50, listOf("hunger"), 30),
        Treatment("sleep_more", "More Sleep", TreatmentType.LIFESTYLE, 0.0, 40, listOf()),
        Treatment("quit_smoking", "Quit Smoking", TreatmentType.LIFESTYLE, 0.0, 60, listOf("withdrawal"), 90),
        Treatment("reduce_stress", "Stress Reduction", TreatmentType.LIFESTYLE, 0.0, 45, listOf())
    )
    
    fun getTreatment(id: String): Treatment? = treatments.find { it.id == id }
}
