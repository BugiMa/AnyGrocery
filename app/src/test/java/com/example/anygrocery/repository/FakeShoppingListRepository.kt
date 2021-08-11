package com.example.anygrocery.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.anygrocery.data.local.model.ListsWithProducts
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList

class FakeShoppingListRepository: IShoppingListRepository {

    private val shoppingLists = mutableListOf<ShoppingList>()
    private val products = mutableListOf<Product>()
    private val listsWithProducts = mutableListOf<ListsWithProducts>()

    private val shoppingListsLiveData = MutableLiveData<List<ShoppingList>>(shoppingLists)
    private val productsLiveData = MutableLiveData<List<Product>>(products)
    private val listsWithProductsLiveData = MutableLiveData<List<ListsWithProducts>>(listsWithProducts)

    private fun refreshLiveData() {
        shoppingListsLiveData.postValue(shoppingLists)
        productsLiveData.postValue(products)
        listsWithProductsLiveData.postValue(listsWithProducts)
    }

    override suspend fun insertList(shoppingList: ShoppingList) {
        shoppingLists.add(shoppingList)
        listsWithProducts.add(ListsWithProducts(shoppingList, emptyList()))
        refreshLiveData()
    }
    override suspend fun updateList(shoppingList: ShoppingList) {
        shoppingLists.find{it.id == shoppingList.id}?.apply {
            this.name = shoppingList.name
            this.allCount = shoppingList.allCount
            this.checkedCount = shoppingList.checkedCount
            this.creationDate = shoppingList.creationDate
            this.isArchived = shoppingList.isArchived
        }
        refreshLiveData()
    }
    override suspend fun deleteList(shoppingList: ShoppingList) {
        shoppingLists.remove(shoppingList)
        listsWithProducts.remove(listsWithProducts.find {it.shoppingList == shoppingList})
        refreshLiveData()
    }

    override suspend fun insertProduct(product: Product) {
        if (shoppingLists.isNotEmpty()) {

            products.add(product)

            listsWithProducts.find { it.shoppingList.id == product.listId }
                .apply { this!!.products += product }

            refreshLiveData()
        }
    }
    override suspend fun updateProduct(product: Product) {
        if (shoppingLists.isNotEmpty()) {

            products.find {it.name == product.name && it.listId == product.listId}?.apply {
                this.amount = product.amount
                this.isChecked = product.isChecked
            }

            listsWithProducts.find { it.shoppingList.id == product.listId }
                .apply { this!!.products.find { it.name == product.name }
                    .apply{
                        this?.amount = product.amount
                        this?.isChecked = product.isChecked
                    }
                }

            refreshLiveData()
        }
    }
    override suspend fun deleteProduct(product: Product) {
        if (shoppingLists.isNotEmpty()) {

            products.remove(product)

            listsWithProducts.find { it.shoppingList.id == product.listId }
                .apply { this!!.products -= product }

            refreshLiveData()
        }
    }

    override fun allActive(): LiveData<List<ShoppingList>> {
        return MutableLiveData(shoppingListsLiveData.value?.filter { it.isArchived == false })
    }
    override fun allArchived(): LiveData<List<ShoppingList>> {
        return MutableLiveData(shoppingListsLiveData.value?.filter { it.isArchived == true })
    }
    override fun listsWithProducts(): LiveData<List<ListsWithProducts>> {
        return listsWithProductsLiveData
    }
}