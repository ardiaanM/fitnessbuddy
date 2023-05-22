package com.example.fitnessbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import com.example.fitnessbuddy.R
import com.example.fitnessbuddy.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {

    private lateinit var loginInfoTV1: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home) // Set the home icon

        loginInfoTV1 = binding.loginInfoTV1!!
        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")

        // Split the email to only the name
        val name = email?.substringBefore("@") ?: ""

        //only get the name
        loginInfoTV1.text = "Welcome, $name"

        toolbar.setNavigationOnClickListener {
            // Navigate back to the LoginScreen when the home icon is clicked
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonLogout.setOnClickListener {
            // Handle logout button click
            auth.signOut()
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()


            // Redirect to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
