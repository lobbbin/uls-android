package com.ultimatelifesimulator.world.economy

import java.util.UUID

data class MarketItem(
    val id: String,
    val name: String,
    val category: String,
    val basePrice: Double,
    val currentPrice: Double,
    val supply: Int,
    val demand: Int,
    val volatility: Float = 0.1f
) {
    fun updatePrice(): MarketItem {
        val change = ((Math.random() - 0.5) * 2 * volatility).toFloat()
        val newPrice = currentPrice * (1 + change)
        return copy(currentPrice = newPrice.coerceIn(basePrice * 0.5, basePrice * 2.0))
    }
}

data class TradeRoute(
    val id: String = UUID.randomUUID().toString(),
    val startLocation: String,
    val endLocation: String,
    val goods: List<String>,
    val distance: Int,
    val risk: Float = 0.1f,
    val profit: Double = 100.0
)

data class EconomicIndicator(
    val gdp: Double = 1000000.0,
    val inflation: Float = 0.02f,
    val unemployment: Float = 0.05f,
    val consumerConfidence: Int = 50,
    val stockMarketIndex: Double = 10000.0,
    val interestRate: Float = 0.03f
)

enum class EconomicCycle {
    RECESSION,
    DEPRESSION,
    RECOVERY,
    EXPANSION,
    PEAK
}

enum class TradeStatus {
    NORMAL,
    SANCTIONS,
    EMBARGO,
    WAR
}

object EconomySystem {
    private val marketItems = mutableMapOf<String, MarketItem>()
    private val tradeRoutes = mutableListOf<TradeRoute>()
    private var economyState = EconomicCycle.EXPANSION
    private var indicators = EconomicIndicator()
    
    fun initialize() {
        // Initialize basic market items
        val items = listOf(
            MarketItem("food", "Food", "Essentials", 10.0, 10.0, 1000, 1000),
            MarketItem("water", "Water", "Essentials", 5.0, 5.0, 2000, 2000),
            MarketItem("clothing", "Clothing", "Goods", 50.0, 50.0, 500, 500),
            MarketItem("weapon", "Weapons", "Goods", 200.0, 200.0, 100, 100),
            MarketItem("medicine", "Medicine", "Essentials", 100.0, 100.0, 200, 300),
            MarketItem("fuel", "Fuel", "Energy", 50.0, 50.0, 500, 500),
            MarketItem("electronics", "Electronics", "Luxury", 500.0, 500.0, 50, 50),
            MarketItem("luxury_goods", "Luxury Goods", "Luxury", 1000.0, 1000.0, 20, 20),
            MarketItem("real_estate", "Real Estate", "Property", 100000.0, 100000.0, 5, 5),
            MarketItem("stocks", "Stocks", "Financial", 100.0, 100.0, 1000, 1000),
            MarketItem("bonds", "Bonds", "Financial", 1000.0, 1000.0, 500, 500),
            MarketItem("gold", "Gold", "Commodity", 1500.0, 1500.0, 100, 100),
            MarketItem("silver", "Silver", "Commodity", 20.0, 20.0, 200, 200),
            MarketItem("oil", "Oil", "Energy", 80.0, 80.0, 300, 300),
            MarketItem("drugs_illegal", "Illegal Drugs", "Contraband", 500.0, 500.0, 10, 10, 0.5f),
            MarketItem("weapons_illegal", "Illegal Weapons", "Contraband", 2000.0, 2000.0, 5, 5, 0.5f)
        )
        
        items.forEach { marketItems[it.id] = it }
        
        // Initialize trade routes
        tradeRoutes.add(TradeRoute("route1", "harbor", "marketplace", listOf("food", "luxury_goods"), 10, 0.1f, 200.0))
        tradeRoutes.add(TradeRoute("route2", "farm", "marketplace", listOf("food"), 5, 0.05f, 100.0))
        tradeRoutes.add(TradeRoute("route3", "factory", "mall", listOf("electronics", "clothing"), 20, 0.15f, 500.0))
    }
    
    fun getItemPrice(itemId: String): Double = marketItems[itemId]?.currentPrice ?: 0.0
    
    fun buyItem(itemId: String, quantity: Int): Double {
        val item = marketItems[itemId] ?: return 0.0
        val total = item.currentPrice * quantity
        marketItems[itemId] = item.copy(supply = item.supply - quantity, demand = item.demand + quantity)
        return total
    }
    
    fun sellItem(itemId: String, quantity: Int): Double {
        val item = marketItems[itemId] ?: return 0.0
        val total = item.currentPrice * quantity * 0.9 // 10% tax/fee
        marketItems[itemId] = item.copy(supply = item.supply + quantity, demand = item.demand - quantity)
        return total
    }
    
    fun updateMarket() {
        marketItems.values.forEach { item ->
            marketItems[item.id] = item.updatePrice()
        }
        
        // Update economic cycle
        updateEconomicCycle()
    }
    
    private fun updateEconomicCycle() {
        val random = Math.random()
        economyState = when (economyState) {
            EconomicCycle.RECESSION -> if (random < 0.3) EconomicCycle.DEPRESSION else EconomicCycle.RECESSION
            EconomicCycle.DEPRESSION -> if (random < 0.7) EconomicCycle.RECOVERY else EconomicCycle.DEPRESSION
            EconomicCycle.RECOVERY -> if (random < 0.5) EconomicCycle.EXPANSION else EconomicCycle.RECOVERY
            EconomicCycle.EXPANSION -> if (random < 0.4) EconomicCycle.PEAK else EconomicCycle.EXPANSION
            EconomicCycle.PEAK -> if (random < 0.6) EconomicCycle.RECESSION else EconomicCycle.PEAK
        }
        
        // Update indicators based on cycle
        indicators = when (economyState) {
            EconomicCycle.RECESSION -> indicators.copy(
                gdp = indicators.gdp * 0.98,
                inflation = indicators.inflation * 0.8f,
                unemployment = (indicators.unemployment + 0.02f).coerceAtMost(0.2f),
                consumerConfidence = (indicators.consumerConfidence - 10).coerceAtLeast(10),
                stockMarketIndex = indicators.stockMarketIndex * 0.95
            )
            EconomicCycle.DEPRESSION -> indicators.copy(
                gdp = indicators.gdp * 0.95,
                inflation = 0f,
                unemployment = (indicators.unemployment + 0.05f).coerceAtMost(0.3f),
                consumerConfidence = (indicators.consumerConfidence - 15).coerceAtLeast(0),
                stockMarketIndex = indicators.stockMarketIndex * 0.9
            )
            EconomicCycle.RECOVERY -> indicators.copy(
                gdp = indicators.gdp * 1.02,
                inflation = indicators.inflation * 1.1f,
                unemployment = (indicators.unemployment - 0.01f).coerceAtLeast(0.03f),
                consumerConfidence = (indicators.consumerConfidence + 5).coerceAtMost(70),
                stockMarketIndex = indicators.stockMarketIndex * 1.03
            )
            EconomicCycle.EXPANSION -> indicators.copy(
                gdp = indicators.gdp * 1.05,
                inflation = (indicators.inflation + 0.005f).coerceAtMost(0.1f),
                unemployment = (indicators.unemployment - 0.01f).coerceAtLeast(0.03f),
                consumerConfidence = (indicators.consumerConfidence + 5).coerceAtMost(90),
                stockMarketIndex = indicators.stockMarketIndex * 1.05
            )
            EconomicCycle.PEAK -> indicators.copy(
                gdp = indicators.gdp * 1.02,
                inflation = (indicators.inflation + 0.01f).coerceAtMost(0.15f),
                unemployment = indicators.unemployment,
                consumerConfidence = 100,
                stockMarketIndex = indicators.stockMarketIndex * 1.02
            )
        }
    }
    
    fun getIndicators(): EconomicIndicator = indicators
    
    fun getCycle(): EconomicCycle = economyState
    
    fun getMarketItems(): List<MarketItem> = marketItems.values.toList()
    
    fun isItemLegal(itemId: String): Boolean = itemId !in listOf("drugs_illegal", "weapons_illegal")
}

class EconomyManager {
    private val playerInvestments = mutableMapOf<String, Double>()
    private val playerBusinesses = mutableListOf<String>()
    private var totalTradeProfit = 0.0
    
    fun invest(amount: Double, type: String): Boolean {
        if (amount <= 0) return false
        playerInvestments[type] = (playerInvestments[type] ?: 0.0) + amount
        return true
    }
    
    fun getInvestmentValue(type: String): Double {
        val invested = playerInvestments[type] ?: 0.0
        val multiplier = when (type) {
            "stocks" -> EconomySystem.getIndicators().stockMarketIndex / 10000
            "bonds" -> 1.0 + EconomySystem.getIndicators().interestRate
            "gold" -> 1.0 + (Math.random() - 0.5) * 0.1
            else -> 1.0
        }
        return invested * multiplier
    }
    
    fun getTotalInvestmentValue(): Double = playerInvestments.keys.sumOf { getInvestmentValue(it) }
    
    fun runTradeRoute(routeId: String): Double {
        val route = EconomySystem.tradeRoutes.find { it.id == routeId } ?: return 0.0
        val success = Math.random() > route.risk
        if (success) {
            totalTradeProfit += route.profit
            return route.profit
        }
        return -route.profit * 0.5
    }
    
    fun getTradeRoutes(): List<TradeRoute> = EconomySystem.tradeRoutes
    
    fun startBusiness(name: String): Boolean {
        if (playerBusinesses.contains(name)) return false
        playerBusinesses.add(name)
        return true
    }
    
    fun getBusinesses(): List<String> = playerBusinesses.toList()
}
