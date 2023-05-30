//The Workout Planner Tab.

package com.example.fitnessbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessbuddy.databinding.ActivityExercisesMenuBinding

import android.os.Parcel
import android.os.Parcelable


// The ExercisesMenu class handles the user interface and interaction
// Exercise data class represents and manages the individual exercises in the application.

data class Exercise(
    val name: String,
    val imageResId: Int,
    val description: String,
    var isSelected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageResId)
        parcel.writeString(description)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}




class ExercisesMenu : AppCompatActivity() {
    private lateinit var binding: ActivityExercisesMenuBinding
    private lateinit var exerciseContainer: LinearLayout
    private lateinit var collectorContainer: LinearLayout
    private lateinit var clearExerciseButton: Button
    val selectedExercises: ArrayList<Exercise> = arrayListOf()

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

                displayExercises(exercises.toList()) // Pass exercises.toList() instead of exercises
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected
            }
        }

        val sendButton: Button = binding.sendButton

        sendButton.setOnClickListener {
            if (selectedExercises.isEmpty()) {
                Toast.makeText(this, "Please choose an exercise", Toast.LENGTH_SHORT).show()
            } else {
                ExerciseCollection.startActivity(this, selectedExercises)
            }
        }


        clearExerciseButton = binding.clearExerciseButton
        clearExerciseButton.setOnClickListener {
            clearCollectorContainer()
        }


    }

    private fun getExercisesForBodyPart(bodyPart: String): List<Exercise> {
        return when (bodyPart) {
            "Legs" -> listOf(
                Exercise("Squats", R.drawable.workoutssquats, "Stand with your feet shoulder-width apart, lower your body by bending your knees and hips, keeping your back straight. Push through your heels to return to the starting position."),
                Exercise("Lunges", R.drawable.legs_lunges, "Take a step forward with one leg, lowering your body until both knees are bent at a 90-degree angle. Push through the front heel to return to the starting position and repeat with the other leg."),
                Exercise("Leg Press", R.drawable.legs_bulgarian, "Sit on the leg press machine with your feet on the footplate, shoulder-width apart. Push the footplate away from your body by extending your legs, then slowly bend your knees to return to the starting position."),
                Exercise("Calf Raises", R.drawable.legs_calfraises, "Stand on a raised surface with the balls of your feet positioned at the edge. Raise your heels as high as possible, then lower them below the surface to stretch your calf muscles."),
                Exercise("Step-Ups", R.drawable.legs_stepups, "Step onto a raised platform with one foot, pushing through the heel to lift your body up. Step back down and repeat with the other leg."),
                Exercise("Hamstring Curls", R.drawable.legs_hamstringcurls, "Lie face down on a leg curl machine and position your ankles under the pad. Curl your legs up towards your glutes, squeezing your hamstrings at the top."),
                Exercise("Romanian Deadlifts", R.drawable.legs_romaniandeadlifts, "Hold a barbell or dumbbells in front of your thighs, keeping your knees slightly bent. Hinge at the hips and lower the weight, keeping your back straight, until you feel a stretch in your hamstrings. Return to the starting position."),
                Exercise("Box Jumps", R.drawable.legs_boxjumps, "Stand facing a sturdy box or platform. Jump onto the box, landing with both feet simultaneously. Step back down and repeat."),
            )
            "Back" -> listOf(
                Exercise("Deadlifts", R.drawable.back_deadlift, "\"Stand with your feet hip-width apart, keeping your back straight and your knees slightly bent. Bend at the hips and grip the barbell with an overhand grip. Lift the barbell by extending your hips and knees, keeping the barbell close to your body. Lower the barbell back down to the starting position, maintaining control throughout the movement.\""),
                Exercise("Rows", R.drawable.back_rows, "\"Stand with your feet shoulder-width apart and slightly bend your knees. Hold a dumbbell in each hand with your palms facing your body. Bend forward at the hips, keeping your back straight. Pull the dumbbells towards your body by squeezing your shoulder blades together. Lower the dumbbells back down in a controlled manner.\""),
                Exercise("Pull-ups", R.drawable.back_pullup, "Hang from a pull-up bar with your palms facing away from you and your hands slightly wider than shoulder-width apart. Engage your core and pull your body up by bending your elbows and squeezing your shoulder blades together. Continue pulling until your chin is above the bar, then lower your body back down with control."),
                Exercise("Lat Pulldowns", R.drawable.back_latpulldowns, "Sit at a lat pulldown machine and grip the bar with your hands slightly wider than shoulder-width apart. Pull the bar down towards your chest while keeping your back straight, then slowly return to the starting position."),
                Exercise("Bent-Over Rows", R.drawable.fitnessbuddyicon, "Hold a barbell or dumbbells with your palms facing down. Bend forward at the hips, keeping your back straight, and lift the weights towards your lower chest. Lower them back down in a controlled manner."),
                Exercise("T-Bar Rows", R.drawable.fitnessbuddyicon, "Place one end of a barbell into a landmine or secure it in a corner. Stand with your feet shoulder-width apart and grip the other end of the barbell. Bend forward at the hips, keeping your back straight, and pull the barbell towards your abdomen. Lower it back down."),
                Exercise("Reverse Flyes", R.drawable.fitnessbuddyicon, "Stand with your feet shoulder-width apart and hold a pair of dumbbells. Bend forward at the hips, keeping a slight bend in your knees. Raise the dumbbells out to the sides, squeezing your shoulder blades together. Lower them back down."),
                Exercise("Supermans", R.drawable.fitnessbuddyicon, "Lie face down on the floor with your arms extended in front of you. Simultaneously lift your arms, chest, and legs off the ground, squeezing your lower back. Hold for a moment, then lower back down."),
                Exercise("Seated Cable Rows", R.drawable.fitnessbuddyicon, "Sit at a cable row machine with your feet resting against the footplates. Grip the handles with your palms facing each other. Pull the handles towards your abdomen, squeezing your shoulder blades together. Extend your arms back out in a controlled manner."),
                Exercise("Single-Arm Dumbbell Rows", R.drawable.fitnessbuddyicon, "Place one knee and the corresponding hand on a flat bench, with your back parallel to the ground. Hold a dumbbell in the opposite hand, letting it hang straight down. Pull the dumbbell up towards your hip, keeping your elbow close to your body. Lower it back down."),
            )
            "Chest" -> listOf(
                Exercise("Dumbbell Flies", R.drawable.chest_dumbbellflies, "Lie on a flat bench with a dumbbell in each hand. Extend your arms out to the sides, keeping a slight bend in your elbows. Slowly bring the dumbbells together in a wide arc motion, feeling the stretch in your chest."),
                Exercise("Bench Press", R.drawable.chest_benchpress, "Lie on a flat bench with a barbell in your hands, palms facing forward. Lower the barbell towards your chest, then push it back up to the starting position."),
                Exercise("Push-ups", R.drawable.fitnessbuddyicon, "Start in a high plank position with your hands shoulder-width apart. Lower your body by bending your elbows, keeping your back straight. Push yourself back up to the starting position."),
                Exercise("Incline Dumbbell Press", R.drawable.fitnessbuddyicon, "Lie on an incline bench with a dumbbell in each hand. Extend your arms upwards, palms facing forward. Lower the dumbbells towards your chest, then press them back up."),
                Exercise("Chest Dips", R.drawable.fitnessbuddyicon, "Position your hands on parallel bars, with your arms fully extended and supporting your body weight. Lower your body by bending your elbows, then push yourself back up."),
                Exercise("Cable Flyes", R.drawable.fitnessbuddyicon, "Stand in the middle of a cable machine, holding a handle in each hand. Step forward slightly and bring your arms together in front of you, crossing them over each other. Slowly return to the starting position."),
                Exercise("Chest Press Machine", R.drawable.fitnessbuddyicon, "Sit at a chest press machine with your feet flat on the floor. Push the handles away from your body until your arms are fully extended, then slowly bring them back towards your chest."),
            )
            "Biceps" -> listOf(
                Exercise("Dumbbell Curls", R.drawable.fitnessbuddyicon, "Stand with a dumbbell in each hand, palms facing forward. Curl the dumbbells towards your shoulders, squeezing your biceps at the top. Lower them back down in a controlled manner."),
                Exercise("Barbell Curls", R.drawable.fitnessbuddyicon, "Stand with a barbell in front of you, hands shoulder-width apart and palms facing forward. Curl the barbell towards your shoulders, keeping your elbows close to your body. Lower it back down."),
                Exercise("Hammer Curls", R.drawable.fitnessbuddyicon, "Stand with a dumbbell in each hand, palms facing your body. Curl the dumbbells towards your shoulders, keeping your palms facing inwards. Lower them back down."),
                Exercise("Preacher Curls", R.drawable.fitnessbuddyicon, "Sit at a preacher curl bench, resting your arms on the pad. Grip an EZ bar with an underhand grip. Curl the barbell towards your shoulders, keeping your upper arms stationary. Lower it back down."),
                Exercise("Chin-ups", R.drawable.fitnessbuddyicon, "Hang from a pull-up bar with your palms facing towards you. Pull your body upwards until your chin is above the bar, then lower yourself back down in a controlled manner."),
                Exercise("Concentration Curls", R.drawable.fitnessbuddyicon, "Sit on a bench, holding a dumbbell in one hand. Rest your elbow on the inside of your thigh, palm facing upwards. Curl the dumbbell towards your shoulder, squeezing your biceps. Lower it back down."),
                Exercise("Cable Curls", R.drawable.fitnessbuddyicon, "Stand in the middle of a cable machine, holding a handle in each hand. Curl the handles towards your shoulders, keeping your elbows close to your sides. Return to the starting position."),
            )
            "Triceps" -> listOf(
                Exercise("Tricep Dips", R.drawable.triceps_tricepdips, "Position your hands on parallel bars, with your arms fully extended and supporting your body weight. Lower your body by bending your elbows, then push yourself back up."),
                //Exercise("Tricep Pushdowns", R.drawable.triceps_triceppushdowns, "Stand in front of a cable machine, holding a straight bar with an overhand grip. Push the bar down towards your thighs, fully extending your arms. Return to the starting position."),
                Exercise("Skull Crushers", R.drawable.triceps_skullcrushers, "Lie on a flat bench with a barbell or dumbbells in your hands, arms extended over your chest. Bend your elbows, bringing the weight towards your forehead. Extend your arms back up to the starting position."),
                Exercise("Close Grip Bench Press", R.drawable.triceps_closegripbenchpress, "Lie on a flat bench with a barbell in your hands, hands placed closer together than shoulder-width apart. Lower the barbell towards your chest, then push it back up to the starting position."),
                Exercise("Overhead Tricep Extensions", R.drawable.fitnessbuddyicon, "Stand or sit with a dumbbell in one hand. Raise the dumbbell overhead, keeping your upper arm close to your head. Bend your elbow, lowering the dumbbell behind your head. Extend your arm back up to the starting position."),
                Exercise("Tricep Kickbacks", R.drawable.fitnessbuddyicon, "Hold a dumbbell in one hand, bending forward at the hips with your back straight. Extend your arm backwards, squeezing your tricep. Return to the starting position."),
                Exercise("Diamond Push-ups", R.drawable.fitnessbuddyicon, "Start in a high plank position with your hands close together, forming a diamond shape with your thumbs and index fingers. Lower your body by bending your elbows, keeping your back straight. Push yourself back up to the starting position."),
            )
            "Abs" -> listOf(
                Exercise("Crunches", R.drawable.abs_crunches, "Lie on your back with your knees bent and feet flat on the floor. Place your hands behind your head and lift your upper body towards your knees, engaging your core. Lower your upper body back down in a controlled manner."),
                Exercise("Plank", R.drawable.abs_plank, "Start in a push-up position, then lower yourself onto your forearms. Engage your core and hold this position, keeping your body straight from head to heels."),
                Exercise("Russian Twists", R.drawable.fitnessbuddyicon, "Sit on the floor with your knees bent and feet lifted off the ground. Lean back slightly and twist your torso from side to side, touching the floor on each side."),
                Exercise("Leg Raises", R.drawable.fitnessbuddyicon, "Lie on your back with your legs extended. Lift your legs towards the ceiling, keeping them straight, then lower them back down without touching the ground."),
                Exercise("Mountain Climbers", R.drawable.fitnessbuddyicon, "Start in a high plank position. Alternately bring your knees towards your chest, as if you're running in place, while keeping your core engaged."),
                Exercise("Bicycle Crunches", R.drawable.fitnessbuddyicon, "Lie on your back with your knees bent and hands behind your head. Bring one elbow towards the opposite knee, while extending the other leg. Alternate sides in a cycling motion."),
                Exercise("Reverse Crunches", R.drawable.fitnessbuddyicon, "Lie on your back with your knees bent and feet lifted off the ground. Bring your knees towards your chest, lifting your hips off the ground. Lower your hips back down without touching the ground."),
            )
            "Shoulders" -> listOf(
                Exercise("Shoulder Press", R.drawable.shoulders_shoulderpress, "Sit or stand with dumbbells in your hands, palms facing forward. Lift the dumbbells upwards until your arms are fully extended overhead. Lower them back down to the starting position."),
                Exercise("Lateral Raises", R.drawable.shoulders_lateralraises, "Stand with dumbbells in your hands, palms facing your body. Lift the dumbbells out to the sides until they are parallel to the floor, keeping a slight bend in your elbows. Lower them back down."),
                Exercise("Front Raises", R.drawable.fitnessbuddyicon, "Stand with dumbbells in your hands, palms facing your thighs. Lift the dumbbells in front of you, keeping your arms straight, until they are parallel to the floor. Lower them back down."),
                Exercise("Upright Rows", R.drawable.fitnessbuddyicon, "Stand with dumbbells in your hands, palms facing your body. Lift the dumbbells upwards, leading with your elbows, until they are at chest level. Lower them back down."),
                Exercise("Arnold Press", R.drawable.fitnessbuddyicon, "Sit or stand with dumbbells in your hands, palms facing your body. Rotate your palms outward as you lift the dumbbells overhead, then rotate them back in as you lower them."),
                Exercise("Shrugs", R.drawable.fitnessbuddyicon, "Stand with dumbbells in your hands, palms facing your body. Lift your shoulders towards your ears, squeezing your traps at the top. Lower them back down."),
                Exercise("Reverse Flyes", R.drawable.fitnessbuddyicon, "Sit on a bench with a dumbbell in each hand, palms facing each other. Lean forward at the hips and lift the dumbbells out to the sides, squeezing your shoulder blades together. Lower them back down."),
            )
            else -> emptyList()
        }
    }





/*    private fun getSelectedExercises(): ArrayList<Exercise> {
        val selectedExercises = ArrayList<Exercise>()

        for (i in 0 until exerciseContainer.childCount) {
            val exerciseView = exerciseContainer.getChildAt(i)
            val exercise = exerciseView.tag as Exercise

            if (exercise.isSelected) {
                selectedExercises.add(exercise)
            }
        }

        return selectedExercises
    }*/




    //grabs exercise_item layout
    private fun displayExercises(exercises: List<Exercise>) {
        exerciseContainer.removeAllViews()

        exercises.forEach { exercise ->
            val exerciseView = layoutInflater.inflate(R.layout.exercise_item, null, false).apply {
                tag = exercise
            }


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
        selectedExercises.add(exercise)
        val exerciseCollectorView = layoutInflater.inflate(R.layout.exercise_collector_item, collectorContainer, false)
        val exerciseNameTextView: TextView = exerciseCollectorView.findViewById(R.id.collectedExerciseNameTextView)
        exerciseNameTextView.text = exercise.name

        collectorContainer.addView(exerciseCollectorView)
    }

    private fun removeExerciseFromCollector(exercise: Exercise) {
        selectedExercises.remove(exercise)
        for (i in 0 until collectorContainer.childCount) {
            val collectorItemView = collectorContainer.getChildAt(i)
            val exerciseNameTextView: TextView = collectorItemView.findViewById(R.id.collectedExerciseNameTextView)
            if (exerciseNameTextView.text == exercise.name) {
                collectorContainer.removeViewAt(i)
                break
            }
        }
    }

    private fun clearCollectorContainer() {
        collectorContainer.removeAllViews()
        selectedExercises.clear()
    }
}
