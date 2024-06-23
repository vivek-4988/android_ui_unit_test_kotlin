package com.kotlin.unittest

class Tax {

    fun calculateTax(grossInCome: Double, taxRate: Double): Double {
        return grossInCome * taxRate
    }

    fun calculateIncome(grossIncome: Double, taxRate: Double): Double {
        return grossIncome - (grossIncome * taxRate)
    }

}