package com.example.fitnessbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessbuddy.databinding.ActivityExercisesMenuBinding

data class Exercise(val name: String, val imageResId: Int, var isSelected: Boolean = false)

class ExercisesMenu : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesMenuBinding
    private lateinit var exerciseContainer: LinearLayout
    private lateinit var exerciseInfoTextView: TextView
    private lateinit var collectorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bodyPartSpinner: Spinner = binding.bodyPartSpinner
        exerciseContainer = binding.exerciseContainer
        collectorContainer = binding.collectorContainer

        bodyPartSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedBodyPart = parent?.getItemAtPosition(position).toString()
                val exercises = getExercisesForBodyPart(selectedBodyPart)

                displayExercises(exercises)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected
            }
        }

        val sendButton: Button = binding.sendButton

        sendButton.setOnClickListener {
            val selectedExercises = getSelectedExercises()
            ExerciseCollection.startActivity(this, selectedExercises)
        }

    }

    private fun getExercisesForBodyPart(bodyPart: String): List<Exercise> {
        return when (bodyPart) {
            "Legs" -> listOf(
                Exercise("Squats", R.drawable.legs_squats),
                Exercise("Lunges", R.drawable.legs_lunges),
                Exercise("Leg Press", R.drawable.legs_bulgarian)
            )
            "Back" -> listOf(
                Exercise("Exercise 1 for Back", R.drawable.back_deadlift),
                Exercise("Exercise 2 for Back", R.drawable.back_rows),
                Exercise("Exercise 3 for Back", R.drawable.back_pullup)
            )
            // Add more body parts and their corresponding exercises
            else -> emptyList()
        }
    }

    private fun getSelectedExercises(): ArrayList<Exercise> {
        val selectedExercises = ArrayList<Exercise>()

        for (i in 0 until exerciseContainer.childCount) {
            val exerciseView = exerciseContainer.getChildAt(i)
            val exercise = exerciseView.tag as Exercise

            if (exercise.isSelected) {
                selectedExercises.add(exercise)
            }
        }

        return selectedExercises
    }


    //grabs exercise_item layout
    private fun displayExercises(exercises: List<Exercise>) {
        exerciseContainer.removeAllViews()

        exercises.forEach { exercise ->
            val exerciseView = layoutInflater.inflate(R.layout.exercise_item, exerciseContainer, false)

            val exerciseImageView: ImageView = exerciseView.findViewById(R.id.exerciseImageView)
            val exerciseNameTextView: TextView = exerciseView.findViewById(R.id.exerciseNameTextView)

            exerciseImageView.setImageResource(exercise.imageResId)
            exerciseNameTextView.text = exercise.name

            exerciseView.setOnClickListener {
                exercise.isSelected = !exercise.isSelected
                updateExerciseItemView(exerciseView, exercise.isSelected)

                if (exercise.isSelected) {
                    addExerciseToCollector(exercise)
                } else {
                    removeExerciseFromCollector(exercise)
                }
            }

            exerciseContainer.addView(exerciseView)
        }
    }

    private fun updateExerciseItemView(exerciseView: View, isSelected: Boolean) {
        val exerciseNameTextView: TextView = exerciseView.findViewById(R.id.exerciseNameTextView)
        exerciseNameTextView.isSelected = isSelected
    }

    private fun addExerciseToCollector(exercise: Exercise) {
        val exerciseCollectorView = layoutInflater.inflate(R.layout.exercise_collector_item, collectorContainer, false)
        val exerciseNameTextView: TextView = exerciseCollectorView.findViewById(R.id.collectedExerciseNameTextView)
        exerciseNameTextView.text = exercise.name

        collectorContainer.addView(exerciseCollectorView)
    }

    private fun removeExerciseFromCollector(exercise: Exercise) {
        for (i in 0 until collectorContainer.childCount) {
            val collectorItemView = collectorContainer.getChildAt(i)
            val exerciseNameTextView: TextView = collectorItemView.findViewById(R.id.collectedExerciseNameTextView)
            if (exerciseNameTextView.text == exercise.name) {
                collectorContainer.removeViewAt(i)
                break
            }
        }
    }
}