package com.imprarce.android.feature_home.ui.detailhome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.module_tasks.room.ModuleTasksRepository
import com.imprarce.android.local.task.TaskListItem
import com.imprarce.android.local.task.room.TaskRepository
import com.imprarce.android.local.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.local.task_completed.room.TaskCompletedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailModuleTaskViewModel @Inject constructor(
    private val repositoryTaskModule: ModuleTasksRepository,
    private val repositoryTask: TaskRepository,
    private val repositoryTaskCompleted: TaskCompletedRepository
) : ViewModel() {
    private val _taskListItems = MutableLiveData<List<TaskListItem>>()
    val taskListItems: LiveData<List<TaskListItem>> = _taskListItems

    private val _taskItem = MutableLiveData<TaskListItem>()
    val taskItem: LiveData<TaskListItem> = _taskItem

    private val _taskCompletedList = MutableLiveData<List<Int>>()
    val taskCompletedList: LiveData<List<Int>> = _taskCompletedList

    private var _taskComplete = MutableLiveData<Int>()
    val taskComplete: LiveData<Int> = _taskComplete

    init {
        Log.d("DetailModuleTaskViewModel", "ViewModel created")
    }

    fun loadTasks(id_module: Int){
        viewModelScope.launch {
            when (val response = repositoryTaskModule.getTaskIdsForModule(id_module)) {
                is ResponseRoom.Success -> {
                    val tasksIdList = response.result
                    when (val tasksList = repositoryTaskModule.getTasksByIds(tasksIdList)) {
                        is ResponseRoom.Success -> {
                            _taskListItems.value = tasksList.result.map { TaskListItem(it) }
                        }
                        else -> {
                        }
                    }
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    fun getTask(id_task: Int){
        viewModelScope.launch {
            when (val response = repositoryTask.getTaskById(id_task)) {
                is ResponseRoom.Success -> {
                    _taskItem.value = response.result?.let { TaskListItem(it) }
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    fun completeTask(id_task: Int, id_user: String){
        viewModelScope.launch {
            val taskCompleted = TaskCompletedDbEntity(id_user, id_task)
            _taskComplete.value = id_task
            repositoryTaskCompleted.insertTaskCompleted(taskCompleted)
        }
    }

    fun getCompletedTasks(id_user: String) {
        viewModelScope.launch {
            when (val response = repositoryTaskCompleted.getCompletedTasksForUser(id_user)) {
                is ResponseRoom.Success -> {
                    val completedTasks = response.result
                    val taskIds = completedTasks.map { it.id_task }
                    _taskCompletedList.value = taskIds
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    fun destroyViewModel(){
        onCleared()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("DetailModuleTaskViewModel", "ViewModel destroyed")
    }
}