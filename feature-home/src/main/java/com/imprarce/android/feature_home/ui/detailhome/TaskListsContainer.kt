package com.imprarce.android.feature_home.ui.detailhome

import com.imprarce.android.local.task.TaskListItem

data class TaskListsContainer(
    val taskList: List<TaskListItem>,
    val taskListCompleted: List<Int>
)
