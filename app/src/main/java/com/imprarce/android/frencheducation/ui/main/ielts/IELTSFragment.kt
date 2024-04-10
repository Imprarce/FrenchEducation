package com.imprarce.android.frencheducation.ui.main.ielts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentIELTSBinding
import com.imprarce.android.frencheducation.ui.BaseFragment
import com.imprarce.android.frencheducation.ui.MainViewModel
import com.imprarce.android.frencheducation.ui.main.adapters.FilterAdapter


class IELTSFragment : BaseFragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding : FragmentIELTSBinding? = null
    private val binding get() = _binding!!

    private val filterItems = listOf(R.string.title, R.string.level, R.string.progress)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIELTSBinding.inflate(inflater, container, false)

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