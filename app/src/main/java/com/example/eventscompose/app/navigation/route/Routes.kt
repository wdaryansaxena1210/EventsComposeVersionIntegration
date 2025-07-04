package com.example.eventscompose.app.navigation.route



object EventRoutes {
    const val Events = "events"
    const val EventDetails = "event_details/{eventId}"

    fun eventDetailsRoute(eventId: String) = "event_details/$eventId"
}