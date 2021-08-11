package com.example.anygrocery.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.anygrocery.data.local.model.ListsWithProducts
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList
import com.example.anygrocery.repository.IShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor (
    application: Application,
    private val repository: IShoppingListRepository
) : AndroidViewModel(application) {

    private val activeListsLiveData:   LiveData<List<ShoppingList>> = repository.allActive()
    private val archivedListsLiveData: LiveData<List<ShoppingList>> = repository.allArchived()
    private var listsWithProducts: LiveData<List<ListsWithProducts>> = repository.listsWithProducts()

    fun getActiveListsLiveData(): LiveData<List<ShoppingList>> {
        return activeListsLiveData
    }
    fun getArchivedListsLiveData(): LiveData<List<ShoppingList>> {
        return archivedListsLiveData
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

    fun addProduct(product: Product, list: ShoppingList) = viewModelScope.launch {

        val productFound = listsWithProducts.value?.find {it.shoppingList.id == list.id}
            ?.products?.find {it.name == product.name}
                ?.apply { amount += product.amount }

        if (productFound == null) {
            repository.insertProduct(product)
            repository.updateList(list.apply {
                allCount = allCount!! + 1 })
        } else {
            repository.updateProduct(productFound)
        }
    }

    fun deleteProduct(product: Product, list: ShoppingList) = viewModelScope.launch {
        repository.deleteProduct(product)
        repository.updateList(list.apply {
            allCount = allCount!! - 1
            checkedCount = if (product.isChecked!!) checkedCount!! - 1 else checkedCount})
    }
    fun checkProduct(product: Product, list: ShoppingList) = viewModelScope.launch {
        val check = product.apply { isChecked = !isChecked!! }
        repository.updateProduct(check)
        repository.updateList(list.apply {
            checkedCount = checkedCount!! + if (check.isChecked!!) 1 else -1 })
    }
}