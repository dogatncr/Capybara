package com.example.capybara.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart")
data class CartProduct(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val price: String,
    val title: String,
    val quantity: Int,
)