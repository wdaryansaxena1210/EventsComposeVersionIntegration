package com.example.eventscompose.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.network.api.BaseConfigApi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(val baseConfigApi: BaseConfigApi): ViewModel() {
    init {
        viewModelScope.launch {
            try {
                val remoteConfig = baseConfigApi.getBaseConfig()

            }catch (e : Exception){
                e.printStackTrace()
            }
        }
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