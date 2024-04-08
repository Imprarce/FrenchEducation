package com.imprarce.android.frencheducation.ui.main.detailhome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.module.ModuleListItem
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.databinding.FragmentDetailModuleBinding
import com.imprarce.android.frencheducation.ui.main.adapters.ModuleListAdapter
import com.imprarce.android.frencheducation.ui.main.adapters.TaskListAdapter
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnTaskClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailModuleFragment : Fragment(),  OnTaskClickListener{
    private val viewModel by viewModels<DetailModuleTaskViewModel>()

    private var _binding: FragmentDetailModuleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id_module = arguments?.getInt("id_module")
        Log.d("DetailModuleFragment", "$id_module")
        if(id_module != null){
            viewModel.loadTasks(id_module)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailModuleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.nameFragment.text = "Название модуля"

        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.taskListItems.observe(viewLifecycleOwner){ response ->
            setAdapter(response)
        }
    }

    private fun setAdapter(taskList: List<TaskListItem>) {
        val adapter = TaskListAdapter(taskList, this)
        binding.recyclerViewDetail.adapter = adapter
        binding.recyclerViewDetail.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onTaskClick(taskListItem: TaskListItem) {
        val bundle = bundleOf("id_task" to taskListItem.task.id_task)
        findNavController().navigate(R.id.action_detailModuleFragment_to_detailTaskFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}