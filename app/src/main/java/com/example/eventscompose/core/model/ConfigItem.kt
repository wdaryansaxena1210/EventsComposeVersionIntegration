package com.example.eventscompose.core.model

import com.google.gson.annotations.SerializedName

data class ConfigItem(
    val name: String,
    @SerializedName("min_version") val minVersion: String,
    val type: String,
    val value: Any
)
