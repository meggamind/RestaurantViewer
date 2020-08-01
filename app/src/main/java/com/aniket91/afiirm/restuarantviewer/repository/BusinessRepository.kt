package com.aniket91.afiirm.restuarantviewer.repository

import androidx.lifecycle.MutableLiveData
import com.aniket91.afiirm.restuarantviewer.api.YelpService
import com.aniket91.afiirm.restuarantviewer.model.IModelMapper
import com.aniket91.afiirm.restuarantviewer.model.entity.Business

class BusinessRepository(
    private val yelpService: YelpService,
    private val modelMapper: IModelMapper
) {
    private val listOfBusiness: MutableLiveData<List<Business>> = MutableLiveData()

    suspend fun fetchRestaurants(): MutableLiveData<List<Business>> {
        val response = yelpService.discoverBusiness2("new York")

        if (response.isSuccessful) {
            println("isSuccessful")
            listOfBusiness.value = modelMapper.map(response.body())
        } else {
            listOfBusiness.postValue(null)
        }

        return listOfBusiness
    }
}