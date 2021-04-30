package com.ozgursakizli.starshipdelivery.application

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

private val TAG = StarshipDeliveryApplication::class.java.simpleName

@HiltAndroidApp
class StarshipDeliveryApplication : Application() {

    companion object {
        @Volatile
        private lateinit var INSTANCE: StarshipDeliveryApplication

        fun getInstance(): StarshipDeliveryApplication = INSTANCE
    }

    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    init {
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        val unCaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread: Thread?, ex: Throwable? ->
            Log.e(TAG, "StarshipDeliveryApplication", ex)
            defaultExceptionHandler?.let { thread?.let { ex?.let { defaultExceptionHandler!!.uncaughtException(thread, ex) } } }
        }
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler)
    }

    override fun onCreate() {
        Log.d(TAG, "------ Beginning of Log File ------")
        super.onCreate()
        INSTANCE = this
    }

    override fun onTerminate() {
        Log.d(TAG, "onTerminate")
        super.onTerminate()
    }

}