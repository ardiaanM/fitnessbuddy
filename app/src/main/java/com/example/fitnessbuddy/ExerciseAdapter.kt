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
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_description_layout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {

        val workout = exerciseDataClassList[position]
        holder.nameTextView.text = workout.name // Display exercise name
        holder.typeTextView.text = workout.type // Display exercise type

        holder.itemView.setOnClickListener {
            // Start the activity to display exercise details when the item is clicked
            val workoutDetailsIntent =  Intent(context, ExercisesDetailsActivity::class.java)
            workoutDetailsIntent.putExtra("name", workout.name)
            workoutDetailsIntent.putExtra("type", workout.type)
            workoutDetailsIntent.putExtra("difficulty", workout.difficulty)
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
