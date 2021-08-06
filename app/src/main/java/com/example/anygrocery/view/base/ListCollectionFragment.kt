package com.example.anygrocery.view.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.anygrocery.MainActivity
import com.example.anygrocery.R
import com.example.anygrocery.adapter.SectionsPagerAdapter
import com.example.anygrocery.databinding.FragmentListCollectionBinding
import com.example.anygrocery.util.SpinnerTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListCollectionFragment : Fragment(), MainActivity.IOnBackPressed {

    private lateinit var viewPager: ViewPager2

    private var _binding: FragmentListCollectionBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCollectionBinding.inflate(inflater, container, false)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())

        viewPager = binding.viewPager
        viewPager.apply {
            this.adapter = sectionsPagerAdapter
            this.setPageTransformer(SpinnerTransformer())
        }

        val tabs: TabLayout = binding.tabs
        val tabTitles: Array<String> = arrayOf(
            resources.getString(R.string.tab_label_1),
            resources.getString(R.string.tab_label_2)
        )
        val tabIcons: Array<Drawable?> = arrayOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_format_list_bulleted_24),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_archive_24)
        )

        TabLayoutMediator(
            tabs, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position]
            tab.icon = tabIcons[position]
        }.attach()

        return binding.root
    }

    override fun onBackPressed(): Boolean {
        return if (viewPager.currentItem == 1) {
            viewPager.currentItem = 0
            true
        } else {

            false
        }
    }

}