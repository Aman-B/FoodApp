package com.example.foodapp.views.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.ItemsCategoryClickListener
import com.example.foodapp.R

class ItemsCategoryAdapter(
    private var itemsCategoryList: ArrayList<String>,
    private var itemsCategoryClickListener: ItemsCategoryClickListener
) :RecyclerView.Adapter<ItemsCategoryAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val itemCategoryName: TextView = view.findViewById<TextView>(R.id.item_category_tv)
        val itemCategoryView = view

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            itemsCategoryList.get(position).apply {
                itemCategoryName.setText(this.toString())
            }
            itemCategoryView.setOnClickListener {
                with(itemsCategoryClickListener) {
                    onItemCategoryClicked(
                                itemCategoryName.text.toString()
                            )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsCategoryList.size
    }

}
