package com.imprarce.android.frencheducation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentProfileBinding
import com.imprarce.android.frencheducation.ui.greeting.FirebaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel by viewModels<FirebaseViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.nameFragment.text = "Профиль"

        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.userFromRoom.observe(viewLifecycleOwner){ response ->
            response?.let {
                binding.name.text = response.userName
                binding.email.text = response.login
                Glide.with(requireContext())
                    .load(response.imageUrl)
                    .placeholder(R.drawable.image_plug)
                    .into(binding.photo)
            }
        }


        if (viewModel.userFromRoom.value == null) {
            viewModel.getUser()
        }
    }
}