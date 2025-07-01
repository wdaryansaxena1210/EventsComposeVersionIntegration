package com.example.eventscompose.features.events.presentation.events

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.eventscompose.app.navigation.route.EventRoutes
import com.example.eventscompose.features.events.data.model.CategoriesResponseItem
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.EventsViewModel.EventsUiState
import com.example.eventscompose.features.events.presentation.events.component.EventList
import com.example.eventscompose.features.events.presentation.events.component.TopBarEvents
import kotlin.Boolean

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: EventsViewModel = hiltViewModel()
) {

    //collect the state and display either the Error Screen, Success Screen or Loading screen
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier), contentAlignment = Alignment.Center
    ) {

        when (uiState) {
            //error screen
            is EventsUiState.Error -> {
                Text("error : ${(uiState as EventsUiState.Error).message}")
            }

            EventsUiState.Loading -> {
                CircularProgressIndicator()
            }

            EventsUiState.Nothing -> {
                Text(uiState::class.simpleName.toString())
            }

            is EventsUiState.Success -> {
                EventsContent(
                    events = (uiState as EventsUiState.Success).events
                        ?: emptyList<EventsResponseItem>(),
                    viewModel = viewModel,
                    navController = navController,
                    categories = (uiState as EventsUiState.Success).categories
                        ?: emptyList<CategoriesResponseItem>()
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventsContent(
    modifier: Modifier = Modifier,
    events: List<EventsResponseItem>,
    navController: NavController,
    categories: List<CategoriesResponseItem>,
    viewModel: EventsViewModel = hiltViewModel()
) {

    //first group all events by date
    val groupedEvents: Map<String, List<EventsResponseItem>> = remember(events) {
        viewModel.sortEventsByDate(
            events
        )
    }

    var selectedCategory by remember { mutableStateOf("-1") }
    var showCategoryMenu: Boolean by remember { mutableStateOf(false) }


    //top app bar in scaffold
    Scaffold(
        topBar = {
            TopBarEvents(
                groupedEvents = groupedEvents,
                categories = categories,
                onCategorySelected = { it -> selectedCategory = it },
                showCategoryMenu = showCategoryMenu,
                onToggleCategoryMenu = {showCategoryMenu = !showCategoryMenu},
//            onCalendarToggle = "",
//            onToggleCategoryMenu = ""
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            //actual list of events
            EventList(
                groupedEvents = groupedEvents,
                selectedCategory = selectedCategory,
                vm = viewModel,
                onEventClick = { event -> navController.navigate(EventRoutes.EventDetails(event.id)) }
            )
        }
    }
}








