package com.example.foodapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.foodapp.R
import com.example.foodapp.data.sqlite.database.FoodAppDatabase
import com.example.foodapp.data.sqlite.entities.Item
import com.example.foodapp.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
            applicationContext,
            FoodAppDatabase::class.java, getString(R.string.database_name)
        ).allowMainThreadQueries().build()
        val mainActivityViewModel = MainActivityViewModel(db)

        mainActivityViewModel.isFoodDataInsertedInDB().observe(this, {
            if(it == true)
            {
                Toast.makeText(this,"Database is ready", Toast.LENGTH_SHORT).show()
            }

        })

    }


}