package com.example.bonus_assignment.taskModel

data class TaskResponse(
    val userId: String,
    val title: String,
    val description: String,
    val taskId: String
)