package com.example.eventscompose.features.events.presentation.events.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.eventscompose.features.events.data.model.Event
import com.example.eventscompose.features.events.presentation.EventsViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventList(
    groupedEvents: Map<String, List<Event>>,
    vm: EventsViewModel,
    onEventClick: (Event) -> Unit,
    selectedCategory: String,
    selectedDate: LocalDate
) {

    //render sticky-header + list of events
    LazyColumn {
        groupedEvents.values.forEach { eventList ->

            //date header
            stickyHeader {
                //DON'T display date if there is no event with event.category==selectedCategory
                if (
                    (selectedCategory == "-1" || eventList.any { event ->
                        event.category.split(",").contains(selectedCategory)
                    })
                    &&
                    vm.stringToLocalDate(eventList[0].eventDate) >= selectedDate
                ) {

                    EventsDateHeader(
                        eventList[0].eventDate.split(" ")[0],
                        vm::dateToDay
                    )
                }
            }

            //event list
            items(eventList) { event ->
                //filter event by category
                if ( (selectedCategory == "-1" || event.category.split(",")
                        .contains(selectedCategory))
                    &&
                    vm.stringToLocalDate(event.eventDate) >= selectedDate
                )
                    EventItem(event, vm::findStartAndEndTime, onEventClick = onEventClick)
            }
        }
    }
}