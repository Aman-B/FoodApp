package com.example.foodapp.views.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.data.sqlite.entities.Item

class ItemsAdapter(private var itemsList: ArrayList<Item>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemCost = view.findViewById<TextView>(R.id.item_cost)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ItemsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        with(holder) {
            itemName.text = itemsList.get(position).itemName
            itemCost.text = itemsList.get(position).itemCost.toString()
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}
