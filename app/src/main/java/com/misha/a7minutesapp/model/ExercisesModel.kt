package com.misha.a7minutesapp.model

data class ExercisesModel (
    val id:Int,
    val name :String,
    val image:Int,
    var isCompleted:Boolean = false,
    var isSelected:Boolean = false
    )