package com.example.eventscompose.core.utils

import android.util.Log
import com.example.eventscompose.core.model.ApiDefinition
import com.example.eventscompose.core.model.ConfigItem
import com.example.eventscompose.core.network.api.BaseConfigApi
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigManager @Inject constructor(
    private val api: BaseConfigApi,
) {
    private val gson = Gson()
    private var isLoading = true
    val BASE_URL = "https://www.mystate.iastate.edu/remote/static/config/base.json"
    var baseConfigCache: List<ConfigItem>? = null

    suspend fun getBaseConfig(): List<ConfigItem>? {
        if (baseConfigCache == null) {
            isLoading = true
            loadBaseConfig()
            isLoading = false
        }

        //TODO : if baseConfig is not cached then do an api call
        //TODO : add an 'isLoading' to prevent multiple api calls
        //TODO : Thread safety and mutex?
        //TODO : overload OR refactor with a default value :  getBaseConfig(forcedRefresh Boolean = false)
        //TODO : make an interface with getBaseConfig, loadBaseConfig  methods and make this class implement that interface. decrease coupling with this remote config class, inject interface not class

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
        } catch (e: Exception) {
            Log.e("RemoteConfigManager", "Error fetching base config: ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun getApiDefinition(path: List<String>): ApiDefinition? {
        if (baseConfigCache == null) {
            //TODO
            return null
        }

        return findApiDefinition(baseConfigCache!!, path)
    }

    suspend private fun findApiDefinition(
        currConfig: List<ConfigItem>,
        path: List<String>
    ): ApiDefinition? {

        if(path.isEmpty()){return  null}

        val name = path[0]
        Log.d("RemoteConfigManager", "Searching for $name in ${currConfig.toString().take(100)}")
        for (currItem in currConfig) {
            if (currItem.name == name) {
                Log.d("RemoteConfigManager", "Found $name in ${currConfig.toString().take(100)}")
                if (currItem.type== "API") {
                    Log.d(
                        "RemoteConfigManager",
                        "Api definition found: ${currItem}"
                    )
                    //FIXME : casting fails, not sure why
                    val apiJson = gson.toJson(currItem.value)
                    val apiDefinition = gson.fromJson(apiJson, ApiDefinition::class.java)
                    return apiDefinition
                }
                if (currItem.type == "Config") {
                    val item = currItem.value as Map<String, Any>
                    val response = api.get(item["url"].toString()).string()
                    val newConfig = gson.fromJson(response, Array<ConfigItem>::class.java)
                    findApiDefinition(newConfig.toList(), path.drop(1))
                }
            }
        }
        //could not find the api
        return null
    }

}

