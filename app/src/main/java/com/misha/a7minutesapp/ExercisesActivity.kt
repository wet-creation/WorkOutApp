package com.misha.a7minutesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.misha.a7minutesapp.databinding.ActivityExercisesBinding
import com.misha.a7minutesapp.model.ExercisesModel

class ExercisesActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exercisesList = ArrayList<ExercisesModel>()
    private var currentExercises = 0


    private var binding: ActivityExercisesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        exercisesList = Constants.defaultExercisesList()
        setUpRestView()

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }



    }

    @SuppressLint("SetTextI18n")
    private fun setUpRestView(){
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.imgExercise?.visibility = View.GONE
        binding?.tvNameOfExercise?.visibility = View.VISIBLE
        binding?.tvTimerText?.text = "GET READY FOR"
        binding?.tvNameOfExercise?.text = exercisesList[currentExercises].name
        binding?.progressBar?.max = 10
        setRestProgressBar(10)
    }
    private fun setUpActivityWorkOut(){
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvTimerText?.text = exercisesList[currentExercises].name
        binding?.tvNameOfExercise?.visibility = View.INVISIBLE
        binding?.imgExercise?.visibility = View.VISIBLE
        binding?.imgExercise?.setImageResource(exercisesList[currentExercises].image)
        binding?.progressBar?.max = 30
        setRestProgressBar(30)
    }

    private fun setRestProgressBar(time:Long){
        val tick = 1000L
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(tick * time, tick){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = time.toInt() - restProgress
                binding?.timer?.text = "${time - restProgress}"
            }

            override fun onFinish() {
                if (time == 10L){
                    setUpActivityWorkOut()
                }
                else {
                    if (currentExercises+1 != exercisesList.size){
                        currentExercises++
                        setUpRestView()
                    }
                    else {
                        val intent = Intent(this@ExercisesActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }
}