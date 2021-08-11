package com.example.anygrocery.repository

import androidx.lifecycle.LiveData
import com.example.anygrocery.data.local.model.ListsWithProducts
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList

interface IShoppingListRepository {

    suspend fun insertList(shoppingList: ShoppingList)
    suspend fun updateList(shoppingList: ShoppingList)
    suspend fun deleteList(shoppingList: ShoppingList)

    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)

    fun allActive(): LiveData<List<ShoppingList>>
    fun allArchived(): LiveData<List<ShoppingList>>
    fun listsWithProducts(): LiveData<List<ListsWithProducts>>
}