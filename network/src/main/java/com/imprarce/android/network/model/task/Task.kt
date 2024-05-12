package com.imprarce.android.network.model.task

data class Task(
    var idTask: Int,
    var taskName: String,
    var videoFile: String,
    var audioFile: String,
    var exercise: String,
    var answer: String,
    var type: String
)
