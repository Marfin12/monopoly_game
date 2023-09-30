package com.example.experiments2

import android.app.Application
import android.content.res.Resources

class MyApplication : Application() {
    companion object {
        lateinit var appResources: Resources
    }

    override fun onCreate() {
        super.onCreate()
        appResources = resources
    }
}