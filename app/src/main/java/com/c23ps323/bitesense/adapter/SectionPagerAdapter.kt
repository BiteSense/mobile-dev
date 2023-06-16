package com.c23ps323.bitesense.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c23ps323.bitesense.ui.detail.CompositionFragment
import com.c23ps323.bitesense.ui.detail.DescriptionFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var dataId: String? = null

    fun setId(id: String) {
        dataId = id
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = DescriptionFragment()
                fragment.arguments = Bundle().apply {
                    putString(DescriptionFragment.EXTRA_ID, dataId)
                }
            }
            1 -> {
                fragment = CompositionFragment()
                fragment.arguments = Bundle().apply {
                    putString(CompositionFragment.EXTRA_ID, dataId)
                }
            }
        }
        return fragment as Fragment
    }

}