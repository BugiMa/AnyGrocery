package com.example.anygrocery.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ShoppingListCollectionFragment()
            1 -> return ShoppingListCollectionFragment()
        }
        return ShoppingListCollectionFragment()
    }

    override fun getItemCount(): Int {
        return 2 //TODO: Constant : PAGE_COUNT
    }
}