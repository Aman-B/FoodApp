package com.example.foodapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodapp.ItemsCategoryClickListener
import com.example.foodapp.R
import com.example.foodapp.Utils.Cart
import com.example.foodapp.Utils.ItemCategory
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Item
import com.example.foodapp.viewmodels.MenuViewModel
import com.example.foodapp.views.ItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val itemCategoryList: ArrayList<String> = arrayListOf(
    ItemCategory.BURGER_CATEGORY, ItemCategory.SANDWICH_CATEGORY,
    ItemCategory.COCKTAIL_CATEGORY, ItemCategory.SHAKES_CATEGORY ,
    ItemCategory.DESSERT_CATEGORY , ItemCategory.CHIPS_CATEGORY)
private val itemWithQty : Map<Item,Int> = mapOf()


private val itemsList :ArrayList<Item> = arrayListOf(Item(ItemCategory.BURGER_CATEGORY,"burger",400,1))
private val secondItemsList :ArrayList<Item> = arrayListOf(Item(ItemCategory.BURGER_CATEGORY,"my burger",400,1))
/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment(),ItemsCategoryClickListener,ItemClickListener {
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var itemsRecyclerView: RecyclerView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var selectedCategory : String = ItemCategory.BURGER_CATEGORY

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
        val db = context?.let {
            Room.databaseBuilder(
                it,
                FoodAppDatabase::class.java, getString(R.string.database_name)
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }

        val view =inflater.inflate(R.layout.fragment_menu, container, false)
        val itemsCategoryClickListener :ItemsCategoryClickListener = this
        val itemsCategoryRecyclerView =
            view.findViewById<RecyclerView>(R.id.items_category_recyclerview)
        itemsCategoryRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val itemsCategoryAdapter = ItemsCategoryAdapter(itemCategoryList,itemsCategoryClickListener)
        itemsCategoryRecyclerView.adapter = itemsCategoryAdapter

        menuViewModel = db?.let { MenuViewModel(it) }!!
        val itemClickListener :ItemClickListener = this

        getItemsListForCategory(selectedCategory)
        itemsRecyclerView =
            view.findViewById(R.id.items_recyclerview)
        itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        itemsAdapter = ItemsAdapter(itemsList,itemClickListener)
        itemsRecyclerView.adapter = itemsAdapter
        setFragmentResultListener(ItemFragment.REQUESTKEY){
                _, bundle ->
            run {
                val item = bundle.getSerializable("item") as Item
                val itemQty = bundle.getInt("itemQty") as Int
                addItemToCart(item,itemQty)
            }
        }
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.view_cart -> launchTransactionsFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchTransactionsFragment() {
        if (findNavController().currentDestination?.id == R.id.menuFragment) {
            findNavController().navigate(
                R.id.action_menuFragment_to_transactionsFragment)
        } else {
            Log.i(LOGTAG, " destination ${findNavController().currentDestination?.id}")
        }
    }

    private fun addItemToCart(item: Item, itemQty : Int) {
        Toast.makeText(context, "Item  added to cart : "+item.itemName, Toast.LENGTH_SHORT).show()
        Cart.itemWithQty[item] = itemQty
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

        val LOGTAG = "MenuFragment"
    }

    override fun onItemCategoryClicked(category: String) {
        Toast.makeText(context, "Clicked! ", Toast.LENGTH_SHORT).show()
        getItemsListForCategory(category)
    }

    private fun getItemsListForCategory(category: String) {
        lifecycleScope.launch {
            val itemsListForCategory = menuViewModel.getListOfItems(category)
            withContext(Dispatchers.Main)
            {
                updateListInUI(itemsListForCategory = itemsListForCategory)
            }
        }
    }

    private fun updateListInUI(itemsListForCategory: List<Item>?) {
        if (itemsListForCategory != null) {
            itemsList.clear()
            itemsList.addAll(itemsListForCategory)
        }
        Log.i("Menu ", "list "+ itemsList)
        itemsAdapter.notifyDataSetChanged()
    }

    override fun onItemClicked(item: Item) {
        Toast.makeText(context, "Got item  "+item.itemName, Toast.LENGTH_SHORT).show()
        launchItemFragment(item)
        //TODO: launch ItemFragment with this item. Design and get results from the fragment.
    }

    private fun launchItemFragment(item: Item) {
        val bundle = bundleOf(
            "item" to item
        )
        if (findNavController().currentDestination?.id == R.id.menuFragment) {
            findNavController().navigate(
                R.id.action_menuFragment_to_itemFragment,
                bundle
            )
        } else {
            Log.i(LOGTAG, " destination ${findNavController().currentDestination?.id}")
        }
    }

}