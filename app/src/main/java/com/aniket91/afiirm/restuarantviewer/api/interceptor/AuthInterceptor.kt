package com.aniket91.afiirm.restuarantviewer.api.interceptor

import com.aniket91.afiirm.restuarantviewer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.YelpApiKey}")

        return chain.proceed(requestBuilder.build())
    }
}