package com.aniket91.afiirm.restuarantviewer.ui.viewmodels

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.aniket91.afiirm.restuarantviewer.model.entity.CoOrdinate
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository
import kotlinx.coroutines.launch

class RestaurantViewModel(private val businessRepository: BusinessRepository) : ViewModel(),
    Observable {

    var business = MutableLiveData<List<Business>>()
    var hideKeyboard = MutableLiveData(false)

    @Bindable
    val inputBusinessSearchName = MutableLiveData("")

    private var offset = 0
    private var coOrdinate: CoOrdinate = CoOrdinate(0.0, 0.0)
    var currentBusinessIndex = MutableLiveData(0)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    fun loadRestaurants(coOrdinate: CoOrdinate): MutableLiveData<List<Business>> {
        this.coOrdinate = coOrdinate
        resetOffset()

        viewModelScope.launch {
            business.postValue(businessRepository.fetchRestaurants(coOrdinate, offset).value)
        }

        return business
    }

    fun loadAdditionalRestaurants(index: Int) {
        val shouldFetch = business.value?.let { list ->
            list.size - index < BusinessRepository.BUSINESS_FETCH_LIMIT / 2
        } ?: false

        if (!shouldFetch) return

        Log.i(TAG, "fetching additional business to display: $offset")

        offset += BusinessRepository.BUSINESS_FETCH_LIMIT

        viewModelScope.launch {
            val previousList: List<Business> = business.value ?: listOf()
            val newList: List<Business> = businessRepository.fetchRestaurants(
                coOrdinate,
                offset
            ).value ?: listOf()

            val appendedList: List<Business> = previousList + newList

            Log.i(TAG, "appendedList: ${appendedList.size}")
            business.postValue(appendedList)
        }
    }

    fun loadBusiness() {
        Log.i(TAG, "business to discover: ${inputBusinessSearchName.value.toString()}")
        hideKeyboard.postValue(false)
        resetOffset()
        viewModelScope.launch {
            business.postValue(
                businessRepository.fetchBusiness(
                    coOrdinate,
                    offset,
                    inputBusinessSearchName.value.toString()
                ).value
            )
            inputBusinessSearchName.postValue(null)
        }
    }

    fun toggleFavorite(favoriteBusiness: Business) {
        viewModelScope.launch {
            if (!favoriteBusiness.isFavorite) {
                businessRepository.addFavorite(favoriteBusiness)
            } else {
                businessRepository.removeFavorite(favoriteBusiness)
            }
        }
    }

    private fun resetOffset() {
        offset = 0
    }

    companion object {
        private val TAG = RestaurantViewModel::class.java.simpleName
    }
}