//layoutinflater for the Details in the exercises that you searched for.

package com.example.fitnessbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnessbuddy.databinding.ActivityExercisesDetailsBinding

class ExercisesDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val name = intent.getStringExtra("name")
        binding.workoutName.text = name

        val type = intent.getStringExtra("type")
        val typeText = "Type: $type"
        binding.workoutType.text = typeText

        val difficulty = intent.getStringExtra("difficulty")
        val difficultyText = "Difficulty: $difficulty"
        binding.difficulty.text = difficultyText

        val equipment = intent.getStringExtra("equipment")
        val equipmentText = "Equipment: $equipment"
        binding.equipment.text = equipmentText

        val muscle = intent.getStringExtra("muscle")
        val muscleText = "Muscle: $muscle"
        binding.muscle.text = muscleText

        val description = intent.getStringExtra("instructions")
        val descriptionText = "Instructions:   $description"
        binding.description.text = descriptionText
    }
}