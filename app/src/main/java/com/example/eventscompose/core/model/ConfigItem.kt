package com.example.eventscompose.core.model

import kotlinx.serialization.SerialName

data class ConfigItem(
    val name: String,
    @SerialName("min_version") val minVersion: String,
    val type: String,
    val value: Any
)
