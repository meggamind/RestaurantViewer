package com.aniket91.afiirm.restuarantviewer.ui.viewmodels

import androidx.databinding.Observable
import androidx.lifecycle.*
import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository
import kotlinx.coroutines.launch

class RestaurantViewModel(private val businessRepository: BusinessRepository) : ViewModel(),
    Observable {

    var business = MutableLiveData<List<Business>>()

    fun loadRestaurants(): MutableLiveData<List<Business>> {
        viewModelScope.launch {
            business.postValue(businessRepository.fetchRestaurants().value)
        }
        return business
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}