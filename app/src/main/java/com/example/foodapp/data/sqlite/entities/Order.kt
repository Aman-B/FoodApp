package com.example.foodapp.data.sqlite.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.data.sqlite.entities.Item

@Entity
data class Order(
    @PrimaryKey val orderNumber: Int,
    val orderType: Int,
    val orderTime: String,
    val userType: String,
    val status : String,
    val subTotal : Int,
    val tax : Int,
    val total : Int
    )