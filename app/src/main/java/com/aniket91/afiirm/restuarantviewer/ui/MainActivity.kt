package com.aniket91.afiirm.restuarantviewer.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.aniket91.afiirm.restuarantviewer.R
import com.aniket91.afiirm.restuarantviewer.api.ApiClient
import com.aniket91.afiirm.restuarantviewer.databinding.ActivityMainBinding
import com.aniket91.afiirm.restuarantviewer.model.ModelMapper
import com.aniket91.afiirm.restuarantviewer.model.entity.CoOrdinate
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository
import com.aniket91.afiirm.restuarantviewer.ui.adapter.BusinessCardStackAdapter
import com.aniket91.afiirm.restuarantviewer.ui.fragment.BusinessCardStackTransformer
import com.aniket91.afiirm.restuarantviewer.ui.viewmodels.RestaurantViewModel
import com.aniket91.afiirm.restuarantviewer.utils.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    var locationPermissionGranted = false
    lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermission()
    }

    private fun fetchCurrentLocation(onLocationFetch: (longitude: Double, latitude: Double) -> Unit) {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if (location != null) {
                onLocationFetch(location.longitude, location.latitude)
            } else {
                //ToDo: Show user error
            }
        }
    }

    private fun initAdapter(longitude: Double, latitude: Double) {
        val businessCardStackAdapter = BusinessCardStackAdapter(supportFragmentManager)

        binding.businessViewPager.apply {
            adapter = businessCardStackAdapter
            setPageTransformer(true, BusinessCardStackTransformer())
            offscreenPageLimit = 5
        }

        val apiClient = ApiClient()
        val repo = BusinessRepository(apiClient.getYelpService(), ModelMapper())
        val restaurantViewModel = RestaurantViewModel(repo)

        restaurantViewModel.loadRestaurants(CoOrdinate(latitude = latitude, longitude = longitude))
            .observe(this, Observer { businessList ->
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
            fetchCurrentLocation(::initAdapter)
        }
    }

    private fun requestLocation() {
        PermissionUtils.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
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
            fetchCurrentLocation(::initAdapter)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

//    override fun onLocationChanged(p0: Location) {
//        println("onLocationChanged location: ${p0.latitude}, ${p0.longitude}")
//    }
}
