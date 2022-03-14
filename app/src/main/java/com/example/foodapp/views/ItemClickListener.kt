package com.example.foodapp.views

import android.content.ClipData
import com.example.foodapp.data.sqlite.entities.Item

interface ItemClickListener {
    fun onItemClicked(item: Item)
}