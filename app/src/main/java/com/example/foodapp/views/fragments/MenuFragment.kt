package com.example.foodapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.ItemsCategoryClickListener
import com.example.foodapp.R
import com.example.foodapp.Utils.ItemCategory
import com.example.foodapp.data.sqlite.entities.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val itemCategoryList: ArrayList<String> = arrayListOf(
    ItemCategory.BURGER_CATEGORY, ItemCategory.SANDWICH_CATEGORY,
    ItemCategory.COCKTAIL_CATEGORY, ItemCategory.SHAKES_CATEGORY ,
    ItemCategory.DESSERT_CATEGORY , ItemCategory.CHIPS_CATEGORY)

private val itemsList :ArrayList<Item> = arrayListOf(Item(ItemCategory.BURGER_CATEGORY,"burger",400,1))
private val secondItemsList :ArrayList<Item> = arrayListOf(Item(ItemCategory.BURGER_CATEGORY,"my burger",400,1))
/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment(),ItemsCategoryClickListener {
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var itemsRecyclerView: RecyclerView

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
        val view =inflater.inflate(R.layout.fragment_menu, container, false)
        val itemsCategoryClickListener :ItemsCategoryClickListener = this
        val itemsCategoryRecyclerView =
            view.findViewById<RecyclerView>(R.id.items_category_recyclerview)
        itemsCategoryRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val itemsCategoryAdapter = ItemsCategoryAdapter(itemCategoryList,itemsCategoryClickListener)
        itemsCategoryRecyclerView.adapter = itemsCategoryAdapter


        itemsRecyclerView =
            view.findViewById<RecyclerView>(R.id.items_recyclerview)
        itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        itemsAdapter = ItemsAdapter(itemsList)
        itemsRecyclerView.adapter = itemsAdapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemCategoryClicked() {
        Toast.makeText(context, "Clicekd! ",Toast.LENGTH_SHORT).show()
        itemsList.clear()
        itemsList.addAll(secondItemsList)
        Log.i("Menu ", "list "+ itemsList)
        itemsAdapter.notifyDataSetChanged()
    }
}