package com.example.bonus_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bonus_assignment.loginPage.LoginActivity
import com.example.bonus_assignment.signUpPage.SignupActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.loginButton)
        val signupButton: Button = findViewById(R.id.signupButton)

        loginButton.setOnClickListener {
            // Navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signupButton.setOnClickListener {
            // Navigate to SignupActivity
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
