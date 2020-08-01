package com.aniket91.afiirm.restuarantviewer.model.response

import com.aniket91.afiirm.restuarantviewer.model.entity.Business

data class BusinessResponse(
    val businesses: List<Business>,
    val total: Int
)