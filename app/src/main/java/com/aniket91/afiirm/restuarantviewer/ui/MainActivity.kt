package com.aniket91.afiirm.restuarantviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initAdapter()
    }

    private fun initAdapter() {
        val businessCardStackAdapter = BusinessCardStackAdapter(supportFragmentManager)

        binding.businessViewPager.apply {
            adapter = businessCardStackAdapter
            setPageTransformer(false, BusinessCardStackTransformer())
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
}
