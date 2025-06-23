package com.example.eventscompose.core.network
//
//import com.example.eventscompose.core.network.api.BaseConfigApi
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule{
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(client: OkHttpClient) : Retrofit{
//        return RetrofitInstance.create(client)
//    }
//
//    @Provides
//    @Singleton
//    fun provideHttpClient(): OkHttpClient {
//      return HttpClient.create()
//    }
//
//    @Provides
//    @Singleton
//    fun provideBaseConfigApi(retrofit : Retrofit) : BaseConfigApi{
//        return retrofit.create(BaseConfigApi::class.java)
//    }
//}