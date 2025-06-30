package com.example.eventscompose.features.events.presentation.event_details.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEventDetails(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
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

        actions = { Icon(Icons.Default.Info, contentDescription = "info") },


        )
}