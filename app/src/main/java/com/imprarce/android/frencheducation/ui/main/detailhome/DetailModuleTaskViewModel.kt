package com.imprarce.android.frencheducation.ui.main.detailhome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.frencheducation.data.db.progress.room.ModuleTasksRepository
import com.imprarce.android.frencheducation.data.db.task.TaskListItem
import com.imprarce.android.frencheducation.data.db.task.room.TaskRepository
import com.imprarce.android.frencheducation.data.db.task_completed.TaskCompletedItemList
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.frencheducation.data.db.task_completed.room.TaskCompletedRepository
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

    fun completeTask(id_task: Int, id_user: String){
        viewModelScope.launch {
            val taskCompleted = TaskCompletedDbEntity(id_user, id_task)
            repositoryTaskCompleted.insertTaskCompleted(taskCompleted)
        }
    }

    fun getCompletedTasks(id_user: String) {
        viewModelScope.launch {
            val completedTasks = repositoryTaskCompleted.getCompletedTasksForUser(id_user)
            val taskIds = completedTasks.map { it.id_task }
            _taskCompletedList.value = taskIds
        }
    }

}