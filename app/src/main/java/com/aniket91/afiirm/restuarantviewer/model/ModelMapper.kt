package com.aniket91.afiirm.restuarantviewer.model

import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.model.response.BusinessResponse

interface IModelMapper {
    fun map(businessResponse: BusinessResponse?): List<Business>
    fun map(
        businessResponse: BusinessResponse?,
        favoriteBusiness: List<Business>
    ): List<Business>
}

class ModelMapper() : IModelMapper {
    override fun map(businessResponse: BusinessResponse?): List<Business> {
        val businessData = businessResponse?.businesses

        return businessData?.map { business ->
            Business(
                id = business.id,
                image_url = business.image_url,
                name = business.name,
                rating = business.rating
            )
        }?.toList() ?: listOf()
    }

    override fun map(
        businessResponse: BusinessResponse?,
        favoriteBusiness: List<Business>
    ): List<Business> {
        val businessData = businessResponse?.businesses

        val list = businessData?.map { business ->
            Business(
                id = business.id,
                image_url = business.image_url,
                name = business.name,
                rating = business.rating
            )
        }?.toList() ?: listOf()

        favoriteBusiness.forEach { favorite ->
            list.firstOrNull { business -> business.id == favorite.id }?.apply {
                this.isFavorite = true
            }
        }

        return list
    }
}