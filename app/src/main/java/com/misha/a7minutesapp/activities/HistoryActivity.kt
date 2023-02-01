package com.misha.a7minutesapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misha.a7minutesapp.WorkOutApp
import com.misha.a7minutesapp.addapter.HistoryAdapter
import com.misha.a7minutesapp.database.history.HistoryDao
import com.misha.a7minutesapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var bind:ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(bind?.root)
        setSupportActionBar(bind?.toolbarHistory)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        bind?.toolbarHistory?.setNavigationOnClickListener {
           onBackPressed()
        }
        val historyDao = (application as WorkOutApp).db.historyDao()
        getAllDates(historyDao)

    }
    private fun getAllDates(dao: HistoryDao){
        lifecycleScope.launch {
            dao.fetchAllDates().collect{
                if (it.isNotEmpty()) {
                    val list = ArrayList(it)
                    bind?.tvNoDataAvailable?.visibility = View.GONE
                    bind?.tvHistory?.visibility = View.VISIBLE
                    bind?.rvHistory?.visibility = View.VISIBLE
                    bind?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity,
                        RecyclerView.VERTICAL,true)
                    bind?.rvHistory?.adapter = HistoryAdapter(list)
                }
                else{
                    bind?.tvNoDataAvailable?.visibility = View.VISIBLE
                    bind?.tvHistory?.visibility = View.GONE
                    bind?.rvHistory?.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }
}