package com.example.eventscompose.app

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventscompose.core.model.ConfigItem
import com.example.eventscompose.core.network.api.BaseConfigApi
import com.example.eventscompose.core.utils.RemoteConfigManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val api : BaseConfigApi,
    @ApplicationContext private val context : Context,
    private val remoteConfigManager : RemoteConfigManager
) : ViewModel() {

    //load base config when the MainActivity is created
    init {
        viewModelScope.launch {
            val baseConfig  = remoteConfigManager.getBaseConfig()
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