package com.example.gestiuneacererilor

import android.app.Application
import com.google.firebase.FirebaseApp

class BaseApplication : Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }

}