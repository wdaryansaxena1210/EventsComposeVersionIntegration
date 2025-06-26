package com.example.eventscompose.features.events.domain.use_case.get_events

import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.EventsApi
import com.example.eventscompose.features.events.data.model.EventsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventsApi: EventsApi
) {

}