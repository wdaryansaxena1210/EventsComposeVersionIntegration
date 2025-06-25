package com.example.eventscompose.features.events.data.repository

import com.example.eventscompose.features.events.data.model.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface EventsApi {

    @GET
    suspend fun getEvents(
        @Url url : String,
        @Query("weeks") weeks : String = "-1",
        @Query("key") page : String = "8aa084537a2184f6179c",
        @QueryMap params : Map<String, String> = mapOf()
    ) : EventsResponse

    @GET
    suspend fun get(@Url url : String)
}