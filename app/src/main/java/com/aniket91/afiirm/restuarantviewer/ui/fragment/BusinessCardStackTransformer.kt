package com.aniket91.afiirm.restuarantviewer.ui.fragment

import android.view.View
import androidx.viewpager.widget.ViewPager

class BusinessCardStackTransformer : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            if (position >= 0) {
                page.scaleX = 0.8f
                page.scaleY = 0.9f
                page.translationX =  - page.width * position
                page.translationY = - 30 * position
            }
        }
    }
