package com.example.eventscompose.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.network.api.BaseConfigApi
import com.example.eventscompose.core.utils.RemoteConfigManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    init {
        viewModelScope.launch {
            try {
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun test(){
        println("testing MainActivityViewModel")
    }
}


//refernce for later

/*
viewModelScope.launch {
    val result: Resource<List<ConfigItem>> = try {
        val data = api.getBaseConfig()
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(null, e.message ?: "Unknown error")
    }

    when (result) {
        is Resource.Success -> {
            // Success: result.data
        }
        is Resource.Error -> {
            // Failure: result.message
        }
    }
}
 */