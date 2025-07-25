package com.example.purchasing.models

data class PaymentOption(
    val id: String,
    val name: String,
    val iconResId: Int,   // Drawable resource for the payment method
    val isSelected: Boolean = false
)
