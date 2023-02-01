package com.misha.a7minutesapp

import android.app.Application
import com.misha.a7minutesapp.database.history.HistoryDatabase

class WorkOutApp:Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}