package com.example.anygrocery

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.anygrocery.adapter.SectionsPagerAdapter
import com.example.anygrocery.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //assign binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //assign view pager adapter
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        // assign view pager and attach adapter to it
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        //assign tab layout, array of tab titles and array of tab icons
        val tabs: TabLayout = binding.tabs
        val tabTitles: Array<String> = arrayOf(
            resources.getString(R.string.tab_label_1),
            resources.getString(R.string.tab_label_2))
        val tabIcons: Array<Drawable?> = arrayOf(
            ContextCompat.getDrawable(this, R.drawable.ic_round_format_list_bulleted_24),
            ContextCompat.getDrawable(this, R.drawable.ic_round_archive_24))

        // attach tabs to view pager
        // add titles and icons to corresponding tabs
        TabLayoutMediator(
            tabs, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position]
            tab.icon = tabIcons[position]
        }.attach()
    }
}