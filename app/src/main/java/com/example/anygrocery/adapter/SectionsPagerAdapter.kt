package com.example.anygrocery.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anygrocery.util.Constants
import com.example.anygrocery.view.recyclerViewFab.ListCollectionRecyclerViewFabFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ListCollectionRecyclerViewFabFragment.newInstance(false)
            1 -> return ListCollectionRecyclerViewFabFragment.newInstance(true)
        }
        return ListCollectionRecyclerViewFabFragment()
    }

    override fun getItemCount(): Int {
        return Constants.TAB_NUMBER
    }
}