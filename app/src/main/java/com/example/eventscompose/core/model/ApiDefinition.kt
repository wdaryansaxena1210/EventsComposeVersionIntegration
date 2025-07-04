package com.example.eventscompose.core.model

import com.google.gson.annotations.SerializedName


data class ApiDefinition(
    val name: String,
    @SerializedName("min_version") val minVersion: String,
    val type: String,
    val value: ApiValue
)