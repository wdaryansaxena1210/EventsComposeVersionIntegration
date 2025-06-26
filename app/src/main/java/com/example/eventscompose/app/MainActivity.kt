package com.example.eventscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventscompose.core.ui.theme.EventsComposeTheme
import com.example.eventscompose.features.events.presentation.events.EventScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventsComposeTheme {
                val vm : MainActivityViewModel = hiltViewModel()

                LaunchedEffect(Unit) {
                    vm.test()
                }

                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        EventScreen()
                    }
                }
            }
        }
    }
}

