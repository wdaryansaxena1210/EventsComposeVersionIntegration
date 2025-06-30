package com.example.eventscompose.app.navigation.route

import com.example.eventscompose.features.events.data.model.EventsResponseItem
import kotlinx.serialization.Serializable


sealed class EventRoutes{

    @Serializable
    object Events : EventRoutes()

    @Serializable
    data class EventDetails(val eventId : String)
}

