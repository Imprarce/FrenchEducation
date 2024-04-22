package com.imprarce.android.feature_login

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
import com.imprarce.android.feature_login.databinding.FragmentGreetingBinding
import com.imprarce.android.network.ResponseFirebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GreetingFragment : Fragment() {
    private val viewModel: GreetingViewModel by activityViewModels()

    private var _binding: FragmentGreetingBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGreetingBinding.inflate(inflater, container, false)
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
            navController.navigate(R.id.action_greetingFragment_to_authFragment)
        }

        binding.regButton.setOnClickListener {
            navController.navigate(R.id.action_greetingFragment_to_regFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("GreetingFragment", "Fragment destroyed View")
        viewModelStore.clear()
        _binding = null
    }

}