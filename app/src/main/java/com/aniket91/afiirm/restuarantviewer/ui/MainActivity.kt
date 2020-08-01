package com.aniket91.afiirm.restuarantviewer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.aniket91.afiirm.restuarantviewer.R
import com.aniket91.afiirm.restuarantviewer.api.ApiClient
import com.aniket91.afiirm.restuarantviewer.databinding.ActivityMainBinding
import com.aniket91.afiirm.restuarantviewer.model.ModelMapper
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository
import com.aniket91.afiirm.restuarantviewer.ui.adapter.BusinessCardStackAdapter
import com.aniket91.afiirm.restuarantviewer.ui.fragment.BusinessCardStackTransformer
import com.aniket91.afiirm.restuarantviewer.ui.viewmodels.RestaurantViewModel
import com.aniket91.afiirm.restuarantviewer.utils.PermissionUtils

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


        checkPermission()
    }

    private fun initAdapter() {
        val businessCardStackAdapter = BusinessCardStackAdapter(supportFragmentManager)

        binding.businessViewPager.apply {
            adapter = businessCardStackAdapter
            setPageTransformer(true, BusinessCardStackTransformer())
            offscreenPageLimit = 5
        }

        val apiClient = ApiClient()
        val repo = BusinessRepository(apiClient.getYelpService(), ModelMapper())
        val restaurantViewModel = RestaurantViewModel(repo)

        restaurantViewModel.loadRestaurants().observe(this, Observer { businessList ->
            businessList?.apply {
                businessCardStackAdapter.setBusinesses(this)
                forEach { business ->
                    println("business: ${business.image_url}")
                }
            }
        })
    }

    fun onNext(view: View) {
        binding.businessViewPager.apply {
            setCurrentItem(currentItem + 1, true)
        }
    }

    fun onPrevious(view: View) {
        binding.businessViewPager.apply {
            setCurrentItem(currentItem - 1, true)
        }
    }

    private fun checkPermission() {
        locationPermissionGranted = PermissionUtils.hasLocationPermissionGranted(this)
        if (!locationPermissionGranted) {
            requestLocation()
        } else {
            initAdapter()
        }
    }

    private fun requestLocation() {
        PermissionUtils.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != PermissionUtils.REQUEST_CODE) {
            locationPermissionGranted = false
            return
        }

        if ((grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED)) {
            return
        } else {
            initAdapter()
        }
    }
}
