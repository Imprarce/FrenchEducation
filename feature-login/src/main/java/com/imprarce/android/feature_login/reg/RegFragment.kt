package com.imprarce.android.feature_login.reg

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.imprarce.android.feature_login.GreetingViewModel
import com.imprarce.android.feature_login.databinding.FragmentRegBinding
import com.imprarce.android.feature_login.helpers.SignUpState
import com.imprarce.android.network.ResponseFirebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegFragment : Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private var email: String = ""
    private var password: String = ""
    private var confirmedPassword: String = ""

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegBinding.inflate(inflater, container, false)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignUpState.Success -> {
                    val uri = Uri.parse("myApp://feature-home")
                    navController.navigate(uri)
                }
                is SignUpState.Loading -> {

                }
                is SignUpState.Error  -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        binding.regButton.setOnClickListener {
            email = binding.editTextEmail.text.toString()
            password = binding.editTextPassword.text.toString()
            confirmedPassword = binding.editTextConfirmPassword.text.toString()
            if (password.equals(confirmedPassword, ignoreCase = true)) {
                viewModel.signUp(email, password)
            } else {
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }

        binding.regWithGoogle.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}