package com.aniket91.afiirm.restuarantviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
import com.aniket91.afiirm.restuarantviewer.R
import com.aniket91.afiirm.restuarantviewer.api.ApiClient
import com.aniket91.afiirm.restuarantviewer.api.YelpService
import com.aniket91.afiirm.restuarantviewer.databinding.ActivityMainBinding
import com.aniket91.afiirm.restuarantviewer.model.ModelMapper
import com.aniket91.afiirm.restuarantviewer.repository.BusinessRepository
import com.aniket91.afiirm.restuarantviewer.ui.adapter.BusinessCardStackAdapter
import com.aniket91.afiirm.restuarantviewer.ui.fragment.BusinessCardStackTransFormer
import com.aniket91.afiirm.restuarantviewer.ui.viewmodels.RestaurantViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val apiClient = ApiClient()
        val repo = BusinessRepository(apiClient.getYelpService(), ModelMapper())
        val restaurantViewModel = RestaurantViewModel(repo)

        restaurantViewModel.loadRestaurants().observe(this, Observer { bussinessList ->
            bussinessList?.apply {
                forEach { bussiness ->
                    println("bussiness: ${bussiness.image_url}")
                }
            }
        })

        initAdapter()
    }

    private fun initAdapter() {
        binding.businessViewPager.apply {
            adapter = BusinessCardStackAdapter(supportFragmentManager)
            setPageTransformer(false, BusinessCardStackTransFormer())
            offscreenPageLimit = 5
        }

    }
}
