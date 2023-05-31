// An adapter that shows the Exercises correctly when you search for them.

package com.example.fitnessbuddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exerciseDataClassList: List<ExerciseDataClass>, private val context: Context) :
    RecyclerView.Adapter<ExerciseAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // display exercise information
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        // Inflate the layout for a single workout item and return the ViewHolder
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_description_layout, parent, false) // Modify the code if needed: Update the layout resource file
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        // Bind the workout data to the ViewHolder's UI components
        val workout = exerciseDataClassList[position]
        holder.nameTextView.text = workout.name // Display exercise name
        holder.typeTextView.text = workout.type // Display exercise type

        holder.itemView.setOnClickListener {
            // Start the activity to display exercise details when the item is clicked
            val workoutDetailsIntent =  Intent(context, ExercisesDetailsActivity::class.java)
            workoutDetailsIntent.putExtra("name", workout.name) // Pass exercise name as an extra
            workoutDetailsIntent.putExtra("type", workout.type) // Pass exercise type as an extra
            workoutDetailsIntent.putExtra("difficulty", workout.difficulty) // Modify the code if needed: Pass additional exercise details as extras
            workoutDetailsIntent.putExtra("equipment", workout.equipment)
            workoutDetailsIntent.putExtra("muscle", workout.muscle)
            workoutDetailsIntent.putExtra("instructions", workout.instructions)
            context.startActivity(workoutDetailsIntent)
        }
    }

    override fun getItemCount(): Int {
        return exerciseDataClassList.size // Return the number of exersises in the list.
    }
}
