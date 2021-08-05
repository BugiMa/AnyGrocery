package com.example.anygrocery.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.anygrocery.database.AnyGroceryDatabase
import com.example.anygrocery.database.repository.ListRepository
import com.example.anygrocery.database.repository.ProductRepository
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository
    //private val repository: ProductRepository

    //private val allProducts: LiveData<List<Product>>
    //private val allChecked: LiveData<List<Product>>
    //private val productCount: Int?
    //private val checkedCount: Int?
    private val activeLists:   LiveData<List<ShoppingList>>
    private val archivedLists: LiveData<List<ShoppingList>>

    init {
        val listDao = AnyGroceryDatabase.getInstance(application).listDao()
        repository = ListRepository(listDao)
        activeLists = repository.allActive
        archivedLists = repository.allArchived
    }

    fun insert(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.insert(shoppingList)
    }

    fun update(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.update(shoppingList)
    }

    fun delete(shoppingList: ShoppingList) = viewModelScope.launch {
        repository.delete(shoppingList)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun nuke() = viewModelScope.launch {
        repository.nuke()
    }

    // getters & setters

    fun getActiveLists(): LiveData<List<ShoppingList>> {
        return activeLists
    }
    fun getArchivedLists(): LiveData<List<ShoppingList>> {
        return archivedLists
    }


}
