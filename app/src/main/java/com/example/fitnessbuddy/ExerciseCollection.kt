package com.example.fitnessbuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessbuddy.databinding.ActivityExerciseCollectionBinding

class ExerciseCollection : AppCompatActivity() {
    companion object {
        private const val EXTRA_SELECTED_EXERCISES = "selected_exercises"

        fun startActivity(context: Context, selectedExercises: ArrayList<Exercise>) {
            val intent = Intent(context, ExerciseCollection::class.java)
            intent.putExtra(EXTRA_SELECTED_EXERCISES, selectedExercises)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityExerciseCollectionBinding
    private lateinit var selectedExercises: ArrayList<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the selected exercises from the intent
        selectedExercises = intent.getSerializableExtra(EXTRA_SELECTED_EXERCISES) as ArrayList<Exercise>? ?: ArrayList()

        // Update the UI to display the selected exercises
        displaySelectedExercises()
    }

    private fun displaySelectedExercises() {
        for (exercise in selectedExercises) {
            val exerciseView = layoutInflater.inflate(R.layout.exercise_item, null)

            // Set the exercise name and image
            val exerciseImageView = exerciseView.findViewById<ImageView>(R.id.exerciseImageView)
            val exerciseNameTextView = exerciseView.findViewById<TextView>(R.id.exerciseNameTextView)
            exerciseImageView.setImageResource(exercise.imageResId)
            exerciseNameTextView.text = exercise.name

            // Add the exercise view to the exerciseContainer
            binding.exerciseContainer.addView(exerciseView)
        }
    }

}