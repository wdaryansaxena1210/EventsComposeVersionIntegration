package com.example.eventscompose.features.events.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.model.CategoriesResponseItem
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.domain.use_case.get_categories.GetCategoriesUseCase
import com.example.eventscompose.features.events.domain.use_case.get_events.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
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
        loadData()
    }

    //load categories and events. emit loading, error or success states as needed
    private fun loadData() {
        viewModelScope.launch {
            combine(
                getEventsUseCase.invoke(),
                getCategoriesUseCase.invoke()
            ) { //combine two flows into one flow that emits two objects
                eventsResource: Resource<List<EventsResponseItem>>,
                categoriesResource: Resource<List<CategoriesResponseItem>>
                ->

                // Combine both resources into a single state
                when {
                    eventsResource is Resource.Loading || categoriesResource is Resource.Loading -> {
                       return@combine EventsUiState.Loading
                    }

                    eventsResource is Resource.Success && categoriesResource is Resource.Success -> {
                        return@combine EventsUiState.Success(
                            events = eventsResource.data,
                            categories = categoriesResource.data
                        )
                    }

                    eventsResource is Resource.Error -> {
                        EventsUiState.Error(eventsResource.message ?: "Events loading failed")
                    }

                    categoriesResource is Resource.Error -> {
                        EventsUiState.Error(
                            categoriesResource.message ?: "Categories loading failed"
                        )
                    }

                    else -> EventsUiState.Loading
                }
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }


    fun getEventById(targetId: String): EventsResponseItem? {
        val event = (uiState.value as EventsUiState.Success).events?.find { it.id == targetId }
        return event
    }


    sealed class EventsUiState {
        object Nothing : EventsUiState()
        data class Success(
            val events: List<EventsResponseItem>?,
            val categories: List<CategoriesResponseItem>?
        ) :
            EventsUiState()

        data class Error(val message: String?) : EventsUiState()
        object Loading : EventsUiState()
    }

    sealed class EventsNavigationEvent {
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

    fun sortEventsByDate(events: List<EventsResponseItem>): Map<String, List<EventsResponseItem>> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //"yyyy-MM-dd HH:mm:ss" to "yyyy-MM-dd"
        val sortedEvents =
            events.sortedByDescending { dateFormat.parse(it.eventDate.split(" ")[0]) }.reversed()
        val groupedEvents: Map<String, List<EventsResponseItem>> =
            sortedEvents.groupBy { it.eventDate.split(" ")[0] }


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

        val (durHours, durMinutes) = duration.split(":").map { it.toIntOrNull() ?: 0 }
        val end = start.plusHours(durHours.toLong()).plusMinutes(durMinutes.toLong())
        val formattedEnd = end.format(formatter)

        return Pair(formattedStart, formattedEnd)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToLocalDate(strDate: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDate.parse(strDate, formatter)
    }
}