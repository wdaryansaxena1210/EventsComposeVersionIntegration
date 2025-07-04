package com.example.eventscompose.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventscompose.app.navigation.route.EventRoutes
import com.example.eventscompose.features.events.presentation.EventsViewModel
import com.example.eventscompose.features.events.presentation.event_details.EventDetailsScreen
import com.example.eventscompose.features.events.presentation.events.EventScreen

//FIXME : not sure if we should include the MainActivityViewModel into the params of NavHost cuz
// what does nav host has to do with MainActivityViewModel. the reason i added the MainActivityViewModel
// here is so that it is generated with a proper lifecycle (and scoping the lifecycle to NavHost seems logical)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = EventRoutes.Events
    ) {
        composable(EventRoutes.Events)
        {
            EventScreen(navController = navController)
        }
        composable(EventRoutes.EventDetails) {navBackStackEntry ->
//            val eventDetails = navBackStackEntry.toRoute<EventRoutes.EventDetails>()
            val eventId: String? = navBackStackEntry.arguments?.getString("eventId")
            if(eventId ==null)
                LaunchedEffect(Unit) { navController.popBackStack() }

            //fetch the VM from parent (events-list-screen) to avoid extra api calls that happen in
            //the init block if you create a new EventsVM
            val eventsBackStackEntry = remember(navController.currentBackStackEntry) {
                navController.getBackStackEntry(EventRoutes.Events)
            }

            val eventsViewModel: EventsViewModel = hiltViewModel<EventsViewModel>(eventsBackStackEntry)

            EventDetailsScreen(
                eventId = eventId!!,
                onBackClick = { navController.popBackStack() },
                viewModel = eventsViewModel
            )
        }
    }
}