package com.ultimatelifesimulator.inventory

data class Item(
    val id: String,
    val name: String,
    val category: ItemCategory,
    val description: String = "",
    val value: Double = 0.0,
    val weight: Float = 0f,
    val quantity: Int = 1,
    val isStackable: Boolean = false,
    val isConsumable: Boolean = false,
    val effects: Map<String, Int> = emptyMap()
)

enum class ItemCategory {
    CURRENCY,
    FOOD,
    DRINK,
    CLOTHING,
    WEAPON,
    TOOL,
    BOOK,
    DOCUMENT,
    DRUG,
    MEDICAL,
    ELECTRONIC,
    FURNITURE,
    VEHICLE,
    REAL_ESTATE,
    CONTRABAND,
    KEY,
    GIFT,
    ART
}

data class Asset(
    val id: String,
    val name: String,
    val type: AssetType,
    val value: Double,
    val income: Double = 0.0,
    val expenses: Double = 0.0,
    val isActive: Boolean = true
)

enum class AssetType {
    REAL_ESTATE,
    BUSINESS_SHARE,
    STOCK,
    BOND,
    VEHICLE,
    INTELLECTUAL_PROPERTY,
    ART_COLLECTION,
    PRECIOUS_METAL,
    CRYPTOCURRENCY,
    RETIREMENT_ACCOUNT
}

data class Debt(
    val id: String,
    val creditor: String,
    val type: DebtType,
    val amount: Double,
    val interestRate: Float,
    val monthlyPayment: Double,
    val isInDefault: Boolean = false
)

enum class DebtType {
    BANK_LOAN,
    PERSONAL_LOAN,
    PAYDAY_LOAN,
    MORTGAGE,
    STUDENT_LOAN,
    CREDIT_CARD,
    MEDICAL_DEBT,
    GAMBLING_DEBT,
    LOAN_SHARK
}

class InventoryManager {
    private val items = mutableListOf<Item>()
    private val assets = mutableListOf<Asset>()
    private val debts = mutableListOf<Debt>()
    private var money: Double = 1000.0
    
    fun getMoney(): Double = money
    
    fun addMoney(amount: Double) {
        money += amount
    }
    
    fun removeMoney(amount: Double): Boolean {
        return if (money >= amount) {
            money -= amount
            true
        } else {
            false
        }
    }
    
    fun addItem(item: Item) {
        val existing = items.find { it.id == item.id && item.isStackable }
        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + item.quantity)
            items.remove(existing)
            items.add(updated)
        } else {
            items.add(item)
        }
    }
    
    fun removeItem(itemId: String, quantity: Int = 1): Boolean {
        val item = items.find { it.id == itemId } ?: return false
        return if (item.quantity >= quantity) {
            if (item.quantity == quantity) {
                items.remove(item)
            } else {
                val updated = item.copy(quantity = item.quantity - quantity)
                items.remove(item)
                items.add(updated)
            }
            true
        } else {
            false
        }
    }
    
    fun getItems(): List<Item> = items.toList()
    
    fun getItemsByCategory(category: ItemCategory): List<Item> =
        items.filter { it.category == category }
    
    fun addAsset(asset: Asset) {
        assets.add(asset)
    }
    
    fun removeAsset(assetId: String) {
        assets.removeAll { it.id == assetId }
    }
    
    fun getAssets(): List<Asset> = assets.toList()
    
    fun getTotalAssetValue(): Double = assets.sumOf { it.value }
    
    fun getTotalIncome(): Double = assets.filter { it.isActive }.sumOf { it.income }
    
    fun addDebt(debt: Debt) {
        debts.add(debt)
    }
    
    fun removeDebt(debtId: String) {
        debts.removeAll { it.id == debtId }
    }
    
    fun getDebts(): List<Debt> = debts.toList()
    
    fun getTotalDebt(): Double = debts.sumOf { it.amount }
    
    fun getNetWorth(): Double = money + getTotalAssetValue() - getTotalDebt()
    
    fun processMonthlyDebt() {
        for (debt in debts) {
            if (!debt.isInDefault) {
                val monthlyInterest = debt.amount * (debt.interestRate / 12)
                val newAmount = debt.amount + monthlyInterest
                debts.remove(debt)
                debts.add(debt.copy(amount = newAmount))
            }
        }
    }
}

object ItemRegistry {
    val items = listOf(
        Item("money", "Money", ItemCategory.CURRENCY, "Currency", 1.0, 0f, 1, true),
        Item("bread", "Bread", ItemCategory.FOOD, "Fresh bread", 5.0, 0.5f, 1, true, true, mapOf("hunger" to -20)),
        Item("apple", "Apple", ItemCategory.FOOD, "A fresh apple", 2.0, 0.2f, 1, true, true, mapOf("hunger" to -10)),
        Item("water", "Water", ItemCategory.DRINK, "Clean water", 1.0, 0.5f, 1, true, true, mapOf("thirst" to -30)),
        Item("knife", "Knife", ItemCategory.WEAPON, "A small knife", 25.0, 0.3f, 1, false, false, mapOf("violence" to 5)),
        Item("phone", "Phone", ItemCategory.ELECTRONIC, "Smartphone", 500.0, 0.2f, 1, false),
        Item("laptop", "Laptop", ItemCategory.ELECTRONIC, "Personal computer", 1000.0, 2.0f, 1, false),
        Item("medkit", "Medical Kit", ItemCategory.MEDICAL, "First aid supplies", 50.0, 1.0f, 1, false, true, mapOf("health" to 20)),
        Item("book", "Skill Book", ItemCategory.BOOK, "A book on a skill", 30.0, 0.5f, 1, false),
        Item("id_card", "ID Card", ItemCategory.DOCUMENT, "Personal identification", 0.0, 0.01f, 1, false),
        Item("clothes", "Clothes", ItemCategory.CLOTHING, "Regular clothing", 50.0, 1.0f, 1, false)
    )
}
