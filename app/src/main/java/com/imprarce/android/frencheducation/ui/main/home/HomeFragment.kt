package com.imprarce.android.frencheducation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.module.ModuleListItem
import com.imprarce.android.frencheducation.databinding.FragmentHomeBinding
import com.imprarce.android.frencheducation.ui.BaseFragment
import com.imprarce.android.frencheducation.ui.main.adapters.FilterAdapter
import com.imprarce.android.frencheducation.ui.main.adapters.ModuleListAdapter
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnModuleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), OnModuleClickListener {
    private val viewModel by viewModels<HomeViewModel>()

    private var userId : String? = null
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

        viewModel.setUserId()

        viewModel.userId.observe(viewLifecycleOwner){user ->
            userId = user
        }

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

        viewModel.moduleListItems.observe(viewLifecycleOwner){response ->
            setAdapter(response)
        }

        viewModel.userPhotoUrl.observe(viewLifecycleOwner){ url ->
            Glide.with(requireContext())
                .load(url)
                .placeholder(R.drawable.image_plug)
                .into(binding.toolbar.iconUser)
        }

    }

    private fun setAdapter(moduleList: List<ModuleListItem>) {
        val adapter = ModuleListAdapter(moduleList, this)
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onModuleClick(moduleListItem: ModuleListItem) {
        val bundle = bundleOf("id_module" to moduleListItem.module.id_module, "user_id" to userId)
        findNavController().navigate(R.id.action_homeFragment_to_nav_detail, bundle)
    }
}