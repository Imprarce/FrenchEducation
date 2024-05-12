package com.imprarce.android.feature_home.ui.detailhome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imprarce.android.feature_home.ui.helpers.Converter
import com.imprarce.android.local.ResponseRoom
import com.imprarce.android.local.module_tasks.room.ModuleTasksRepository
import com.imprarce.android.local.task.TaskListItem
import com.imprarce.android.local.task.room.TaskRepository
import com.imprarce.android.local.task_completed.room.TaskCompletedDbEntity
import com.imprarce.android.local.task_completed.room.TaskCompletedRepository
import com.imprarce.android.network.ResponseNetwork
import com.imprarce.android.network.model.task.Task
import com.imprarce.android.network.repository.module_progress.ModuleProgressNetworkRepository
import com.imprarce.android.network.repository.module_tasks.ModuleTasksNetworkRepository
import com.imprarce.android.network.repository.task.TaskNetworkRepository
import com.imprarce.android.network.repository.task_completed.TaskCompletedNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailModuleTaskViewModel @Inject constructor(
    private val moduleTasksRepository: ModuleTasksRepository,
    private val taskRepository: TaskRepository,
    private val taskCompletedRepository: TaskCompletedRepository,
    private val moduleProgressNetworkRepository: ModuleProgressNetworkRepository,
    private val moduleTasksNetworkRepository: ModuleTasksNetworkRepository,
    private val taskNetworkRepository: TaskNetworkRepository,
    private val taskCompletedNetworkRepository: TaskCompletedNetworkRepository
) : ViewModel() {
    private val _taskRoomListItems = MutableLiveData<List<TaskListItem>>()
    val taskRoomListItems: LiveData<List<TaskListItem>> = _taskRoomListItems

    private val _taskRoomItem = MutableLiveData<TaskListItem>()
    val taskRoomItem: LiveData<TaskListItem> = _taskRoomItem

    private val _taskCompletedRoomList = MutableLiveData<List<Int>>()
    val taskCompletedRoomList: LiveData<List<Int>> = _taskCompletedRoomList

    private var _taskRoomComplete = MutableLiveData<Int>()
    val taskRoomComplete: LiveData<Int> = _taskRoomComplete

    private val _taskNetworkListItems = MutableLiveData<List<Task>>()
    val taskNetworkListItems: LiveData<List<Task>> = _taskNetworkListItems

    private val _taskNetworkItem = MutableLiveData<Task>()
    val taskNetworkItem: LiveData<Task> = _taskNetworkItem

    private val _taskCompletedNetworkList = MutableLiveData<List<Int>>()
    val taskCompletedNetworkList: LiveData<List<Int>> = _taskCompletedNetworkList

    private var _taskNetworkComplete = MutableLiveData<Int>()
    val taskNetworkComplete: LiveData<Int> = _taskNetworkComplete

    private var currentModuleId = 0

    init {
        Log.d("DetailModuleTaskViewModel", "ViewModel created")
    }

    fun setCurrentModuleId(id_module: Int){
        currentModuleId = id_module
    }

    // TODO: ДОДЕЛАТЬ ЛОКАЛЬНОЕ ХРАНЕНИЕ
    fun loadTasksNetwork(id_module: Int) {
        viewModelScope.launch {
            when (val response = moduleTasksNetworkRepository.getModuleTasks(id_module)) {
                is ResponseNetwork.Success -> {
                    val moduleTasksList = response.result
                    val taskIdList = moduleTasksList.map { it.idTask }
                    when (val tasksList = taskNetworkRepository.getTasks(taskIdList)) {
                        is ResponseNetwork.Success -> {
                            _taskNetworkListItems.value = tasksList.result!!
                            _taskRoomListItems.value =
                                tasksList.result.map { TaskListItem(Converter.toTaskDbEntity(it)) }
                        }
                        else -> {
                        }
                    }
                }
                is ResponseNetwork.Failure -> {
                }
                is ResponseNetwork.Loading -> {
                }
            }
        }
    }

    fun getTaskNetwork(id_task: Int) {
        viewModelScope.launch {
            when (val response = taskNetworkRepository.getTaskById(id_task)) {
                is ResponseNetwork.Success -> {
                    _taskNetworkItem.value = response.result!!
                    _taskRoomItem.value =
                        response.result?.let { TaskListItem(Converter.toTaskDbEntity(it)) }
                }
                is ResponseNetwork.Failure -> {
                }
                is ResponseNetwork.Loading -> {
                }
            }
        }
    }

    fun completeTaskNetwork(id_task: Int) {
        viewModelScope.launch {
            when (val response = taskCompletedNetworkRepository.createTaskCompleted(id_task)) {
                is ResponseNetwork.Success -> {
                    _taskNetworkComplete.value = id_task
                    _taskRoomComplete.value = id_task
                }
                is ResponseNetwork.Failure -> {
                }
                is ResponseNetwork.Loading -> {
                }
            }
        }
    }

    fun getCompletedTasksNetwork() {
        viewModelScope.launch {
            when (val response = taskCompletedNetworkRepository.getCompletedTasksForUser()) {
                is ResponseNetwork.Success -> {
                    val completedTasks = response.result
                    val taskIds = completedTasks?.map { it.idTask }
                    if (taskIds != null) {
                        _taskCompletedNetworkList.value = taskIds!!
                        _taskCompletedRoomList.value = taskIds!!
                        updateModuleProgressNetwork(taskIds)
                    }
                }
                is ResponseNetwork.Failure -> {
                }
                is ResponseNetwork.Loading -> {
                }
            }
        }
    }

    private suspend fun updateModuleProgressNetwork(taskList: List<Int>) {
        delay(2000)
        var totalTasksCount = 0
        val currentModuleTasksList = _taskNetworkListItems.value

        if (currentModuleTasksList != null) {
            totalTasksCount = currentModuleTasksList.size
        } else {
            return
        }

        val currentModuleTasksCompleted = mutableListOf<Int>()

        taskList.forEach {  taskCompleteId ->
            currentModuleTasksList.forEach {  currentTask ->
                if(taskCompleteId == currentTask.idTask){
                    currentModuleTasksCompleted.add(taskCompleteId)
                }
            }
        }

        val progress = if (totalTasksCount > 0) {
            (currentModuleTasksCompleted.size.toDouble() / totalTasksCount.toDouble() * 100).toInt()
        } else {
            return
        }

        when (val updateResponse = moduleProgressNetworkRepository.setModuleProgress(currentModuleId, progress)) {
                is ResponseNetwork.Success -> {

                }
                is ResponseNetwork.Failure -> {
                    println("Возникла какая-то ошибка при обновлении")
                }
                is ResponseNetwork.Loading -> {}
            }
    }

    fun loadTasksRoom(id_module: Int) {
        viewModelScope.launch {
            when (val response = moduleTasksRepository.getTaskIdsForModule(id_module)) {
                is ResponseRoom.Success -> {
                    val tasksIdList = response.result
                    when (val tasksList = moduleTasksRepository.getTasksByIds(tasksIdList)) {
                        is ResponseRoom.Success -> {
                            _taskRoomListItems.value = tasksList.result.map { TaskListItem(it) }
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

    fun getTaskRoom(id_task: Int) {
        viewModelScope.launch {
            when (val response = taskRepository.getTaskById(id_task)) {
                is ResponseRoom.Success -> {
                    _taskRoomItem.value = response.result?.let { TaskListItem(it) }
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    fun completeTaskRoom(id_task: Int, id_user: String) {
        viewModelScope.launch {
            val taskCompleted = TaskCompletedDbEntity(id_user, id_task)
            _taskRoomComplete.value = id_task
            taskCompletedRepository.insertTaskCompleted(taskCompleted)
        }
    }

    fun getCompletedTasksRoom(id_user: String) {
        viewModelScope.launch {
            when (val response = taskCompletedRepository.getCompletedTasksForUser(id_user)) {
                is ResponseRoom.Success -> {
                    val completedTasks = response.result
                    val taskIds = completedTasks.map { it.id_task }
                    _taskCompletedRoomList.value = taskIds
                }
                is ResponseRoom.Failure -> {
                }
                is ResponseRoom.Loading -> {
                }
            }
        }
    }

    fun destroyViewModel() {
        onCleared()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("DetailModuleTaskViewModel", "ViewModel destroyed")
    }
}