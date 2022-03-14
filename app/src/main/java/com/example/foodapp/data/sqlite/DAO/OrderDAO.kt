package com.example.foodapp.data.sqlite.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.data.sqlite.entities.Order

@Dao
interface OrderDAO {
    @Query("SELECT * FROM `order`")
    suspend fun getAll(): List<Order>

    @Query("SELECT * FROM `Order` WHERE orderType IN (:orderType)")
    suspend fun loadOrderByType(orderType: String): List<Order>

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByOrderNumber(first: String, last: String): User*/
    @Insert
    suspend fun insertOrder(order: Order)

    @Insert
    suspend fun insertAll(vararg orders: Order)

    @Delete
    suspend fun delete(order: Order)

}