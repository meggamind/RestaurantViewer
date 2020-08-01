package com.aniket91.afiirm.restuarantviewer.repository

import androidx.lifecycle.MutableLiveData
import com.aniket91.afiirm.restuarantviewer.api.service.YelpBusinessService
import com.aniket91.afiirm.restuarantviewer.model.IModelMapper
import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.model.entity.CoOrdinate

class BusinessRepository(
    private val yelpService: YelpBusinessService,
    private val modelMapper: IModelMapper
) {
    private val listOfBusiness: MutableLiveData<List<Business>> = MutableLiveData()

    suspend fun fetchRestaurants(coOrdinate: CoOrdinate, offset: Int): MutableLiveData<List<Business>> {
        val response = yelpService.discoverBusiness(
            "restaurants",
            BUSINESS_FETCH_LIMIT,
            offset,
            coOrdinate.longitude,
            coOrdinate.latitude
        )

        if (response.isSuccessful) {
            println("isSuccessful")
            listOfBusiness.value = modelMapper.map(response.body())
        } else {
            listOfBusiness.postValue(null)
        }

        return listOfBusiness
    }

    companion object {
        const val BUSINESS_FETCH_LIMIT = 20
    }
}