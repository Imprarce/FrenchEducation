package com.imprarce.android.frencheducation.ui.main.detailhome.interfaces

import com.imprarce.android.frencheducation.data.db.task.TaskListItem

interface OnTaskClickListener {
    fun onTaskClick(taskListItem: TaskListItem)
}