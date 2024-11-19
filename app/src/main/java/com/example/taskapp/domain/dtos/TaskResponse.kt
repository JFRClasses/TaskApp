package com.example.taskapp.domain.dtos

import com.example.taskapp.domain.models.Task

data class TaskResponse(
    val tasks : List<Task>,
    val completedTasks : Int,
    val pendingTasks : Int,
    val expiredTasks : Int
)
