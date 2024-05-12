package com.imprarce.android.feature_login.reg

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.imprarce.android.feature_login.GreetingViewModel
import com.imprarce.android.feature_login.R
import com.imprarce.android.feature_login.databinding.FragmentRegBinding
import com.imprarce.android.network.ResponseNetwork
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
    private lateinit var customProgressBar : ConstraintLayout


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
        customProgressBar = view.findViewById(R.id.customProgressBarReg)

        viewModel.userGetNetworkLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseNetwork.Success -> {
                    viewModel.userGetRoomLiveData.observe(viewLifecycleOwner){ state ->
                        hideProgressBar()
                        Toast.makeText(context, "Пользователь успешно создан", Toast.LENGTH_LONG).show()
                        val uri = Uri.parse("myApp://feature-home")
                        navController.navigate(uri)
                    }
                }
                is ResponseNetwork.Loading -> {
                    showProgressBar()
                    binding.regButton.isEnabled = false
                }
                is ResponseNetwork.Failure -> {
                    hideProgressBar()
                    Toast.makeText(context, state.exception, Toast.LENGTH_LONG).show()
                    binding.regButton.isEnabled = true
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
                hideKeyboard()
            } else {
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun hideProgressBar(){
        customProgressBar.visibility = View.GONE
        binding.overlay.visibility = View.GONE
    }

    private fun showProgressBar(){
        customProgressBar.visibility = View.VISIBLE
        binding.overlay.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}