package com.example.eventscompose.core.utils

sealed class Resource <T> {
    data class Success<T>(val data : T) : Resource<T>()
    data class Error <T>(val data : T? = null, val message : String) : Resource<T>()
}