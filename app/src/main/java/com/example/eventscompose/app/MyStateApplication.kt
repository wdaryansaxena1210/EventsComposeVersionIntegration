package com.example.eventscompose.app

import android.app.Application
import com.example.eventscompose.core.utils.RemoteConfigManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

//class needed for hilt
@HiltAndroidApp
class MyStateApplication : Application() {

    @Inject
    lateinit var remoteConfigManager: RemoteConfigManager

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        // Do initial setup here
        GlobalScope.launch {
            remoteConfigManager.getBaseConfig()
        }
    }
}