package com.example.foodapp.Utils

import com.example.foodapp.data.sqlite.entities.Item

object Cart {
         val itemWithQty : MutableMap<Item,Int> = mutableMapOf()
}