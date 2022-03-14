package com.example.foodapp.viewmodels

import androidx.lifecycle.ViewModel

import com.example.foodapp.data.repositories.ItemRepository
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Item

class MenuViewModel(val db: FoodAppDatabase) : ViewModel() {
    var foodListItems: ArrayList<Item> = ArrayList()

    suspend fun getListOfItems(category: String): ArrayList<Item> {
        foodListItems = ItemRepository(db).getItemsForThisCategory(category)
        return foodListItems
    }
}