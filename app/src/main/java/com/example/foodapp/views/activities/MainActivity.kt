package com.example.foodapp.views.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.foodapp.R
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
            applicationContext,
            FoodAppDatabase::class.java, getString(R.string.database_name)
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        val mainActivityViewModel = MainActivityViewModel(db)

        lifecycleScope.launch()
        {
            observeDatabase(mainActivityViewModel)
        }
    }

    private suspend fun observeDatabase(mainActivityViewModel: MainActivityViewModel) {
        mainActivityViewModel.isFoodDataInsertedInDB().observe(this@MainActivity, {
            if (it == true) {
                Toast.makeText(this@MainActivity, "Database is ready", Toast.LENGTH_SHORT).show()
            }
        })
    }


}