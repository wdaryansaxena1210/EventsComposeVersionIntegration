package com.example.eventscompose.features.events.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesResponseItem(
    val id: String,
    @SerializedName("long_title") val longTitle: String,
    @SerializedName("short_title") val shortTitle: String
)