package com.example.anygrocery.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.anygrocery.database.AnyGroceryDatabase
import com.example.anygrocery.database.repository.ShoppingListRepository
import com.example.anygrocery.model.ListsWithProducts
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShoppingListRepository

    private val activeListsLiveData:   LiveData<List<ShoppingList>>
    private val archivedListsLiveData: LiveData<List<ShoppingList>>
    private var listsWithProducts: LiveData<List<ListsWithProducts>>

    init {
        val dao = AnyGroceryDatabase.getInstance(application).shoppingListDao()
        repository = ShoppingListRepository(dao)
        
        activeListsLiveData = repository.allActive
        archivedListsLiveData = repository.allArchived
        listsWithProducts = repository.listsWithProducts

    }

    fun getActiveListsLiveData(): LiveData<List<ShoppingList>> {
        return activeListsLiveData
    }
    fun getArchivedListsLiveData(): LiveData<List<ShoppingList>> {
        return activeListsLiveData
    }
    fun getListsWithProducts(): LiveData<List<ListsWithProducts>> {
        return listsWithProducts
    }

    fun addList(list: ShoppingList) = viewModelScope.launch {
        repository.insertList(list)
    }
    fun deleteList(list: ShoppingList) = viewModelScope.launch {
        repository.deleteList(list)
    }
    fun archiveList(list: ShoppingList) = viewModelScope.launch {
        repository.updateList(list.apply{ isArchived = true})
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.insertProduct(product)
    }
    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.deleteProduct(product)
    }
    fun checkProduct(product: Product) = viewModelScope.launch {
        val check = product.apply { isChecked = !isChecked!! }
        repository.updateProduct(check)
    }
}
