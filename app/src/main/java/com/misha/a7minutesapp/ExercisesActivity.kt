package com.misha.a7minutesapp

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.misha.a7minutesapp.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var binding: ActivityExercisesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        setUpRestView()

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun setUpRestView(){
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvTimerText?.text = "GET READY FOR"
        binding?.progressBar?.max = 10
        setRestProgressBar(10)
    }
    private fun setUpActivityWorkOut(){
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvTimerText?.text = "WORK HARD GET LOT"
        binding?.progressBar?.max = 30
        setRestProgressBar(30)
    }

    private fun setRestProgressBar(time:Long){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(1000 * time, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = time.toInt() - restProgress
                binding?.timer?.text = "${time - restProgress}"
            }

            override fun onFinish() {
                if (time == 10L){
                    setUpActivityWorkOut()
                }
                else Toast.makeText(this@ExercisesActivity, "GG 30 seconds have gone", Toast.LENGTH_SHORT).show()
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