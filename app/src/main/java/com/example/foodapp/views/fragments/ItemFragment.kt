package com.example.foodapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.Utils.Cart
import com.example.foodapp.Utils.ItemCategory
import com.example.foodapp.data.sqlite.entities.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var item: Item


/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment(), View.OnClickListener {
    private var itemQty: Int = 1

    private var itemQtyTV: TextView? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            item = it.getSerializable("item") as Item
            Log.i(LOGTAG, " item $item")

            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        var itemName = view.findViewById<TextView>(R.id.item_name)
        var itemCost = view.findViewById<TextView>(R.id.item_cost)
        itemQtyTV = view.findViewById<TextView>(R.id.item_qty)

        var incQtyBtn = view.findViewById<Button>(R.id.increase_qty_btn)
        var decQtyBtn = view.findViewById<Button>(R.id.decrease_qty_button)
        var addToCartBtn = view.findViewById<Button>(R.id.add_to_cart_btn)
        var cancelBtn = view.findViewById<Button>(R.id.cancel_btn)

        incQtyBtn.setOnClickListener(this)
        decQtyBtn.setOnClickListener(this)
        addToCartBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)

        itemName.text = item.itemName
        itemCost.text = item.itemCost.toString()
        itemQtyTV?.text= itemQty.toString()

        //val item = Item(ItemCategory.BURGER_CATEGORY, "burger", 400, 1)
        //setFragmentResult(REQUESTKEY, bundleOf("item" to item, "itemQty" to itemQty))
        //findNavController().navigateUp()
        return view
    }

    companion object {
        const val REQUESTKEY: String = "ItemFragementKey"
        val LOGTAG = "ItemFragment"


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.increase_qty_btn -> { increaseItemQty()}
            R.id.decrease_qty_button -> { decreaseItemQty()}
            R.id.add_to_cart_btn -> { addItemCart()}
            R.id.cancel_btn -> { cancelItemAdd()}
        }

    }

    private fun cancelItemAdd() {
        //finish the fragment
        findNavController().navigateUp()
    }

    private fun addItemCart() {
        //add to cart
        Cart.itemWithQty.put(item, itemQty)
        setFragmentResult(REQUESTKEY, bundleOf("item" to item, "itemQty" to itemQty))
        findNavController().navigateUp()


    }

    private fun decreaseItemQty() {
        if(itemQty==1)
        {
            Toast.makeText(context,"Can't decrease more than this",Toast.LENGTH_SHORT).show()
            return
        }
        itemQtyTV?.text = (--itemQty).toString()
    }

    private fun increaseItemQty() {
        itemQtyTV?.text = itemQty++.toString()
    }
}