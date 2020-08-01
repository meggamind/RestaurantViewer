package com.aniket91.afiirm.restuarantviewer.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "business_table")
data class Business(
    @PrimaryKey
    val id: String,
    val image_url: String,
    val name: String,
    val rating: Double,
    var isFavorite: Boolean = false
)