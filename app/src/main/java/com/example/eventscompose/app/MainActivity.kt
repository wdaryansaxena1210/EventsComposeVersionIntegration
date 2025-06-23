package com.example.eventscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.eventscompose.core.ui.theme.EventsComposeTheme
import com.example.eventscompose.features.events.presentation.EventScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventsComposeTheme {
                Scaffold { innerPadding ->
                    EventScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

