package com.misha.a7minutesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.misha.a7minutesapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var binding:ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmi)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarBmi?.setNavigationOnClickListener {
          onBackPressed()
        }
        binding?.btnCalculate?.setOnClickListener {
            if (binding?.inputHeight?.text?.isNotEmpty() == true && binding?.inputWeight?.text?.isNotEmpty() == true)
                displayView()
            else
                Toast.makeText(this, "Input weight or height\nto calculate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculate() : Double {
        val weight = binding?.inputWeight?.text?.toString()?.toDouble()
        val height = binding?.inputHeight?.text?.toString()?.toDouble()?.div(100)
        return weight!! / (height!!*height)
    }

    @SuppressLint("SetTextI18n")
    private fun displayView(){
        val res = calculate()
        binding?.tvBMIres?.text = String.format("%.2f",res)
        binding?.llShow?.visibility = View.VISIBLE
        when {
            (res >= 18.5 && res < 25) -> {
                binding?.tvSmallMsg?.text = "Normal"
                binding?.tvLongMsg?.text = "You are in a good shape"
            }
            (res >= 25 && res < 30) -> {
                binding?.tvSmallMsg?.text = "Overweight"
                binding?.tvLongMsg?.text = "Looks like someone eats a lot of sweeties"
            }
            (res >= 30 && res < 35) -> {
                binding?.tvSmallMsg?.text = "Obese"
                binding?.tvLongMsg?.text = "Well you are a filthy pig"
            }
            (res >= 35) -> {
                binding?.tvSmallMsg?.text = "Extremely Obese"
                binding?.tvLongMsg?.text = "No one can help ypu only you"
            }
            else -> {
                binding?.tvSmallMsg?.text = "Underweight"
                binding?.tvLongMsg?.text = "My man you need some bulk"
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}