package com.ultimatelifesimulator.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ultimatelifesimulator.core.GameEngine
import com.ultimatelifesimulator.core.repository.GameRepository
import com.ultimatelifesimulator.character.skills.Skill
import com.ultimatelifesimulator.character.skills.SkillRegistry
import com.ultimatelifesimulator.character.traits.Trait
import com.ultimatelifesimulator.character.traits.TraitRegistry
import com.ultimatelifesimulator.life.LifePath
import com.ultimatelifesimulator.life.LifePathType
import com.ultimatelifesimulator.life.LifeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterInfo(
    val name: String = "Player",
    val age: Int = 18,
    val day: Int = 1,
    val year: Int = 2024,
    val lifePath: String = "None"
)

data class CharacterStats(
    val health: Int = 100,
    val energy: Int = 100,
    val stress: Int = 0,
    val charisma: Int = 50,
    val intellect: Int = 50,
    val cunning: Int = 50,
    val violence: Int = 50,
    val stealth: Int = 50,
    val perception: Int = 50,
    val willpower: Int = 50
)

data class CharacterDisplay(
    val characterInfo: CharacterInfo,
    val stats: CharacterStats,
    val skills: List<SkillDisplay> = emptyList(),
    val traits: List<TraitDisplay> = emptyList(),
    val wealth: Double = 1000.0,
    val lifePathStatus: String = ""
)

data class SkillDisplay(
    val id: String,
    val name: String,
    val level: Int,
    val category: String
)

data class TraitDisplay(
    val id: String,
    val name: String,
    val description: String,
    val type: String
)

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val gameEngine: GameEngine,
    private val repository: GameRepository
) : ViewModel() {

    private val _characterDisplay = MutableLiveData<CharacterDisplay>()
    val characterDisplay: LiveData<CharacterDisplay> = _characterDisplay

    private val _lifePathActions = MutableLiveData<List<LifeAction>>()
    val lifePathActions: LiveData<List<LifeAction>> = _lifePathActions

    private val _eventMessage = MutableLiveData<String>()
    val eventMessage: LiveData<String> = _eventMessage

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            repository.initializeNewGame("Player")
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val info = CharacterInfo(
            name = "Player",
            age = 18,
            day = gameEngine.getDay(),
            year = gameEngine.getYear(),
            lifePath = gameEngine.getLifePath().displayName
        )

        val stats = CharacterStats(
            health = gameEngine.getHealthManager().getHealthStatus().physicalHealth,
            energy = gameEngine.getHealthManager().getHealthStatus().energy,
            stress = 100 - gameEngine.getHealthManager().getHealthStatus().mentalHealth,
            charisma = 50,
            intellect = 50,
            cunning = 50,
            violence = 50,
            stealth = 50,
            perception = 50,
            willpower = 50
        )

        val skills = SkillRegistry.coreSkills.take(10).map { skill ->
            SkillDisplay(
                id = skill.id,
                name = skill.name,
                level = skill.level,
                category = skill.category.name
            )
        }

        val traits = TraitRegistry.positiveTraits.take(5).map { trait ->
            TraitDisplay(
                id = trait.id,
                name = trait.name,
                description = trait.description,
                type = trait.type.name
            )
        }

        _characterDisplay.value = CharacterDisplay(
            characterInfo = info,
            stats = stats,
            skills = skills,
            traits = traits,
            wealth = gameEngine.getInventoryManager().getMoney(),
            lifePathStatus = gameEngine.getLifePath().getStatus().title
        )

        _lifePathActions.value = gameEngine.getLifePath().getAvailableActions()
    }

    fun performAction(action: LifeAction) {
        viewModelScope.launch {
            val result = gameEngine.processLifeAction(action)
            _eventMessage.value = result.message
            updateDisplay()
        }
    }

    fun setLifePath(type: LifePathType) {
        gameEngine.setLifePath(type)
        updateDisplay()
    }

    fun advanceTime() {
        gameEngine.advanceTime(8)
        updateDisplay()
    }

    fun rest() {
        gameEngine.getHealthManager().changeEnergy(30)
        gameEngine.getHealthManager().heal(10)
        gameEngine.advanceTime(8)
        updateDisplay()
    }
}
