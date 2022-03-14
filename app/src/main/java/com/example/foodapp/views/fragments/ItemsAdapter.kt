package com.example.foodapp.views.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.data.sqlite.entities.Item
import com.example.foodapp.views.ItemClickListener

class ItemsAdapter(private var itemsList: ArrayList<Item>, private var itemClickListener: ItemClickListener) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)  {
        val itemName = view.findViewById<TextView>(R.id.item_name)
        val itemCost = view.findViewById<TextView>(R.id.item_cost)
        val itemsView = view

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ItemsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        with(holder) {
            itemsList.apply {
                itemName.text = this[position].itemName
                itemCost.text = this[position].itemCost.toString()
                itemsView.setOnClickListener(View.OnClickListener {
                    itemClickListener.onItemClicked(this[position] as Item)
                })
            }


        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}
