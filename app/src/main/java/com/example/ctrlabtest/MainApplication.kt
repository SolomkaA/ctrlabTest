package com.example.ctrlabtest

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "#############" // enter your OneSignal App ID


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }

}