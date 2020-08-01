package com.aniket91.afiirm.restuarantviewer.api

import com.aniket91.afiirm.restuarantviewer.api.interceptor.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiService: YelpService

    fun getYelpService(): YelpService {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_YELP_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient())
                .build()

            apiService = retrofit.create(YelpService::class.java)

        }

        return apiService
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }


    companion object {
        private const val BASE_YELP_URL = "https://api.yelp.com/v3/"
    }
}