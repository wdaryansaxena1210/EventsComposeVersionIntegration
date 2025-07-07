package com.example.eventscompose.app.navigation.route

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.eventscompose.app.navigation.NavHost
import com.example.eventscompose.core.ui.theme.EventsComposeTheme
import com.example.eventscompose.core.utils.RemoteConfigManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


object EventRoutes {
    const val Events = "events"
    const val EventDetails = "event_details/{eventId}"

    fun eventDetailsRoute(eventId: String) = "event_details/$eventId"
}



// MAIN-ACTIVITY:
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContent {
//            EventsComposeTheme {
//                Scaffold { innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
//                        val navController = rememberNavController()
//                        NavHost(navController = navController)
//                    }
//                }
//            }
//        }
//    }
//}

//APPLICATION:
//@Inject
//lateinit var remoteConfigManager: RemoteConfigManager
//
//@OptIn(DelicateCoroutinesApi::class)
//override fun onCreate() {
//    super.onCreate()
//
//    // Do initial setup here
//    GlobalScope.launch {
//        remoteConfigManager.getBaseConfig()
//    }
//}