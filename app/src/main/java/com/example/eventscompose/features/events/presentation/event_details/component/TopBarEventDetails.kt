package com.example.eventscompose.features.events.presentation.event_details.component

import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEventDetails(modifier: Modifier = Modifier, onBackClick: () -> Unit, eventId: String) {
    val context = LocalContext.current
    TopAppBar(
        title = {},

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        },

        actions = {
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW,
                    "https://www.event.iastate.edu/event/$eventId".toUri())
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.Info, contentDescription = "info")
            }
        },
    )
}