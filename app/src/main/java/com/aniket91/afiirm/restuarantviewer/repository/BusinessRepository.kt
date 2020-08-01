package com.aniket91.afiirm.restuarantviewer.repository

import androidx.lifecycle.MutableLiveData
import com.aniket91.afiirm.restuarantviewer.api.YelpService
import com.aniket91.afiirm.restuarantviewer.model.IModelMapper
import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.model.entity.CoOrdinate

class BusinessRepository(
    private val yelpService: YelpService,
    private val modelMapper: IModelMapper
) {
    private val listOfBusiness: MutableLiveData<List<Business>> = MutableLiveData()

    suspend fun fetchRestaurants(coOrdinate: CoOrdinate): MutableLiveData<List<Business>> {
        val response = yelpService.discoverBusiness("restaurants", coOrdinate.longitude, coOrdinate.latitude)

        if (response.isSuccessful) {
            println("isSuccessful")
            listOfBusiness.value = modelMapper.map(response.body())
        } else {
            listOfBusiness.postValue(null)
        }

        return listOfBusiness
    }
}