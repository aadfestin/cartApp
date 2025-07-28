package com.example.purchasing.utils

import com.example.purchasing.models.Item

object Cart {
    val items = mutableListOf<Item>()

    fun add(item: Item) {
        val existingItem = items.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            items.add(item.copy(quantity = 1))
        }
    }

    fun remove(item: Item) {
        val existingItem = items.find { it.id == item.id }
        existingItem?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                items.removeIf { it.id == item.id }
            }
        }
    }

    fun removeAllWithId(id: Int) {
        items.removeAll { it.id == id }
    }

    fun getTotalPrice(): Double {
        return items.sumOf { it.price * it.quantity }
    }

}

