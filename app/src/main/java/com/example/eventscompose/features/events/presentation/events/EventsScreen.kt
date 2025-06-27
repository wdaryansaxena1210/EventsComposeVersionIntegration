package com.example.eventscompose.features.events.presentation.events

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.EventsViewModel.EventsUiState

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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventList(events: List<EventsResponseItem>, vm: EventsViewModel) {
    //group events by date
    val groupedEvents: Map<String, List<EventsResponseItem>> = remember(events) {
        vm.sortEventsByDate(
            events
        )
    }

    Log.d("EventList", "events are this: $events")
    Log.d("EventList", "END EVENTS")

    //render sticky-header + list of events
    LazyColumn {
        groupedEvents.values.forEach { eventList ->
            if (vm.isNotInFuture(eventList[0].eventDate)) {
                stickyHeader {
                    EventsDateHeader(
                        eventList[0].eventDate.split(" ")[0],
                        vm::dateToDay
                    )
                }

                items(eventList) {
                    EventItem(it, vm::findStartAndEndTime)
                }
            }
        }
    }
}


@Composable
private fun EventsDateHeader(
    date: String,
    dateToDay: (String) -> String,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp),
    ) {
        Text(
            text = dateToDay(date),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun EventItem(
    event: EventsResponseItem,
    findStartAndEndTime: (String, String) -> Pair<String, String>
) {
    val (start, end) = findStartAndEndTime(event.eventDate.split(" ")[1], event.duration)
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(-5.dp)
            ) {
                Text(
                    text = "start :",
                )
                Text(
                    text = start,
                    letterSpacing = 0.sp
//                    fontWeight = FontWeight.Bold
                )
                if (event.duration != "0:00") {
                    Text(
                        text = "end :"
                    )
                    Text(
                        text = end,
                        letterSpacing = 0.sp
//                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = event.subject,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
                Text(
                    text = event.shortDesc,
                    maxLines = 2,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



