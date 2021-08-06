package com.example.anygrocery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anygrocery.databinding.ActivityMainBinding
import com.example.anygrocery.view.base.ListCollectionFragment

private const val TAG_LIST_COLLECTION_FRAGMENT = "TAG_FRAGMENT"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ListCollectionFragment(), TAG_LIST_COLLECTION_FRAGMENT)
                .disallowAddToBackStack()
                .commit()
        }
    }

    override fun onBackPressed() {
        val fragment: ListCollectionFragment? = supportFragmentManager.findFragmentByTag(TAG_LIST_COLLECTION_FRAGMENT) as ListCollectionFragment?
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            if (it) super.onBackPressed()
        }
    }

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }
}