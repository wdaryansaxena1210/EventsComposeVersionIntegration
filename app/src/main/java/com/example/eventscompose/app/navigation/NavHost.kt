package com.example.eventscompose.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.eventscompose.app.navigation.route.EventRoutes
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import com.example.eventscompose.features.events.presentation.event_details.EventDetailsScreen
import com.example.eventscompose.features.events.presentation.events.EventScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = EventRoutes.Events
    ) {
        composable<EventRoutes.Events> {
            EventScreen(navController = navController)
        }
        composable<EventRoutes.EventDetails> {
            val eventDetails = it.toRoute<EventRoutes.EventDetails>()
//            EventDetailsScreen(
//                eventId = eventDetails.eventId,
//                onBackClick = {navController.popBackStack()}
//            )
        }
    }
}