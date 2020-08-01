package com.aniket91.afiirm.restuarantviewer.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLEncoder

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer $TOKEN")

        return chain.proceed(requestBuilder.build())
    }

    companion object {
//        private val TOKEN = URLEncoder.encode("itoMaM6DJBtqD54BHSZQY9WdWR5xI_CnpZdxa3SG5i7N0M37VK1HklDDF4ifYh8SI-P2kI_mRj5KRSF4_FhTUAkEw322L8L8RY6bF1UB8jFx3TOR0-wW6Tk0KftNXXYx", "utf-8")
        val TOKEN = "9MFLmjzpxr2CEZ2S6jdq9ajNKRrBrYfA9JuL1usIlccaAhXMdjM9SR8Ydq_XJHnCeiu27pxL77lq7hKnoPIazMHI1ubSGt2cz6F6vgxO3HpQWwEOzk-IzsX0r8skX3Yx"
    }
}