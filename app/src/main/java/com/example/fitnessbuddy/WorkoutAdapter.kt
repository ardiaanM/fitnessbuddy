//An adapter that shows the Exercises correctly when you search for them.

package com.example.fitnessbuddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(private val workoutList: List<Workout>, private val context: Context) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        // Inflate the layout for a single workout item and return the ViewHolder
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_item_layout, parent, false)
        return WorkoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        // Bind the workout data to the ViewHolder's UI components
        val workout = workoutList[position]
        holder.nameTextView.text = workout.name
        holder.typeTextView.text = workout.type
        holder.itemView.setOnClickListener {
            val workoutDetailsIntent =  Intent(context, WorkOutDetailsActivity::class.java)
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
        return workoutList.size
    }
}
