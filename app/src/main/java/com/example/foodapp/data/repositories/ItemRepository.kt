package com.example.foodapp.data.repositories

import android.util.Log
import com.example.foodapp.Utils.ItemCategory
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Item

class ItemRepository(val db: FoodAppDatabase) {
    private val itemList: ArrayList<Item> = ArrayList()

    fun insertItemsIfNotPresent(): Boolean {

        val itemDao = db.ItemDAO()
        if (itemDao.loadAllItemsByCategory(ItemCategory.BURGER_CATEGORY).isEmpty()) {
            fillItemsInList()
            itemDao.insertAll(itemList)
            return true
        }

        Log.i("item ", " " + itemDao.loadAllItemsByCategory(ItemCategory.BURGER_CATEGORY))
        return true
    }

    private fun fillItemsInList() {
        itemList.add(Item(ItemCategory.BURGER_CATEGORY, "Single Burger", 100, 1))
        itemList.add(Item(ItemCategory.BURGER_CATEGORY, "Double Burger", 200, 1))
        itemList.add(Item(ItemCategory.BURGER_CATEGORY, "Combo Burger", 300, 1))
        itemList.add(Item(ItemCategory.BURGER_CATEGORY, "Big combo Burger", 400, 1))

        itemList.add(Item(ItemCategory.SANDWICH_CATEGORY, "Sandwich", 200, 1))
        itemList.add(Item(ItemCategory.SANDWICH_CATEGORY, "Big combo Sandwich", 400, 1))

        itemList.add(Item(ItemCategory.COCKTAIL_CATEGORY, "Cocktail", 500, 1))
        itemList.add(Item(ItemCategory.COCKTAIL_CATEGORY, "Big combo cocktail", 800, 1))

        itemList.add(Item(ItemCategory.SHAKES_CATEGORY, "Shake", 200, 1))
        itemList.add(Item(ItemCategory.SHAKES_CATEGORY, "Big combo shake", 400, 1))

        itemList.add(Item(ItemCategory.DESSERT_CATEGORY, "Dessert", 300, 1))
        itemList.add(Item(ItemCategory.DESSERT_CATEGORY, "Big combo dessert", 500, 1))

        itemList.add(Item(ItemCategory.CHIPS_CATEGORY, "Chips", 100, 1))
        itemList.add(Item(ItemCategory.CHIPS_CATEGORY, "Big combo chips", 200, 1))

    }
}