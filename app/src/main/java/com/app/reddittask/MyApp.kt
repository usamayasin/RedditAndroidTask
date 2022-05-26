package com.app.reddittask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null
        fun getInstance(): MyApp {
            synchronized(MyApp::class.java) {
                if (instance == null)
                    instance =
                        MyApp()
            }
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
