package com.example.eventscompose.features.events.di

import com.example.eventscompose.features.events.data.EventsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventsModule{
    @Provides
    @Singleton
    fun provideEventsApi(retrofit : Retrofit) : EventsApi {
        return retrofit.create(EventsApi::class.java)
    }
}