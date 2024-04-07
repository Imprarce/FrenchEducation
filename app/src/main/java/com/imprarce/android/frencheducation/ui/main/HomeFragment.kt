package com.imprarce.android.frencheducation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.databinding.FragmentHomeBinding
import com.imprarce.android.frencheducation.ui.BaseFragment
import com.imprarce.android.frencheducation.ui.greeting.GreetingViewModel
import com.imprarce.android.frencheducation.ui.main.adapters.FilterAdapter
import com.imprarce.android.frencheducation.ui.main.adapters.ModuleListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel by viewModels<MainViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val filterItems = listOf(R.string.title, R.string.level, R.string.progress)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        viewModel.moduleListItems.observe(viewLifecycleOwner){
            initRecyclerView(binding)
        }

    }

    private fun initRecyclerView(binding: FragmentHomeBinding) {
        val adapter = ModuleListAdapter { module ->
        }
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}