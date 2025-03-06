package com.example.bonus_assignment.signUpPage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bonus_assignment.R
import androidx.appcompat.app.AppCompatActivity
import com.example.bonus_assignment.apiInstance.RetrofitInstance
import com.example.bonus_assignment.taskModel.RegisterRequest
import com.example.bonus_assignment.taskModel.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val nameField = findViewById<EditText>(R.id.et_full_name)
        val emailField = findViewById<EditText>(R.id.et_email)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val signupButton = findViewById<Button>(R.id.btn_signup)

        signupButton.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val regReq = RegisterRequest(name, email, password)
                RetrofitInstance.api.registerUser(regReq).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            if (registerResponse != null && registerResponse.message == "User registered successfully") {
                                runOnUiThread {
                                    Toast.makeText(this@SignupActivity, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                    // Handle successful registration (e.g., navigate to login)
                                    finish()
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(this@SignupActivity, "Registration failed: ${registerResponse?.error ?: "Unknown error"}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@SignupActivity, "Signup failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        runOnUiThread {
                            Toast.makeText(this@SignupActivity, "Signup failed: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }
}