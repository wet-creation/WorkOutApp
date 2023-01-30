package com.misha.a7minutesapp.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.misha.a7minutesapp.Constants
import com.misha.a7minutesapp.R
import com.misha.a7minutesapp.addapter.ExerciseAdapter
import com.misha.a7minutesapp.databinding.ActivityExercisesBinding
import com.misha.a7minutesapp.databinding.DialogBackBinding
import com.misha.a7minutesapp.model.ExercisesModel
import java.util.*
import kotlin.collections.ArrayList

class ExercisesActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exercisesList = ArrayList<ExercisesModel>()
    private var currentExercises = 0
    private lateinit var tts:TextToSpeech
    private lateinit var player:MediaPlayer
    private lateinit var exerciseAdapter: ExerciseAdapter
    private var binding: ActivityExercisesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        exercisesList = Constants.defaultExercisesList()
        setUpExerciseView()
        tts = TextToSpeech(this, this)
        setUpRestView()
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            playDialogBack()
        }

        onInit(0)

    }

    override fun onBackPressed() {
        playDialogBack()
    }

    private fun playDialogBack() {
        val dialog = Dialog(this)
        val dialogBackBinding = DialogBackBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBackBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialogBackBinding.btnYes.setOnClickListener {
            this@ExercisesActivity.finish()
            dialog.dismiss()
        }
        dialogBackBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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
        setProgressBar(10)
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
        setProgressBar(30)
    }

    private fun setUpExerciseView(){
        binding?.rvItemProgress?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseAdapter(exercisesList)
        binding?.rvItemProgress?.adapter = exerciseAdapter
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setProgressBar(time:Long){
        val tick = 1000L
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(tick * time, tick){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                if (time.toInt() - restProgress <= 3){
                    if (time.toInt() - restProgress != 0)
                        speak("${time.toInt() - restProgress}")
                    else player.start()
                }
                binding?.progressBar?.progress = time.toInt() - restProgress
                binding?.timer?.text = "${time - restProgress}"
            }


            override fun onFinish() {
                if (time == 10L){
                    exercisesList[currentExercises].isSelected = true
                    exerciseAdapter.notifyDataSetChanged()
                    setUpActivityWorkOut()
                }
                else {
                    if (currentExercises+1 != exercisesList.size){
                        exercisesList[currentExercises].isSelected = false
                        exercisesList[currentExercises].isCompleted = true
                        exerciseAdapter.notifyDataSetChanged()
                        currentExercises++
                        setUpRestView()
                    }
                    else {
                        val intent = Intent(this@ExercisesActivity, FinishActivity::class.java)
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
        try {
            val soundUri = Uri.parse("android.resource://com.misha.a7minutesapp/res/" + R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(this, soundUri)
            player.isLooping = false

        }catch (e:Exception){
            e.printStackTrace()
        }
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
        if (player.isPlaying)
            player.stop()
        binding = null 
    }


}