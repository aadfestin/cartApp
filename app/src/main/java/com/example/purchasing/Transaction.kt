package com.example.purchasing.models

data class Transaction(
    val description: String = "",
    val date: String = "",
    val method: String = "",
    val amount: Double = 0.0
)
