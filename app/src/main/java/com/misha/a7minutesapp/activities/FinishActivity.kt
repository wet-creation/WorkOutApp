package com.misha.a7minutesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.misha.a7minutesapp.WorkOutApp
import com.misha.a7minutesapp.database.history.HistoryDao
import com.misha.a7minutesapp.database.history.HistoryEntity
import com.misha.a7minutesapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnBack?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val historyDao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(dao: HistoryDao){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = dateFormat.format(dateTime)

        lifecycleScope.launch{
            dao.insert(HistoryEntity(date))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}