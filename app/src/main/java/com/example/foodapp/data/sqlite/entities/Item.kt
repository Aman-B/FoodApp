package com.example.foodapp.data.sqlite.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item (
    val category : String,
    @PrimaryKey  val itemName : String,
    val itemCost : Int,
    val itemQty : Int
)