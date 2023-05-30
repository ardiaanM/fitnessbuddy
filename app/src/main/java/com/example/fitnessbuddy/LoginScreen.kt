//Login Screen Tab as the "Profile-Menu"

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
    private lateinit var ExercisesTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home) // Set the home icon

        loginInfoTV1 = binding.loginInfoTV1!!

        ExercisesTV = binding.ExercisesTV

        ExercisesTV.setOnClickListener {
            val intent = Intent(this, ExercisesMenu::class.java)
            startActivity(intent)
        }



        val email = intent.getStringExtra("email")

        // Split the email to only the name
        val name = email?.substringBefore("@") ?: ""

        //only get the name
        loginInfoTV1.text = "Welcome, $name"


        toolbar.setNavigationOnClickListener {
            // Navigate back to the LoginActivity when the home icon is clicked
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }

        val informationLLL = binding.InformationLLL

        informationLLL.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val workoutsLL = binding.WorkoutsLL

        workoutsLL.setOnClickListener {
            val intent = Intent(this, CompleteWorkouts::class.java)
            startActivity(intent)
        }


        val buttonLogout = binding.buttonLogout

        buttonLogout.setOnClickListener {
            // Sign out the user from Firebase Auth
            FirebaseAuth.getInstance().signOut()

            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()

            // Redirect the user to the login screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val mealsLL = binding.MealsLL

        mealsLL.setOnClickListener {
            val intent = Intent(this, MealsAIMenu::class.java)
            startActivity(intent)
        }




    }

}

