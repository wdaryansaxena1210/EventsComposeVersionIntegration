package com.example.eventscompose.features.events.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.utils.RemoteConfigManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val remoteConfigManager : RemoteConfigManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            val config = remoteConfigManager.getBaseConfig()
            Log.d("EventsViewModel", "Base config fetched: ${config.toString().take(500)}")
        }
    }

    sealed class EventsUiState {
    }

    sealed class EventsNavigationEvent{

    }
}

