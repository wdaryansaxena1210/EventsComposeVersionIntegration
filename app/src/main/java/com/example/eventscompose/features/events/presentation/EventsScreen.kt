package com.example.eventscompose.features.events.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .then(modifier), contentAlignment = Alignment.Center) {
        Text(
            text = "Event Screen"
        )
    }
}