package com.imprarce.android.frencheducation.data.db.task

import com.imprarce.android.frencheducation.data.db.task.room.TaskDbEntity

data class TaskListItem (
    val task : TaskDbEntity,
    var isCorrectAnswer : Boolean = false
)
