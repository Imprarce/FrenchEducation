package com.imprarce.android.frencheducation.ui.main.detailhome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.databinding.FragmentDetailTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTaskFragment : Fragment() {
    private val viewModel by viewModels<DetailModuleTaskViewModel>()
    private var _binding: FragmentDetailTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var answer : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id_task = arguments?.getInt("id_task")

        if(id_task != null){
            viewModel.getTask(id_task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.taskItem.observe(viewLifecycleOwner){response ->
            binding.taskText.text = response.task.exercise
            binding.toolbar.nameFragment.text = response.task.taskName
            answer = response.task.answer
        }

        binding.buttonAnswer.setOnClickListener {
            if(binding.taskAnswer.text.toString().trim().equals(answer.trim(), ignoreCase = true)){
                Toast.makeText(context, "Верно", Toast.LENGTH_LONG).show()

                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "Неверно", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}