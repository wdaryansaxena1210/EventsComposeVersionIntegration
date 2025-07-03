package com.example.eventscompose.app.navigation.route

import kotlinx.serialization.Serializable


sealed class EventRoutes{

    @Serializable
    object Events : EventRoutes()

    @Serializable
    data class EventDetails(val eventId : String)
}

