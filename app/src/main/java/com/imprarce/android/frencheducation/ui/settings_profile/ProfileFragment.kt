package com.imprarce.android.frencheducation.ui.settings_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentProfileBinding
import com.imprarce.android.frencheducation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

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


        viewModel.userFromRoom.observe(viewLifecycleOwner) { response ->
            response?.let {
                binding.name.text = response.userName
                binding.email.text = response.email
                loadUserPhoto(response.imageUrl)
            }
        }


        if (viewModel.userFromRoom.value == null) {
            viewModel.getUser()
        }
    }

    private fun loadUserPhoto(imageUrl: String?) {
        Glide.with(requireContext())
            .load(imageUrl)
            .placeholder(R.drawable.image_plug)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.photo)
    }
}