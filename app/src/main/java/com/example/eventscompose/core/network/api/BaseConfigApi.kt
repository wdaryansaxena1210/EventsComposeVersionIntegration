package com.example.eventscompose.core.network.api

import com.example.eventscompose.core.model.ConfigItem
import retrofit2.http.GET

interface BaseConfigApi {

    @GET
    suspend fun getBaseConfig(): List<ConfigItem>
}