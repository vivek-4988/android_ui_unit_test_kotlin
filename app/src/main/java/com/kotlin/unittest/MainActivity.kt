package com.kotlin.unittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tax = Tax()
        val netIncome = tax.calculateIncome(1000.0,0.1)
        val netTax = tax.calculateTax(1000.0,0.1)


        println(netIncome)
        println(netTax)


    }
}