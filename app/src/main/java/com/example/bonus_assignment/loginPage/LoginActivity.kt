package com.example.bonus_assignment.loginPage

import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bonus_assignment.R
import com.example.bonus_assignment.apiInstance.RetrofitInstance
import com.example.bonus_assignment.signUpPage.SignupActivity
import com.example.bonus_assignment.taskPage.TaskActivity
import com.example.bonus_assignment.taskModel.LoginRequest
import com.example.bonus_assignment.taskModel.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField = findViewById<EditText>(R.id.et_email)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val signupText = findViewById<TextView>(R.id.tv_signup)

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val loginRequest = LoginRequest(email,password)
                loginUser(loginRequest)
            }
        }

        signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginUser(userToLogin: LoginRequest) {
        RetrofitInstance.api.loginUser(userToLogin).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.message == "Login successful") {
                        val userId = loginResponse.userId // Assuming 'user_id' is a field in your LoginResponse model

                        // Show a toast for successful login
                        Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()

                        // Create an Intent to start TaskActivity
                        val intent = Intent(this@LoginActivity, TaskActivity::class.java)

                        // Pass the user_id to TaskActivity
                        intent.putExtra("USER_ID", userId)
                        // Start TaskActivity
                        startActivity(intent)

                        // Finish LoginActivity
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("LoginActivity", "Error: ${response.message()}")
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}