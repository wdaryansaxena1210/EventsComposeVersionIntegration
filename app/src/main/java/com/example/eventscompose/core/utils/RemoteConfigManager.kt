package com.example.eventscompose.core.utils

import android.util.Log
import com.example.eventscompose.core.model.ConfigItem
import com.example.eventscompose.core.network.api.BaseConfigApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigManager @Inject constructor(
    private val api : BaseConfigApi
) {
    private var isLoading = true
    val BASE_URL = "https://www.mystate.iastate.edu/remote/static/config/base.json"
    var baseConfigCache : List<ConfigItem>? = null

    suspend fun getBaseConfig(): List<ConfigItem>? {
        if (baseConfigCache == null) {
            isLoading = true
            loadBaseConfig()
            isLoading = false
        }

        //TODO : if baseConfig is not cached then do an api call
        //TODO : add an 'isLoading' to prevent multiple api calls
        //TODO : Thread safety and mutex?

        return baseConfigCache
    }




    private suspend fun loadBaseConfig() {
        try {
        val baseConfig = api.getBaseConfig(this.BASE_URL)

        //Cache BaseConfig
        this.baseConfigCache = baseConfig
        Log.d(
            "RemoteConfigManager",
            "Base config fetched: ${this.baseConfigCache.toString().take(100)}"
        )
        }catch (e : Exception){
            Log.e("RemoteConfigManager", "Error fetching base config: ${e.message}")
            e.printStackTrace()
        }
    }

}

