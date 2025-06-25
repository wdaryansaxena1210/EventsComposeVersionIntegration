package com.example.eventscompose.features.events.data.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class EventsResponseItem(
    val category: String,
    @SerialName("channel_id") val channelId: String,
    val cost: String,
    @SerialName("date_posted") val datePosted: String,
    val duration: String,
    val email: String,
    @SerialName("event_date") val eventDate: String,
    val id: String,
    val location: String,
    val phone: String,
    @SerialName("short_desc") val shortDesc: String,
    val subject: String,
    val views: String
)