package com.example.eventscompose.features.events.presentation.events.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel

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
                EventItem(it, vm::findStartAndEndTime)
            }
        }
    }
}