package com.sigpro.lider

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class SigproLiderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: SigproLiderApp

        fun getInstance(): SigproLiderApp = instance

        fun getAppContext(): Context = instance.applicationContext
    }
}
