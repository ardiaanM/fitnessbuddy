//workout collection of the API-NINJA API.

package com.example.fitnessbuddy

data class Workout(
    val name: String,
    val type: String,
    val muscle: String,
    val equipment: String,
    val difficulty: String,
    val instructions: String
)

