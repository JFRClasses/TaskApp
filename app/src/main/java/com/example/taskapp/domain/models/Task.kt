package com.example.taskapp.domain.models

data class Task(
    val creationDate: String,
    val description: String,
    val dueDate: String,
    val id: Int,
    val isDone: Boolean,
    val title: String,
    val userId: Int
)