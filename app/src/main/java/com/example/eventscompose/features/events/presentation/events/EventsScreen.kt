package com.example.eventscompose.features.events.presentation.events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.EventsViewModel.EventsUiState
import com.example.eventscompose.features.events.presentation.events.component.EventList

@RequiresApi(Build.VERSION_CODES.O)
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
                        ?: emptyList<EventsResponseItem>(),
                    viewModel = viewModel
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsSuccessScreen(
    modifier: Modifier = Modifier,
    events: List<EventsResponseItem>,
    viewModel: EventsViewModel
) {
    Scaffold(
        topBar = { TopBarEvents() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            //title of screen
            Text(
                text = "Events",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            //actual list of events
            EventList(events = events, vm = viewModel)
        }
    }
}

@Composable
fun TopBarEvents() {
}










