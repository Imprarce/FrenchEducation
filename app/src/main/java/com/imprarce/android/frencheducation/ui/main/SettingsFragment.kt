package com.imprarce.android.frencheducation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentSettingsBinding
import com.imprarce.android.frencheducation.ui.MainActivity
import com.imprarce.android.frencheducation.ui.greeting.GreetingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel by viewModels<GreetingViewModel>()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutButton.setOnClickListener {
            viewModel.logOut()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}