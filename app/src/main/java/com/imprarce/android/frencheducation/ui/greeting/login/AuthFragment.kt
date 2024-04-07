package com.imprarce.android.frencheducation.ui.greeting.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.api.ResponseFirebase
import com.imprarce.android.frencheducation.databinding.FragmentAuthBinding
import com.imprarce.android.frencheducation.ui.greeting.GreetingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val viewModel by viewModels<GreetingViewModel>()

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
                    navController.navigate(
                        R.id.action_authFragment_to_mainFragment,
                        null,
                        navOptions {
                            anim{
                               enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
                               popEnter = androidx.navigation.ui.R.anim.nav_default_pop_enter_anim
                               popExit = androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                               exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
                            }
                            launchSingleTop = true
                            popUpTo(R.id.nav_home){
                                inclusive = true
                            }
                        }
                    )
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
            navController.navigate(R.id.action_authFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}