package com.example.foodapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodapp.R
import com.example.foodapp.Utils.Cart
import com.example.foodapp.Utils.UserType
import com.example.foodapp.data.repositories.OrdersRepository
import com.example.foodapp.data.repositories.TransactionsRepository
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Item
import com.example.foodapp.data.sqlite.entities.Order
import com.example.foodapp.viewmodels.TransactionsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val ORDERTYPE = 0
/**
 * A simple [Fragment] subclass.
 * Use the [TransactionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionsFragment : Fragment(), View.OnClickListener {
    private var total: Int=0
    private var tax: Int=0
    private var subTotal: Int = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =  inflater.inflate(R.layout.fragment_transactions, container, false)
        val itemsRecyclerView =
            view.findViewById<RecyclerView>(R.id.food_items_recyclerview)

        val subTotalTV = view.findViewById<TextView>(R.id.subtotal_count)
        val taxTV = view.findViewById<TextView>(R.id.total_tax)
        val totalTV = view.findViewById<TextView>(R.id.total_count)
        val confirmAndPayBtn = view.findViewById<Button>(R.id.confirm_and_pay_btn)
        val togoBtn = view.findViewById<Button>(R.id.togo_btn)

        itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        val itemWithQty = Cart.itemWithQty
        val transactionItemsAdapter = TransactionItemsAdapter(itemWithQty)
        itemsRecyclerView.adapter = transactionItemsAdapter

        confirmAndPayBtn.setOnClickListener(this)
        togoBtn.setOnClickListener(this)

        subTotal = calculateSubTotal(itemWithQty)
        tax = calculateTax(subTotal)
        total =subTotal+tax
        subTotalTV.text=subTotal.toString()
        taxTV.text=tax.toString()
        totalTV.text=total.toString()
        return view
    }

    private fun calculateTax(subTotal: Int): Int {
        return ((subTotal * .0825).toInt())
    }

    private fun calculateSubTotal(itemWithQty: MutableMap<Item, Int>): Int {
        var subTotal :Int = 0
        for ((key, value) in itemWithQty.entries) {
            subTotal += (key.itemCost * value)
        }
        return  subTotal
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TransactionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransactionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.confirm_and_pay_btn -> putOrderInDB(0)
            R.id.togo_btn -> {showPopup()
                              putOrderInDB(1)}
        }
    }

    private fun showPopup() {
        //Not implemented because of time constraints.

    }

    private fun putOrderInDB(orderType: Int) {

        val order = Order(
            orderNumber = System.currentTimeMillis().toString(),
            orderType =orderType,
            orderTime = System.currentTimeMillis().toString(),
            userType = UserType.MANAGER,
            status = "Completed",
            subTotal = subTotal,
            tax= tax,
            total = total
        )

        var transactionViewModel = TransactionsViewModel()
        val db = context?.let {
            Room.databaseBuilder(
                it,
                FoodAppDatabase::class.java, getString(R.string.database_name)
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
        if (db != null) {
            lifecycleScope.launch {
                OrdersRepository(db).putOrder(order)

                withContext(Dispatchers.Main)
                {
                    Toast.makeText(context,"Order completed.",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}