package com.example.eventscompose.features.events.presentation.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventscompose.features.events.data.model.EventsResponse
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.EventsViewModel.EventsUiState

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier), contentAlignment = Alignment.Center
    ) {

        when (uiState) {
            is EventsUiState.Error -> {
                Text(uiState::class.simpleName.toString())
            }

            EventsUiState.Loading -> {
                Text(uiState::class.simpleName.toString())
            }

            EventsUiState.Nothing -> {
                Text(uiState::class.simpleName.toString())
            }

            is EventsViewModel.EventsUiState.Success -> {
                EventsSuccessScreen(
                    events = (uiState as EventsUiState.Success).events
                        ?: EventsResponse()
                )
            }
        }
    }
}

@Composable
fun EventsSuccessScreen(modifier: Modifier = Modifier, events: EventsResponse) {
    Scaffold(
        topBar = { TopBarEvents() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            //title of screen
            Text(
                "Events",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            //actual list of events
            EventList(events = events)
        }
    }
}

@Composable
fun TopBarEvents() {
}

@Composable
fun EventList(events: EventsResponse) {
    val groupedEvents by remember (events){ events.groupBy { it.datePosted } }
    LazyColumn {
        items(groupedEvents) {
            Text(it.datePosted)
        }
    }
}


