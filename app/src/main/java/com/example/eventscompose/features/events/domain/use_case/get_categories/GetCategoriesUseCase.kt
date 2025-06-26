package com.example.eventscompose.features.events.domain.use_case.get_categories

import android.util.Log
import com.example.eventscompose.core.utils.RemoteConfigManager
import com.example.eventscompose.core.utils.Resource
import com.example.eventscompose.features.events.data.EventsApi
import com.example.eventscompose.features.events.data.model.CategoriesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCategoriesUseCase @Inject constructor(
    private val api: EventsApi,
    private val remoteConfigManager: RemoteConfigManager
) {
    operator fun invoke(): Flow<Resource<CategoriesResponse>> {
        return flow {
            emit(Resource.Loading())

            val categoriesApiVal =
                remoteConfigManager.getApiValue(listOf("events", "categories.api"))

            try {
                val response = api.getCategories(
                    url = categoriesApiVal!!.url,
                    key = categoriesApiVal.parameters["key"].toString()
                )
                Log.d("GetCategoriesUseCase", "Response: $response")
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }

        }
    }
}