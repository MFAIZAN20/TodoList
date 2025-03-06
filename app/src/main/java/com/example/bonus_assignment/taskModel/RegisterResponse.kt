package com.example.bonus_assignment.taskModel

data class RegisterResponse(
    val message: String,
    val userId: String? = null,
    val error: String? = null
)