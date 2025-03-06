package com.example.bonus_assignment.apiService

import com.example.bonus_assignment.taskModel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/get_user_id")
    fun getUserId(@Body idToken: Map<String, String>): Call<UserIdResponse>

    @POST("/tasks")
    fun getTasks(@Body request: TaskRequest): Call<List<TaskResponse>>

    @POST("/add_task")
    fun addTask(@Body request: AddTaskRequest): Call<Void>

    @POST("/delete_task")
    fun deleteTask(@Body request: DeleteTaskRequest): Call<Void>
}
