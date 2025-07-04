package com.example.eventscompose.features.events.presentation.events.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    onDateSelected: (LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            onDateSelected(millisToLocalDate(it))
        }
    }
    Card (
        elevation = CardDefaults.cardElevation(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        DatePicker(
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun millisToLocalDate(millis: Long): LocalDate =
    Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()