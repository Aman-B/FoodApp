package com.example.foodapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.repositories.ItemRepository
import com.example.foodapp.data.sqlite.database.FoodAppDatabase

class MainActivityViewModel(val db: FoodAppDatabase) : ViewModel() {
    private val isFoodDataInsertedInDB = MutableLiveData<Boolean>()

    fun isFoodDataInsertedInDB(): MutableLiveData<Boolean> {
        isFoodDataInsertedInDB.value = ItemRepository(db).insertItemsIfNotPresent()
        return isFoodDataInsertedInDB
    }
}