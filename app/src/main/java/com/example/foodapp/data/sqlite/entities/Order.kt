package com.example.foodapp.data.sqlite.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.data.sqlite.entities.Item

@Entity
data class Order(
    @PrimaryKey val orderNumber: String,
    val orderType: Int, //0 for confirm and pay, 1 for togo order.
    val orderTime: String,
    val userType: String,
    val status : String,
    val subTotal : Int,
    val tax : Int,
    val total : Int
    )