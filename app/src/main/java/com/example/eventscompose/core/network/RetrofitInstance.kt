package com.example.eventscompose.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    //TODO try using Moshi instead of Gson as convertor factory
    fun create(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummy.base.url/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client)
            .build()
    }
}
