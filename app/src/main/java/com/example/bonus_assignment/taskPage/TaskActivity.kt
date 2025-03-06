package com.example.bonus_assignment.taskPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bonus_assignment.taskModel.*
import com.example.bonus_assignment.apiInstance.RetrofitInstance
import com.example.bonus_assignment.databinding.ActivityTaskBinding
import com.example.bonus_assignment.taskAdopter.TaskAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskActivity : AppCompatActivity() {

    private val taskResponseList = mutableListOf<TaskResponse>()
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize RecyclerView
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskResponseList) { task ->
            deleteTask(task.taskId, userId) // Use the TaskResponse object directly
        }

        binding.rvTasks.adapter = taskAdapter

        // Load tasks for the specific user
        loadTasks(userId)

        // Handle Add Button click
        binding.btnAddTask.setOnClickListener {
            val newTask = binding.etTask.text.toString().trim()
            val taskDescription = binding.disTask.text.toString().trim()
            if (newTask.isNotEmpty() && taskDescription.isNotEmpty()) {
                addTask(newTask, taskDescription, userId) // Add the task
                binding.etTask.text.clear() // Clear the input fields
                binding.disTask.text.clear()
            } else {
                Toast.makeText(this, "Please enter both task and description", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTasks(userId: String) {
        val request = TaskRequest(userId)

        RetrofitInstance.api.getTasks(request).enqueue(object : Callback<List<TaskResponse>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<TaskResponse>>, response: Response<List<TaskResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let { tasks ->
                        taskResponseList.clear()
                        taskResponseList.addAll(tasks)
                        taskAdapter.notifyDataSetChanged()
                    } ?: run {
                        Toast.makeText(this@TaskActivity, "No tasks found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@TaskActivity, "Failed to load tasks: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                Toast.makeText(this@TaskActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addTask(title: String, description: String, userId: String) {
        val request = AddTaskRequest(userId, title, description)

        RetrofitInstance.api.addTask(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@TaskActivity, "Task added successfully", Toast.LENGTH_SHORT).show()
                    loadTasks(userId)
                } else {
                    Toast.makeText(this@TaskActivity, "Failed to add task", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@TaskActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteTask(taskId: String, userId: String) {
        val request = DeleteTaskRequest(taskId,userId)

        RetrofitInstance.api.deleteTask(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@TaskActivity, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                    loadTasks(userId)
                } else {
                    Toast.makeText(this@TaskActivity, "Failed to delete task", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@TaskActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
