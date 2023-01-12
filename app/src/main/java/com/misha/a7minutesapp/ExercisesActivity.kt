package com.misha.a7minutesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.misha.a7minutesapp.databinding.ActivityExercisesBinding
import com.misha.a7minutesapp.model.ExercisesModel
import java.util.*
import kotlin.collections.ArrayList

class ExercisesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exercisesList = ArrayList<ExercisesModel>()
    private var currentExercises = 0
    private lateinit var tts:TextToSpeech


    private var binding: ActivityExercisesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        exercisesList = Constants.defaultExercisesList()
        tts = TextToSpeech(this, this)

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
        speak("GET READY FOR ${exercisesList[currentExercises].name}")
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
        speak(exercisesList[currentExercises].name)
        binding?.progressBar?.max = 30
        setRestProgressBar(30)
    }

    private fun setRestProgressBar(time:Long){
        val tick = 1000L
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(tick * time, tick){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                if (time.toInt() - restProgress <= 3){
                    if (time.toInt() - restProgress != 0)
                        speak("${time.toInt() - restProgress}")
                }
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
    private fun speak(string: String){
        tts.speak(string, TextToSpeech.QUEUE_ADD,null,"")
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)
            speak("GET READY FOR ${exercisesList[currentExercises].name}")
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "The language is not supported!")
        }
        else Log.e("TTS", "The initialization failed")
    }
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }
        if (tts.isSpeaking){
            tts.stop()
            tts.shutdown()
        }
        binding = null
    }


}