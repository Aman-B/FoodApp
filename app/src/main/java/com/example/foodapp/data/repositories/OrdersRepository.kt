package com.example.foodapp.data.repositories

import com.example.foodapp.data.sqlite.DAO.OrderDAO
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Order

class OrdersRepository(val db: FoodAppDatabase) {

    suspend fun putOrder(order: Order){
        val orderDAO = db.OrderDAO()

        orderDAO.insertOrder(order)

    }
}