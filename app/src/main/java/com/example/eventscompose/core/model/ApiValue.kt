package com.example.eventscompose.core.model

data class ApiValue(
    val headers: Map<String, String>,
    val method: String,
    val parameterEncoding: String,
    val parameters: Map<String, String>,
    val url: String
)