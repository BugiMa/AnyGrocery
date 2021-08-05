package com.example.anygrocery.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.anygrocery.database.AnyGroceryDatabase
import com.example.anygrocery.database.repository.ProductRepository
import com.example.anygrocery.model.Product
import kotlinx.coroutines.launch

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    private val allProducts: LiveData<List<Product>>
    private val allChecked: LiveData<List<Product>>
    private val productCount: Int?
    private val checkedCount: Int?

    init {
        val productDao = AnyGroceryDatabase.getInstance(application).productDao()
        repository = ProductRepository(productDao)

        allProducts = repository.allProducts
        allChecked = repository.allChecked

        productCount = allProducts.value?.size
        checkedCount = allChecked.value?.size
    }

    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }

    fun update(product: Product) = viewModelScope.launch {
        repository.update(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    // getters & setters

    fun getAllProducts(): LiveData<List<Product>> {
        return allProducts
    }
    fun getAllChecked(): LiveData<List<Product>> {
        return allChecked
    }
    fun getProductCount(): Int? {
        return productCount
    }
    fun getCheckedCount(): Int? {
        return checkedCount
    }
}
