package com.example.eventscompose.features.events.presentation.event_details

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.eventscompose.features.events.data.model.Event
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.event_details.component.TopBarEventDetails
import java.text.SimpleDateFormat
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetailsScreen(
    eventId: String,
    onBackClick: () -> Unit,
    viewModel: EventsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is EventsViewModel.EventsUiState.Success -> {

            // fetch the event with the same eventId and display it
            val event = viewModel.getEventById(eventId)
//                (uiState as EventsViewModel.EventsUiState.Success).events?.find { it.id == eventId }
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

        is EventsViewModel.EventsUiState.Idle -> return
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun EventDetailsContent(
    event: Event,
    onBackClick: () -> Unit,
    viewModel: EventsViewModel
) {
    val context = LocalContext.current
    val calendarState by viewModel.calendarState.collectAsStateWithLifecycle()

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.addEventToCalendar(context, event)
        } else {
            // Handle permission denied - could show a snackbar or dialog
        }
    }

    // Handle calendar state changes
    LaunchedEffect(calendarState) {
        when (calendarState) {
            is EventsViewModel.CalendarState.Success -> {
                // Auto-clear success state after 3 seconds
                kotlinx.coroutines.delay(3000)
                viewModel.clearCalendarState()
            }

            is EventsViewModel.CalendarState.Error -> {
                // Auto-clear error state after 5 seconds
                kotlinx.coroutines.delay(5000)
                viewModel.clearCalendarState()
            }

            else -> { /* No action needed */
            }
        }
    }

    Scaffold(
        topBar = {
            TopBarEventDetails(
                onBackClick = onBackClick,
                eventId = event.id
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            //CALENDAR
            // Parse date for display (format: "2025-07-04 00:00:00")
            val eventDateTime = remember(event.eventDate) {
                try {
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    formatter.parse(event.eventDate)
                } catch (e: Exception) {
                    null
                }
            }

            val displayDate = remember(eventDateTime) {
                eventDateTime?.let {
                    val dayFormatter = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
                    dayFormatter.format(it)
                } ?: "Date not available"
            }

            val displayTime = remember(eventDateTime, event.duration) {
                viewModel.displayTime(eventDateTime, event)
            }

            // When title
            Text(
                "When",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            // When info
            Text(
                text = "$displayDate\n$displayTime",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Where title
            Text(
                "Where",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            // Where info
            Text(
                text = event.location.ifBlank { "Location not specified" },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Cost title
            Text(
                "Cost",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            // Cost info
            Text(
                text = event.cost.ifBlank { "Free" },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Event Description title
            Text(
                "Event Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(8.dp)
            )

            // Event Description info
            Text(
                text = event.shortDesc.ifBlank { "No description available" },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Contact info (if available)
            if (event.email.isNotBlank() || event.phone.isNotBlank()) {
                Text(
                    "Contact",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(8.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp),
                ) {
                    if (event.email.isNotBlank()) {
                        Text(
                            text = "Email: ${event.email}",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    if (event.phone.isNotBlank()) {
                        Text(
                            text = "Phone: ${event.phone}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Add to calendar button
            Button(
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    // Check if permission is already granted
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_CALENDAR
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        viewModel.addEventToCalendar(context, event)
                    } else {
                        permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
                    }
                },
                enabled = calendarState !is EventsViewModel.CalendarState.Loading,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (calendarState is EventsViewModel.CalendarState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        "Add to calendar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            // Status messages
            when (calendarState) {
                is EventsViewModel.CalendarState.Success -> {
                    Text(
                        "Event added to calendar successfully!",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }

                is EventsViewModel.CalendarState.Error -> {
                    Text(
                        "Failed to add event: ${(calendarState as EventsViewModel.CalendarState.Error).message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }

                else -> { /* No message */
                }
            }
        }
    }
}



