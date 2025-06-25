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
    private val remoteConfigManager: RemoteConfigManager,
    private val api: EventsApi
) : ViewModel() {

    init {
        viewModelScope.launch {

            val apiDef = remoteConfigManager.getApiValue(listOf("events", "events.api"))

            val response = api.getEvents(apiDef!!.url, key=apiDef.parameters["key"].toString())
        }
    }

    sealed class EventsUiState {
    }

    sealed class EventsNavigationEvent {

    }
}

