package com.aniket91.afiirm.restuarantviewer.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aniket91.afiirm.restuarantviewer.model.entity.Business

@Dao
interface BusinessDao {
    @Insert
    suspend fun insertFavorite(businesses: Business)

    @Query("SELECT * FROM BUSINESS_TABLE")
    suspend fun getAllFavorites(): List<Business>

    @Delete
    suspend fun delete(businesses: Business)
}