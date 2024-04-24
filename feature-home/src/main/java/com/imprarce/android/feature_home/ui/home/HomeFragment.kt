package com.imprarce.android.feature_home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.feature_home.R
import com.imprarce.android.feature_home.databinding.FragmentHomeBinding
import com.imprarce.android.feature_home.ui.MainViewModel
import com.imprarce.android.feature_home.ui.adapters.FilterAdapter
import com.imprarce.android.feature_home.ui.adapters.ModuleListAdapter
import com.imprarce.android.feature_home.ui.detailhome.interfaces.OnModuleClickListener
import com.imprarce.android.local.module.ModuleListItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), OnModuleClickListener {
    private val mainViewModel: MainViewModel by activityViewModels()

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

        val customProgressBar = view.findViewById<RelativeLayout>(R.id.customProgressBar)

        customProgressBar.visibility = View.VISIBLE
        binding.overlay.visibility = View.VISIBLE

        mainViewModel.userFromRoom.observe(viewLifecycleOwner){user ->
            if(user != null){
                userId = user.id_user
                viewModel.loadModules(user.id_user)
            }
        }


        viewModel.moduleListItems.observe(viewLifecycleOwner){response ->
            setAdapter(response)
            customProgressBar.visibility = View.GONE
            binding.overlay.visibility = View.GONE
        }

        val adapter = FilterAdapter(filterItems)
        binding.recyclerViewFilters.adapter = adapter

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