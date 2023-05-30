// Collection of the selected exercises.

package com.example.fitnessbuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessbuddy.databinding.ActivityExerciseCollectionBinding
import java.text.DecimalFormat
import java.text.NumberFormat

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
    private var isTimerOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the selected exercises from the intent
        selectedExercises = intent.getSerializableExtra(EXTRA_SELECTED_EXERCISES) as ArrayList<Exercise>? ?: ArrayList()

        // Update the UI to display the selected exercises
        displaySelectedExercises()
        binding.timer.setOnClickListener {
            if (!isTimerOn){
                starTimer()
            }

        }
    }

    private fun displaySelectedExercises() {
        for (exercise in selectedExercises) {
            val exerciseView = layoutInflater.inflate(R.layout.exercise_item_with_description, null)

            // Set the exercise name, image, and description
            val exerciseImageView = exerciseView.findViewById<ImageView>(R.id.exerciseImageView)
            val exerciseNameTextView = exerciseView.findViewById<TextView>(R.id.exerciseNameTextView)
            val exerciseDescriptionTextView = exerciseView.findViewById<TextView>(R.id.exerciseDescription)

            exerciseImageView.setImageResource(exercise.imageResId)
            exerciseNameTextView.text = exercise.name
            exerciseDescriptionTextView.text = exercise.description

            val previewButton: Button = exerciseView.findViewById(R.id.previewButton)
            val previewImage: ImageView = exerciseView.findViewById(R.id.exerciseImageView)


            previewButton.setOnClickListener {
                if (previewImage.visibility == View.VISIBLE) {
                    previewImage.visibility = View.GONE
                    previewButton.text = "Show" // Change the text to "Show Image" when the image is hidden
                } else {
                    previewImage.visibility = View.VISIBLE
                    previewButton.text = "Hide" // Change the text to "Hide Image" when the image is shown
                }
            }


            // Add the exercise view to the exerciseContainer
            binding.exerciseContainer.addView(exerciseView)
        }
    }


    //Timer
    private fun starTimer(){
        isTimerOn  = true
        object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f: NumberFormat = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                val timerText = f.format(min) + ":" + f.format(sec)
                binding.timer.text = timerText
            }

            override fun onFinish() {
                isTimerOn = false
                binding.timer.text = "Start Timer"
            }
        }.start()
    }

}