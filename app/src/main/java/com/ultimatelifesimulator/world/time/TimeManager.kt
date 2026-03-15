package com.ultimatelifesimulator.world.time

data class GameTime(
    val day: Int = 1,
    val hour: Int = 8,
    val minute: Int = 0,
    val year: Int = 2024,
    val season: Season = Season.SPRING
) {
    fun advance(hours: Int = 1): GameTime {
        var newHour = hour + hours
        var newDay = day
        var newYear = year
        
        while (newHour >= 24) {
            newHour -= 24
            newDay++
        }
        
        while (newDay > 365) {
            newDay -= 365
            newYear++
        }
        
        return copy(
            day = newDay,
            hour = newHour,
            year = newYear,
            season = getSeason(newDay)
        )
    }
    
    fun advanceDays(days: Int): GameTime = advance(days * 24)
    
    private fun getSeason(day: Int): Season {
        val dayOfYear = ((day - 1) % 365) + 1
        return when (dayOfYear) {
            in 1..90 -> Season.WINTER
            in 91..180 -> Season.SPRING
            in 181..270 -> Season.SUMMER
            else -> Season.AUTUMN
        }
    }
    
    fun getAge(age: Int): Int {
        return ((day - 1) / 365) + age
    }
    
    fun format(): String {
        return "Day $day, Year $year ${season.name.lowercase().replaceFirstChar { it.uppercase() }} ${String.format("%02d:%02d", hour, minute)}"
    }
}

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

class TimeManager {
    private var gameTime = GameTime()
    
    fun getTime(): GameTime = gameTime
    
    fun advance(hours: Int = 1) {
        gameTime = gameTime.advance(hours)
    }
    
    fun advanceDays(days: Int) {
        gameTime = gameTime.advanceDays(days)
    }
    
    fun setTime(time: GameTime) {
        gameTime = time
    }
    
    fun getDay(): Int = gameTime.day
    fun getYear(): Int = gameTime.year
    fun getSeason(): Season = gameTime.season
    fun getHour(): Int = gameTime.hour
    
    fun isNight(): Boolean = gameTime.hour < 6 || gameTime.hour >= 22
    fun isMorning(): Boolean = gameTime.hour in 6..11
    fun isAfternoon(): Boolean = gameTime.hour in 12..17
    fun isEvening(): Boolean = gameTime.hour in 18..21
}
