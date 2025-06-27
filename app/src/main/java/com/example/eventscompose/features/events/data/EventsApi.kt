package com.example.eventscompose.features.events.data

import com.example.eventscompose.features.events.data.model.CategoriesResponseItem
import com.example.eventscompose.features.events.data.model.EventsResponseItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url


//TODO : safeApiCall wrapper

interface EventsApi {

    @GET
    suspend fun get(@Url url: String)

    @GET
    suspend fun getEvents(
        @Url url: String,
        @Query("weeks") weeks: String = "-1",
        @Query("key") key: String,
        @QueryMap params: Map<String, String> = mapOf()
    ): List<EventsResponseItem>

    @GET
    suspend fun getCategories(
        @Url url: String,
        @Query("key") key: String,
        @Query("categories") categories : String = "-1",
        @QueryMap params: Map<String, String> = mapOf()
    ): List<CategoriesResponseItem>

}