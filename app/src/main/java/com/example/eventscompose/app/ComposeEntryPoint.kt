package com.example.eventscompose.app

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.eventscompose.app.navigation.NavHost
import com.example.eventscompose.core.ui.theme.EventsComposeTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComposeEntryPoint(
    onMenuClick: () -> Unit = { Log.d("ComposeEntryPoint", "onMenuClick clicked") },
    onBackClick: () -> Unit = { Log.d("ComposeEntryPoint", "onBackClick clicked") },
) {
    EventsComposeTheme {
        val navController = rememberNavController()
        NavHost(
            navController
        )
    }
}