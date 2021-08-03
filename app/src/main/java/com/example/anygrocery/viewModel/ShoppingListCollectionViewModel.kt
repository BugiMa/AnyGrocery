package com.example.anygrocery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anygrocery.model.ShoppingListModel

class ShoppingListCollectionViewModel : ViewModel() {

    private val listCollection: ArrayList<ShoppingListModel>? = null
}