package com.example.eventscompose.features.events.presentation.event_details

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.event_details.component.TopBarEventDetails

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    eventId: String,
    onBackClick: () -> Unit,
    viewModel: EventsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is EventsViewModel.EventsUiState.Success -> {

            // fetch the event with the same eventId and display it
            val event =
                (uiState as EventsViewModel.EventsUiState.Success).events?.find { it.id == eventId }
            if (event != null) {
                EventDetailsContent(
                    event = event,
                    onBackClick = onBackClick,
                    viewModel = viewModel
                )
            } else {
                //event list is present but the list does not contain event with id=eventId
                LaunchedEffect(Unit) { onBackClick() }
            }
        }

        //EventsUiState is at loading state. should never occur but just in case
        is EventsViewModel.EventsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        //should never occur but just in case
        is EventsViewModel.EventsUiState.Error -> {
            LaunchedEffect(Unit) {
                onBackClick()
            }
        }

        is EventsViewModel.EventsUiState.Nothing -> return
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun EventDetailsContent(
    event: EventsResponseItem,
    onBackClick: () -> Unit,
    viewModel: EventsViewModel
) {
    Scaffold(
        topBar = { TopBarEventDetails(onBackClick = onBackClick) }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            //check for invalid-format entries?
            val (eventDate, eventStart) = event.eventDate.split(" ")
            val (startTime, endTime) = viewModel.findStartAndEndTime(
                eventStart,
                event.duration
            )
            val day = viewModel.dateToDay(eventDate)
            //displayed in ui : startTime, endTime and day

            //when title
            Text(
                "When",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            //when info
            Text(
                text = "$day :  $startTime - $endTime",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            //Where title
            Text(
                "Where",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            //Where info
            Text(
                text = event.location,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            //cost title
            Text(
                "Cost",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            //Cost info
            Text(
                text = event.cost,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            //Event Description title
            Text(
                "Event Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            //Event Description info
            Text(
                text = event.shortDesc,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            //add to calendar button
            Button(
                onClick = {/*TODO*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Add to calendar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun PreviewEventDetailsScreen(modifier: Modifier = Modifier) {
//    EventDetailsScreen(
//        event = EventsResponseItem(
//        category = "Academic",
//        channelId = "12345",
//        cost = "Free",
//        datePosted = "2024-12-15 13:00",
//        duration = "2 hours",
//        email = "contact@event.com",
//        eventDate = "2024-12-20",
//        id = "event_001",
//        location = "Memorial Union",
//        phone = "(515) 294-4123",
//        shortDesc = "Join us for an exciting workshop on mobile app development using Jetpack Compose.",
//        subject = "Android Development Workshop",
//        views = "156"
//        ),
//        onBackClick = { },
//        )
//}

