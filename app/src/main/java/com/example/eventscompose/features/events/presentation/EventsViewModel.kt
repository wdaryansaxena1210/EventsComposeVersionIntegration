package com.example.eventscompose.features.events.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.utils.RemoteConfigManager
import com.example.eventscompose.features.events.data.repository.EventsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val remoteConfigManager : RemoteConfigManager,
    private val api : EventsApi
) : ViewModel() {

    init {
        viewModelScope.launch {
            val config = remoteConfigManager.getBaseConfig()
            Log.d("EventsViewModel", "Base config fetched: ${config.toString().take(100)}")

            val apiDef = remoteConfigManager.getApiDefinition(listOf("events", "events.api"))
            Log.d("EventsViewModel", "asasas Api definition fetched: ${apiDef.toString().take(100)}")

            val response = api.getEvents("https://www.event.iastate.edu/api/events/", "-1")
            Log.d("EventsViewModel", "Events fetched: ${response.toString().take(100)}")
        }
    }

    sealed class EventsUiState {
    }

    sealed class EventsNavigationEvent{

    }
}

