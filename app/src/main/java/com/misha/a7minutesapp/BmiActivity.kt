package com.misha.a7minutesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.misha.a7minutesapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNIT_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleUnit = METRIC_UNIT_VIEW
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
            if (currentVisibleUnit == METRIC_UNIT_VIEW) {
                if (binding?.inputHeight?.text?.isNotEmpty() == true && binding?.inputWeightMetric?.text?.isNotEmpty() == true)
                    displayView()
                else
                    Toast.makeText(this, "Input weight or height\nto calculate", Toast.LENGTH_SHORT)
                        .show()
            }
            else{
                if (binding?.inputFeet?.text?.isNotEmpty() == true && binding?.inputInch?.text?.isNotEmpty() == true
                    && binding?.inputWeightUs?.text?.isNotEmpty() == true)
                    displayView()
                else
                    Toast.makeText(this, "Input weight or height\nto calculate", Toast.LENGTH_SHORT)
                        .show()
            }
        }
        binding?.rgUnits?.setOnCheckedChangeListener {_, id->
            if(id == R.id.rbMetricUnits)
                setMetricUnits()
            else setUsUnits()
        }

    }

    private fun setUsUnits() {
        currentVisibleUnit = US_UNIT_VIEW
        binding?.llShow?.visibility = View.INVISIBLE
        binding?.llShowUs?.visibility = View.VISIBLE
        binding?.llShowMetric?.visibility = View.GONE
    }

    private fun setMetricUnits() {
        currentVisibleUnit = METRIC_UNIT_VIEW
        binding?.llShow?.visibility = View.INVISIBLE
        binding?.llShowMetric?.visibility = View.VISIBLE
        binding?.llShowUs?.visibility = View.GONE
    }

    private fun calculate() : Double {

        return if (currentVisibleUnit == METRIC_UNIT_VIEW){
            val weight = binding?.inputWeightMetric?.text.toString().toDouble()
            val height = binding?.inputHeight?.text.toString().toDouble().div(100)
            weight / (height*height)

        } else {
            val weight = binding?.inputWeightUs?.text.toString().toDouble()
            val inch = binding?.inputInch?.text.toString().toDouble()
            val feet = binding?.inputFeet?.text.toString().toDouble()*12
            val height = feet + inch
            (weight*703)/(height*height)
        }

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