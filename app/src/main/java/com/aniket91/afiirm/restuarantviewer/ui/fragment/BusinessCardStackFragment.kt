package com.aniket91.afiirm.restuarantviewer.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aniket91.afiirm.restuarantviewer.R
import com.aniket91.afiirm.restuarantviewer.model.entity.Business
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_business_card_item.*

class BusinessCardStackFragment(
    private val business: Business,
    private val toggleFavorite: (business: Business) -> Unit
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business_card_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(businessImageView.context)
            .load(business.image_url)
            .into(businessImageView)

        businessRatingTextView.text = business.rating.toString()
        businessNameTextView.text = business.name

        setFavorite(view.context)

        favoriteImageView.setOnClickListener {
            setFavorite(it.context)
            toggleFavorite(business)
        }
    }

    private fun setFavorite(context: Context) {
        if (business.isFavorite) {
            business.isFavorite = false
            favoriteImageView.background =
                ContextCompat.getDrawable(context, R.drawable.ic_favorite_black_24dp)
        } else {
            business.isFavorite = true
            favoriteImageView.background = ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_border_black_24dp
            )
        }
    }
}