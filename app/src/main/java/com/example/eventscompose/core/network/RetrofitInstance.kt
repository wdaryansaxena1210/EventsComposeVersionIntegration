package com.example.eventscompose.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun create(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummy.base.url/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
