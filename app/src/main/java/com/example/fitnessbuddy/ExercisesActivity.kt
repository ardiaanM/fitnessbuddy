//The Exercises Tab

package com.example.fitnessbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessbuddy.databinding.ActivityExercisesBinding
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ExercisesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExercisesBinding
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exerciseDataClassList: MutableList<ExerciseDataClass> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiKey = "QmBMIYJYJfs7HmrkNGDASFKxhbYx6zxTmMfK8G7H"

        // Initialize the RecyclerView and its adapter
        exerciseAdapter = ExerciseAdapter(exerciseDataClassList, this)
        binding.workoutsRecyclerView.adapter = exerciseAdapter
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
                    exerciseDataClassList.clear() // Clear the existing workout list

                    for (exercise in exercises) {
                        val exerciseName = exercise.path("name").asText()
                        val exerciseType = exercise.path("type").asText()
                        val exerciseMuscle = exercise.path("muscle").asText()
                        val exerciseEquipment = exercise.path("equipment").asText()
                        val exerciseDifficulty = exercise.path("difficulty").asText()
                        val exerciseInstructions = exercise.path("instructions").asText()

                        val exerciseDataClass = ExerciseDataClass(
                            exerciseName,
                            exerciseType,
                            exerciseMuscle,
                            exerciseEquipment,
                            exerciseDifficulty,
                            exerciseInstructions
                        )

                        exerciseDataClassList.add(exerciseDataClass)
                    }

                    runOnUiThread {
                        exerciseAdapter.notifyDataSetChanged() // Notify the adapter of the dataset change
                        Log.d("FitnessBuddy", "Workout List Size: ${exerciseDataClassList.size}")
                    }
                } else {
                    runOnUiThread {
                        Log.e("FitnessBuddy", "Error: Response Code $responseCode")
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Log.e("FitnessBuddy", "Error: ${e.message}")

                }
            }
        }.start()
    }
}