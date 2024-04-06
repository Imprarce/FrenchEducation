package com.imprarce.android.frencheducation.ui.greeting_auth_reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentRegBinding

class RegFragment : Fragment() {
    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

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

        binding.regButton.setOnClickListener {
            navController.navigate(R.id.action_regFragment_to_authFragment)
        }

        binding.regWithGoogle.setOnClickListener {
            navController.navigate(R.id.action_regFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}