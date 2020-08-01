package com.aniket91.afiirm.restuarantviewer.api

import com.aniket91.afiirm.restuarantviewer.model.response.BusinessResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    suspend fun discoverBusiness(
        @Query("term") term: String,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Response<BusinessResponse>
}