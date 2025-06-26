package com.example.eventscompose.features.events.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.model.ApiValue
import com.example.eventscompose.core.utils.RemoteConfigManager
import com.example.eventscompose.features.events.data.EventsApi
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

            val apiDef: ApiValue? = remoteConfigManager.getApiValue(listOf("events", "events.api"))

            val response = api.getEvents(apiDef!!.url, key = apiDef.parameters["key"].toString())
            Log.d("EventsViewModel", "Response: $response")

            val categoriesApi = remoteConfigManager.getApiValue(listOf("events", "categories.api"))
            Log.d("EventsViewModel", "Categories API: $categoriesApi")

            val categoriesResponse = api.getCategories(
                categoriesApi!!.url,
                key = categoriesApi.parameters["key"].toString()
            )
            Log.d("EventsViewModel", "Categories Response: ${categoriesResponse.toString().take(50)}")
        }
    }

    sealed class EventsUiState {
    }

    sealed class EventsNavigationEvent {
    }
}

