package com.example.foodapp.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodapp.R
import com.example.foodapp.viewmodels.LoginViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private val LOGTAG: String = "LoginFragment"

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
        val view =inflater.inflate(R.layout.fragment_login, container, false)

        val loginViewModel = LoginViewModel()

        val passwordET = view.findViewById<EditText>(R.id.password_et)
        val loginBtn= view.findViewById<Button>(R.id.login_btn)

        loginBtn.setOnClickListener {
            passwordET.text.toString().apply {
                if (loginViewModel.validatePassword(this)) {
                    val userType = loginViewModel.getUserType(this)
                    showMenuFragment(userType)
                }
            }
        }
        return view
    }

    private fun showMenuFragment(userType: String) {
        Toast.makeText(requireContext(), "UserType : $userType", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf(
            "userType" to userType
        )
        if (findNavController().currentDestination?.id == R.id.loginFragment) {
            findNavController().navigate(
                R.id.action_login_to_menu_fragment,
                bundle
            )
        } else {
            Log.i(LOGTAG, " destination ${findNavController().currentDestination?.id}")
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}