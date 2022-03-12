package com.example.foodapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.foodapp.Utils.UserType

class LoginViewModel : ViewModel() {

    val managerPassword = "manager"
    val serverPassword = "server"

    fun validatePassword(password: String): Boolean {
        if (password == managerPassword || password == serverPassword) {
            return true
        }
        return false
    }

    fun getUserType(password: String): String {
        return if (password.equals(managerPassword)) {
            UserType.MANAGER
        } else {
            UserType.SERVER
        }
    }
}