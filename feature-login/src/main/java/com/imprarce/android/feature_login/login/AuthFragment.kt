package com.imprarce.android.feature_login.login

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.imprarce.android.feature_login.GreetingViewModel
import com.imprarce.android.feature_login.R
import com.imprarce.android.feature_login.databinding.FragmentAuthBinding
import com.imprarce.android.network.ResponseFirebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private var email: String = ""
    private var password: String = ""


    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseFirebase.Success -> {
                    val uri = Uri.parse("myApp://feature-home")
                    navController.navigate(uri)
                }
                is ResponseFirebase.Loading -> {

                }
                is ResponseFirebase.Failure -> {
                    Toast.makeText(context, response.exception.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        binding.enterButton.setOnClickListener {
            email = binding.editTextEmail.text.toString()
            password = binding.editTextPassword.text.toString()
            Log.d("AUTH", "$email")
            viewModel.login(email, password)
        }

        binding.enterWithGoogle.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}