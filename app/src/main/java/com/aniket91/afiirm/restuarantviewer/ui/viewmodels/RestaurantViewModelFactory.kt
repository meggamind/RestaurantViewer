package com.aniket91.afiirm.restuarantviewer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository


@Suppress("UNCHECKED_CAST")
class RestaurantViewModelFactory(
    private val repository: BusinessRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }
}