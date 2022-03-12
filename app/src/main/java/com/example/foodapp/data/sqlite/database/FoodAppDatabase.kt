package com.example.foodapp.data.sqlite.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.data.sqlite.DAO.ItemDAO
import com.example.foodapp.data.sqlite.DAO.OrderDAO
import com.example.foodapp.data.sqlite.entities.Item
import com.example.foodapp.data.sqlite.entities.Order

@Database(entities = [Item::class, Order::class], version = 1)
abstract class FoodAppDatabase : RoomDatabase() {
    abstract fun OrderDAO():OrderDAO
    abstract fun ItemDAO(): ItemDAO
}
