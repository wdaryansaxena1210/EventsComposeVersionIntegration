package com.example.eventscompose.features.events.presentation.events.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventList(
    groupedEvents: Map<String, List<EventsResponseItem>>,
    vm: EventsViewModel,
    onEventClick: (EventsResponseItem) -> Unit
) {

    //render sticky-header + list of events
    LazyColumn {
        groupedEvents.values.forEach { eventList ->
            stickyHeader {
                EventsDateHeader(
                    eventList[0].eventDate.split(" ")[0],
                    vm::dateToDay
                )
            }

            items(eventList) {
                EventItem(it, vm::findStartAndEndTime, onEventClick = onEventClick)
            }
        }
    }
}