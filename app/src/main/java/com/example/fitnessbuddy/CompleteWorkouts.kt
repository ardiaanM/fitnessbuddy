//The Exercises Tab

package com.example.fitnessbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbuddy.databinding.ActivityCompleteWorkoutsBinding
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CompleteWorkouts : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteWorkoutsBinding
    private lateinit var workoutAdapter: WorkoutAdapter
    private val workoutList: MutableList<Workout> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiKey = "QmBMIYJYJfs7HmrkNGDASFKxhbYx6zxTmMfK8G7H"

        // Initialize the RecyclerView and its adapter
        workoutAdapter = WorkoutAdapter(workoutList, this)
        binding.workoutsRecyclerView.adapter = workoutAdapter
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchButton.setOnClickListener {
            val muscle = binding.searchField.text.toString()
            searchExercisesByMuscle(apiKey, muscle)
        }
    }

    private fun searchExercisesByMuscle(apiKey: String, muscle: String) {
        val apiUrl = "https://api.api-ninjas.com/v1/exercises?muscle=$muscle"

        Log.d("FitnessBuddy", "API URL: $apiUrl")

        Thread {
            try {
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("X-Api-Key", apiKey)

                val responseCode = connection.responseCode
                Log.d("FitnessBuddy", "API Response Code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseStream: InputStream = connection.inputStream

                    val mapper = ObjectMapper()
                    val root: JsonNode = mapper.readTree(responseStream)
                    Log.d("FitnessBuddy", "root: $root")
                    // Process the JSON response as needed
                    val exercises = root
                    //Log.d("FitnessBuddy", "Exercise Count: ${exercises.size()}")
                    workoutList.clear() // Clear the existing workout list

                    for (exercise in exercises) {
                        val exerciseName = exercise.path("name").asText()
                        val exerciseType = exercise.path("type").asText()
                        val exerciseMuscle = exercise.path("muscle").asText()
                        val exerciseEquipment = exercise.path("equipment").asText()
                        val exerciseDifficulty = exercise.path("difficulty").asText()
                        val exerciseInstructions = exercise.path("instructions").asText()

                        val workout = Workout(
                            exerciseName,
                            exerciseType,
                            exerciseMuscle,
                            exerciseEquipment,
                            exerciseDifficulty,
                            exerciseInstructions
                        )

                        workoutList.add(workout)
                    }

                    runOnUiThread {
                        workoutAdapter.notifyDataSetChanged() // Notify the adapter of the dataset change
                        Log.d("FitnessBuddy", "Workout List Size: ${workoutList.size}")
                    }
                } else {
                    runOnUiThread {
                        Log.e("FitnessBuddy", "Error: Response Code $responseCode")
                        // Show error message in UI or handle the error scenario
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Log.e("FitnessBuddy", "Error: ${e.message}")
                    // Show error message in UI or handle the error scenario
                }
            }
        }.start()
    }
}