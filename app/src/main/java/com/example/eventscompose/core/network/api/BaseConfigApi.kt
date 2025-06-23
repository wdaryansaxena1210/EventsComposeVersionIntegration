package com.example.eventscompose.core.network.api

import com.example.eventscompose.core.model.ConfigItem
import retrofit2.http.GET
import retrofit2.http.Url

interface BaseConfigApi {
    @GET
    suspend fun getBaseConfig(@Url url : String): List<ConfigItem>
}