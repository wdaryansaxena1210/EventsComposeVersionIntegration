package com.example.eventscompose.features.events.presentation.events.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun EventsDateHeader(
    date: String,
    dateToDay: (String) -> String,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {
        Text(
            text = dateToDay(date),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }
}