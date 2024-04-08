package com.imprarce.android.frencheducation.ui.main.detailhome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.data.db.task.room.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailModuleTaskViewModel @Inject constructor(
    private val repositoryTaskModule: ModuleTasksRepository,
    private val repositoryTask: TaskRepository
) : ViewModel() {
    private val _taskListItems = MutableLiveData<List<TaskListItem>>()
    val taskListItems: LiveData<List<TaskListItem>> = _taskListItems

    private val _taskItem = MutableLiveData<TaskListItem>()
    val taskItem: LiveData<TaskListItem> = _taskItem

    private val _taskCorrectnessMap = MutableLiveData<Map<Int, Boolean>>()
    val taskCorrectnessMap: LiveData<Map<Int, Boolean>> = _taskCorrectnessMap

    fun loadTasks(id_module: Int){
        viewModelScope.launch {
            val tasksIdList = repositoryTaskModule.getTaskIdsForModule(id_module)
            val tasksList = repositoryTaskModule.getTasksByIds(tasksIdList)
            _taskListItems.value = tasksList.map { TaskListItem(it) }
        }
    }

    fun getTask(id_task: Int){
        viewModelScope.launch {
            val task = repositoryTask.getTaskById(id_task)
            _taskItem.value = TaskListItem(task)
        }
    }

    fun checkAnswer(taskId: Int, userAnswer: String) {
        val isAnswerCorrect = userAnswer.equals(_taskItem.value?.task?.answer)
        val updatedMap = _taskCorrectnessMap.value.orEmpty().toMutableMap()
        updatedMap[taskId] = isAnswerCorrect
        _taskCorrectnessMap.value = updatedMap
    }
}