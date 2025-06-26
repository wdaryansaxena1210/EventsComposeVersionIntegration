package com.example.eventscompose.features.events.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.model.CategoriesResponse
import com.example.eventscompose.features.events.data.model.EventsResponse
import com.example.eventscompose.features.events.domain.use_case.get_categories.GetCategoriesUseCase
import com.example.eventscompose.features.events.domain.use_case.get_events.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Nothing)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getEvents()
        }
        viewModelScope.launch {
            getCategories()
        }
    }

    private suspend fun getEvents() {
        getEventsUseCase.invoke().collect {
            when (it) {
                is Resource.Success -> {
                    if (_uiState.value is EventsUiState.Success) {
                        _uiState.value =
                            (_uiState.value as EventsUiState.Success).copy(events = it.data)
                    } else {
                        _uiState.value = EventsUiState.Success(it.data, null)
                    }
                }

                is Resource.Error -> {
                    _uiState.value = EventsUiState.Error(it.message ?: "A unknown error occurred")
                }

                is Resource.Loading -> {
                    _uiState.value = EventsUiState.Loading
                }
            }
        }

    }

    private suspend fun getCategories() {
        getCategoriesUseCase.invoke().collect {
            when (it) {
                is Resource.Success -> {
                    if (_uiState.value is EventsUiState.Success) {
                        _uiState.value =
                            (_uiState.value as EventsUiState.Success).copy(categories = it.data)
                    } else {
                        _uiState.value = EventsUiState.Success(null, it.data)
                    }
                }

                is Resource.Error -> {
                    _uiState.value = EventsUiState.Error(it.message ?: "An unknown error occurred")
                }

                is Resource.Loading -> {
                    _uiState.value = EventsUiState.Loading
                }
            }
        }
    }

    sealed class EventsUiState {
        object Nothing : EventsUiState()
        data class Success(val events: EventsResponse?, val categories: CategoriesResponse?) :
            EventsUiState()

        data class Error(val message: String?) : EventsUiState()
        object Loading : EventsUiState()
    }

    sealed class EventsNavigationEvent {
    }
}

