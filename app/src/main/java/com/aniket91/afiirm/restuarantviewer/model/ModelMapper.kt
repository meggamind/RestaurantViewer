package com.aniket91.afiirm.restuarantviewer.model

import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.model.response.BusinessResponse

interface IModelMapper {
    fun map(businessResponse: BusinessResponse?): List<Business>
}

class ModelMapper() : IModelMapper {
    override fun map(businessResponse: BusinessResponse?): List<Business> {
        val businessData = businessResponse?.businesses

        return businessData?.map { business ->
            Business(
                image_url = business.image_url,
                name = business.name,
                rating = business.rating
            )
        }?.toList() ?: listOf()
    }
}