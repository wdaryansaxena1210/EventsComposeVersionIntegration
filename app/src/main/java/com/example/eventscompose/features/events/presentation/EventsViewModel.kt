package com.example.eventscompose.features.events.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.model.CategoriesResponse
import com.example.eventscompose.features.events.data.model.EventsResponse
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.domain.use_case.get_categories.GetCategoriesUseCase
import com.example.eventscompose.features.events.domain.use_case.get_events.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventsUiState>(EventsUiState.Nothing)
    val uiState = _uiState.asStateFlow()

    init {
        //TODO : convert to async await instead of separate coroutines
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


    /*
    Functions below are related to parsing Time and Date string to objects
     */
    fun dateToDay(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val eventDate = dateFormat.parse(date.split(" ")[0])
        val dayFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        return eventDate?.let { dayFormat.format(it).toString() } ?: " "
    }

    fun sortEventsByDate(events: EventsResponse): Map<String, List<EventsResponseItem>> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //"yyyy-MM-dd HH:mm:ss" to "yyyy-MM-dd"
        val sortedEvents =
            events.sortedByDescending { dateFormat.parse(it.eventDate.split(" ")[0]) }.reversed()
        val groupedEvents: Map<String, List<EventsResponseItem>> =
            sortedEvents.groupBy { it.eventDate.split(" ")[0] }

        Log.d("sortEventsByDate", "groupedEvents: $groupedEvents")

        return groupedEvents
    }



    //needs android Oreo (android 8) or above
    @RequiresApi(Build.VERSION_CODES.O)
    fun findStartAndEndTime(startTime: String, duration: String): Pair<String, String> {
        val start = LocalTime.parse(startTime) // format: "HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val formattedStart = start.format(formatter)

        if (duration.isEmpty() || duration == "0:00") {
            return Pair(formattedStart, "")
        }

        val (durHours, durMinutes) = duration.split(":").map { it.toInt() }
        val end = start.plusHours(durHours.toLong()).plusMinutes(durMinutes.toLong())
        val formattedEnd = end.format(formatter)

        return Pair(formattedStart, formattedEnd)
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

