package com.imprarce.android.feature_home.ui.detailhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.imprarce.android.feature_home.databinding.FragmentDetailTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTaskFragment : Fragment() {
    private val viewModel: DetailModuleTaskViewModel by activityViewModels()
    private var _binding: FragmentDetailTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var answer : String

    private var taskId : Int = 0
    private var userId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id_task = arguments?.getInt("id_task")
        val id_user = arguments?.getString("user_id")


        if(id_user != null) userId = id_user
        if(id_task != null){
            viewModel.getTask(id_task)
            taskId = id_task
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



        viewModel.taskItem.observe(viewLifecycleOwner){response ->
            binding.taskText.text = response.task.exercise
            answer = response.task.answer
        }

        binding.buttonAnswer.setOnClickListener {
            if(binding.taskAnswer.text.toString().trim().equals(answer.trim(), ignoreCase = true)){
                Toast.makeText(context, "Верно", Toast.LENGTH_LONG).show()
                viewModel.completeTask(taskId, userId)
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