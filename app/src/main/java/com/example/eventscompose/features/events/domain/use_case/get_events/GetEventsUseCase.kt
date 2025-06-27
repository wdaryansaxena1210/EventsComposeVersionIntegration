package com.example.eventscompose.features.events.domain.use_case.get_events

import android.util.Log
import com.example.eventscompose.core.utils.RemoteConfigManager
import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.EventsApi
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetEventsUseCase @Inject constructor(
    private val api : EventsApi,
    private val remoteConfigManager: RemoteConfigManager
){
    operator fun invoke() : Flow<Resource<List<EventsResponseItem>>>{
        return flow {
            emit(Resource.Loading())

            val eventsApiVal = remoteConfigManager.getApiValue(listOf("events", "events.api"))

            try {
                val response = api.getEvents(url = eventsApiVal!!.url, key = eventsApiVal.parameters["key"].toString())
                Log.d("GetEventsUseCase", "Response: $response")
                emit(Resource.Success(response))
            }
            catch (e : Exception){
                emit(Resource.Error(message = e.message.toString()))
            }

        }
    }
}