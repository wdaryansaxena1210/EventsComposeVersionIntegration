package com.example.eventscompose.features.events.domain.use_case.add_event_to_calendar


import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import com.example.eventscompose.features.events.data.model.Event
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddEventToCalendarUseCase @Inject constructor() {

    operator fun invoke(context: Context, event: Event): Result<Unit> {
        return try {
            val contentResolver: ContentResolver = context.contentResolver

            // Parse the event_date (format: "2025-07-04 00:00:00")
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val eventDateTime = dateTimeFormat.parse(event.eventDate)
                ?: return Result.failure(Exception("Invalid date format"))

            val startCalendar = Calendar.getInstance().apply {
                time = eventDateTime
            }

            // Calculate end time based on duration
            val endCalendar = calculateEndTime(startCalendar, event.duration)

            // Create ContentValues for the event
            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, startCalendar.timeInMillis)
                put(CalendarContract.Events.DTEND, endCalendar.timeInMillis)
                put(CalendarContract.Events.TITLE, event.subject)
                put(CalendarContract.Events.DESCRIPTION, event.shortDesc)
                put(CalendarContract.Events.EVENT_LOCATION, event.location)
                put(CalendarContract.Events.CALENDAR_ID, getPrimaryCalendarId(contentResolver))
                put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)

                // Add contact info if available
                if (event.email.isNotBlank()) {
                    put(CalendarContract.Events.ORGANIZER, event.email)
                }
            }

            // Insert the event
            val uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
            if (uri != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to insert event"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun calculateEndTime(startCalendar: Calendar, duration: String): Calendar {
        val endCalendar = startCalendar.clone() as Calendar

        if (duration.isBlank() || duration == "0:00") {
            // Default to 1 hour if no duration specified
            endCalendar.add(Calendar.HOUR_OF_DAY, 1)
        } else {
            // Parse duration format "H:MM" or "HH:MM"
            try {
                val parts = duration.split(":")
                if (parts.size == 2) {
                    val hours = parts[0].toIntOrNull() ?: 0
                    val minutes = parts[1].toIntOrNull() ?: 0

                    endCalendar.add(Calendar.HOUR_OF_DAY, hours)
                    endCalendar.add(Calendar.MINUTE, minutes)
                } else {
                    // Fallback to 1 hour
                    endCalendar.add(Calendar.HOUR_OF_DAY, 1)
                }
            } catch (e: Exception) {
                // Fallback to 1 hour if duration parsing fails
                endCalendar.add(Calendar.HOUR_OF_DAY, 1)
            }
        }

        return endCalendar
    }

    private fun getPrimaryCalendarId(contentResolver: ContentResolver): Long {
        return try {
            val projection = arrayOf(CalendarContract.Calendars._ID)
            val cursor = contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                "${CalendarContract.Calendars.IS_PRIMARY} = 1",
                null,
                null
            )

            cursor?.use {
                if (it.moveToFirst()) {
                    it.getLong(0)
                } else {
                    1L // Fallback to calendar ID 1
                }
            } ?: 1L
        } catch (e: Exception) {
            1L // Fallback to calendar ID 1
        }
    }
}