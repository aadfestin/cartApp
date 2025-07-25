package com.example.purchasing.utils

import com.example.purchasing.models.Item

object ItemRepository {
    val items = mutableListOf(
        Item(1, "Item 1"),
        Item(2, "Item 2"),
        Item(3, "Item 3")
    )
}
