package com.misha.a7minutesapp

import com.misha.a7minutesapp.model.ExercisesModel

object Constants {

    fun defaultExercisesList():ArrayList<ExercisesModel>{
        val exercisesList = ArrayList<ExercisesModel>()
        val abdominalCrunch = ExercisesModel(
            1,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch
        )
        exercisesList.add(abdominalCrunch)

        val highKneesRunningInPlace = ExercisesModel(
            2,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place
        )
        exercisesList.add(highKneesRunningInPlace)

        val jumpingJacks = ExercisesModel(
            3,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks
        )
        exercisesList.add(jumpingJacks)

        val lunge = ExercisesModel(
            4,
            "Lunge",
            R.drawable.ic_lunge
        )
        exercisesList.add(lunge)
        val plank = ExercisesModel(
            5,
            "Plank",
            R.drawable.ic_plank
        )
        exercisesList.add(plank)

        val pushUps = ExercisesModel(
            6,
            "Push up",
            R.drawable.ic_push_up
        )
        exercisesList.add(pushUps)

        val pushUpAndRotation = ExercisesModel(
            7,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation
        )
        exercisesList.add(pushUpAndRotation)

        val sidePlank = ExercisesModel(
            8,
            "Side plank",
            R.drawable.ic_side_plank
        )
        exercisesList.add(sidePlank)

        val squat = ExercisesModel(
            9,
            "Squat",
            R.drawable.ic_squat
        )
        exercisesList.add(squat)

        val stepUpOntoChair = ExercisesModel(
            10,
            "Step Up Onto Chair",
            R.drawable.ic_step_up_onto_chair
        )
        exercisesList.add(stepUpOntoChair)

        val tricepsDipOnChair = ExercisesModel(
            11,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair
        )
        exercisesList.add(tricepsDipOnChair)

        val wallSit = ExercisesModel(
            12,
            "Wall sit",
            R.drawable.ic_wall_sit
        )
        exercisesList.add(wallSit)



        return exercisesList
    }
}