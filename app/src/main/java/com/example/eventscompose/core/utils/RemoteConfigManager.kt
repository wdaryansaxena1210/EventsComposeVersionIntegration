package com.example.eventscompose.core.utils

import android.util.Log
import com.example.eventscompose.core.model.ApiValue
import com.example.eventscompose.core.model.ConfigItem
import com.example.eventscompose.core.network.api.BaseConfigApi
import com.google.gson.Gson
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


//TODO : if baseConfig is not cached, then do an api call
//TODO : add an 'isLoading' to prevent multiple api calls
//TODO : Thread safety and mutex?
//TODO : overload OR refactor with a default value :  getBaseConfig(forcedRefresh Boolean = false)
//TODO : make an interface with getBaseConfig, loadBaseConfig
// methods and make this class implement that interface. decrease coupling with this remote config class, inject interface not class

@Singleton
class RemoteConfigManager @Inject constructor(
    private val api: BaseConfigApi,
) {
    //instance variables
    private val gson = Gson()
    private var isLoading = true
    val BASE_URL = "https://www.mystate.iastate.edu/remote/static/config/base.json"
    var baseConfigCache: List<ConfigItem>? = null

    //return cached baseConfig or else do api call
    suspend fun getBaseConfig(): List<ConfigItem>? {
        if (baseConfigCache == null) {
            isLoading = true
            loadBaseConfig()
            isLoading = false
        }
        return baseConfigCache
    }

    //api call to get base config
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

    /**
     * Get the API's value from the base config given a path
     */
    suspend fun getApiValue(path: List<String>): ApiValue? {
        //FIXME if baseConfigCache==null then change hardcoded delay to something else
        if (baseConfigCache == null)
            delay(3000)

        Log.d(
            "RemoteConfigManager",
            "Base config before finding API's Value: ${this.baseConfigCache.toString().take(100)}"
        )
        return findApiValue(baseConfigCache!!, path)
    }

    /**
     * Recursively find the API value in the base config
     */
    suspend private fun findApiValue(
        currConfig: List<ConfigItem>,
        path: List<String>
    ): ApiValue? {
        if(path.isEmpty())
            return  null

        val name = path[0]
        //find ConfigItem with same name as path[0]
        for (currItem in currConfig) {
            if (currItem.name == name) {

                //return the api's value if we found the api
                if (currItem.type== "API") {
                    val apiJson = gson.toJson(currItem.value)
                    val apiValue = gson.fromJson(apiJson, ApiValue::class.java)
                    val finalApiValue = fixHTTPtoHTTPS(apiValue)
                    return finalApiValue
                }

                //if api not found, recursively search until you reach the last element of the path
                if (currItem.type == "Config") {
                    val item = currItem.value as Map<String, Any>
                    val response = api.get(item["url"].toString()).string()
                    val newConfig = gson.fromJson(response, Array<ConfigItem>::class.java)
                    return findApiValue(newConfig.toList(), path.drop(1))
                }
            }
        }
        //could not find the api
        //TODO write error handling code if wrong path is given as param
        return null
    }

    //helper function
    private fun fixHTTPtoHTTPS(definition: ApiValue): ApiValue {
        val updatedUrl = if (definition.url.startsWith("http://")) {
            definition.url.replaceFirst("http://", "https://")
        } else {
            definition.url
        }
        return definition.copy(url = updatedUrl)
    }
}

