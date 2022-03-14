package com.example.foodapp.data.sqlite.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.data.sqlite.entities.Item

@Dao
interface ItemDAO {
    @Query("SELECT * FROM `Item`")
    suspend fun getAll(): List<Item>

    @Query("SELECT * FROM `Item` WHERE category LIKE (:category)")
    suspend fun loadAllItemsByCategory(category:String): List<Item>

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByOrderNumber(first: String, last: String): User*/

    @Insert
    suspend fun insertAll(items: List<Item>)

    @Delete
    suspend fun delete(item: Item)

}