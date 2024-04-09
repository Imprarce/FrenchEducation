package com.imprarce.android.frencheducation.ui.main.detailhome

import com.imprarce.android.frencheducation.data.db.task.TaskListItem

data class TaskListsContainer(
    val taskList: List<TaskListItem>,
    val taskListCompleted: List<Int>
)
