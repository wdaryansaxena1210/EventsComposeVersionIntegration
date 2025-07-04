package com.example.eventscompose.features.events.presentation.events.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventscompose.features.events.data.model.Event

@Composable
internal fun EventItem(
    event: Event,
    findStartAndEndTime: (String, String) -> Pair<String, String>,
    onEventClick: (Event) -> Unit
) {
    val (start, end) = findStartAndEndTime(event.eventDate.split(" ")[1], event.duration)
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onEventClick(event) }
    ) {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy((-5).dp)
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