package com.aniket91.afiirm.restuarantviewer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aniket91.afiirm.restuarantviewer.ui.fragment.BusinessCardStackFragment

class BusinessCardStackAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = BusinessCardStackFragment()

    override fun getCount(): Int = 5
}