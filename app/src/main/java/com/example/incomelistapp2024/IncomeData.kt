package com.example.incomelistapp2024

import kotlin.random.Random

object IncomeData {
    private val incomes = mutableMapOf<String, Int>()

    init {
        generateRandomIncomes()
    }

    private fun generateRandomIncomes() {
        val dates = listOf("2024-10-01", "2024-10-02", "2024-10-03")
        for (date in dates) {
            val randomIncome = Random.nextInt(10_000, 1_000_001)
            incomes[date] = randomIncome
        }
    }

    fun getIncomeForDate(date: String): Int? {
        return incomes[date]
    }
}
