package com.example.foodapp.views.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.data.sqlite.entities.Item

class TransactionItemsAdapter(val itemWithQty: MutableMap<Item, Int>) : RecyclerView.Adapter<TransactionItemsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById<TextView>(R.id.item_name)
        val itemQtyTV  = view.findViewById<TextView>(R.id.item_qty)
        val totalItemCost  = view.findViewById<TextView>(R.id.total_items_cost)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item_view, parent, false)
        return TransactionItemsAdapter.ViewHolder(view)
   }



    override fun getItemCount(): Int {
        return itemWithQty.keys.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item = itemWithQty.keys.elementAt(position)
            val itemQty = itemWithQty[item]
            itemName.text = item.itemName
            totalItemCost.text = (item.itemCost * itemQty!!.toInt()).toString()
            itemQtyTV.text=itemQty.toString()
        }
    }

}
