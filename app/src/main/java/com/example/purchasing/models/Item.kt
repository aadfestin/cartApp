package com.example.purchasing.models

// models/Item.kt
data class Item(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,

    var quantity: Int = 1 // default to 1
)
