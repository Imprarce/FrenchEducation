package com.imprarce.android.frencheducation.ui.main.detailhome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.frencheducation.R
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.databinding.FragmentDetailModuleBinding
import com.imprarce.android.frencheducation.ui.main.adapters.TaskListAdapter
import com.imprarce.android.frencheducation.ui.main.detailhome.interfaces.OnTaskClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailModuleFragment : Fragment(),  OnTaskClickListener {
    private val viewModel: DetailModuleTaskViewModel by activityViewModels()

    var adapter = TaskListAdapter(emptyList(), this, emptyList())
    private var _binding: FragmentDetailModuleBinding? = null
    private val binding get() = _binding!!
    private var userId: String = ""
    private var moduleId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id_module = arguments?.getInt("id_module")
        val id_user = arguments?.getString("user_id")


        Log.d("DetailModuleFragment", "$id_module")

        if(id_user != null){
            userId = id_user
        }

        if(id_module != null){
            viewModel.loadTasks(id_module)
            viewModel.getCompletedTasks(userId)
            moduleId = id_module
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

        viewModel.taskCompletedList.observe(viewLifecycleOwner) { completedTasks ->
            viewModel.taskListItems.observe(viewLifecycleOwner) { taskList ->
                val taskListsContainer = TaskListsContainer(taskList, completedTasks)
                setAdapter(taskListsContainer)
            }
        }

        viewModel.taskComplete.observe(viewLifecycleOwner){task ->
            adapter.markTaskCompleted(task)
        }
    }

    private fun setAdapter(taskListsContainer: TaskListsContainer) {
        adapter = TaskListAdapter(taskListsContainer.taskList, this, taskListsContainer.taskListCompleted)
        binding.recyclerViewDetail.adapter = adapter
        binding.recyclerViewDetail.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onTaskClick(taskListItem: TaskListItem) {
        val bundle = bundleOf("id_task" to taskListItem.task.id_task, "user_id" to userId)
        findNavController().navigate(R.id.action_detailModuleFragment_to_detailTaskFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyViewModel()
    }

}