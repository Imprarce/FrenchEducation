package com.imprarce.android.frencheducation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.imprarce.android.feature_home.ui.adapters.FilterAdapter
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentCommunityBinding
import com.imprarce.android.frencheducation.ui.BaseFragment
import com.imprarce.android.frencheducation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    private val filterItems = listOf(R.string.rating, R.string.title, R.string.views)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        mainViewModel.userFromRoom.observe(viewLifecycleOwner){ url ->
            Glide.with(requireContext())
                .load(url.imageUrl)
                .placeholder(R.drawable.image_plug)
                .into(binding.toolbar.iconUser)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}