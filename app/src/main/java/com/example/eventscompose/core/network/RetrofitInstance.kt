package com.example.eventscompose.core.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit

object RetrofitInstance{
    fun create() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory()
            .build()
    }
}