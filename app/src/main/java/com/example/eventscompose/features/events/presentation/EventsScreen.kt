package com.example.eventscompose.features.events.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .then(modifier), contentAlignment = Alignment.Center) {

        when (uiState) {
            is EventsViewModel.EventsUiState.Error -> {
                Text(uiState::class.simpleName.toString())
            }
            EventsViewModel.EventsUiState.Loading -> {
                Text(uiState::class.simpleName.toString())
            }
            EventsViewModel.EventsUiState.Nothing -> {
                Text(uiState::class.simpleName.toString())
            }
            is EventsViewModel.EventsUiState.Success -> {
                Box(){
//                    Text((uiState as EventsViewModel.EventsUiState.Success).events?.get(0).toString())
                }
            }
        }
    }
}