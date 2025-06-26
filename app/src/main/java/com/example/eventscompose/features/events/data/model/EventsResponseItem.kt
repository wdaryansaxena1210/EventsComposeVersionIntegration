package com.example.eventscompose.features.events.data.model

import com.google.gson.annotations.SerializedName


data class EventsResponseItem (
    val category: String,
    @SerializedName("channel_id") val channelId: String,
    val cost: String,
    @SerializedName("date_posted") val datePosted: String,
    val duration: String,
    val email: String,
    @SerializedName("event_date") val eventDate: String,
    val id: String,
    val location: String,
    val phone: String,
    @SerializedName("short_desc") val shortDesc: String,
    val subject: String,
    val views: String
)