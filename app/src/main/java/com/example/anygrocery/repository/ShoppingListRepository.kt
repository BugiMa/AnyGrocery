package com.example.anygrocery.repository

import androidx.lifecycle.LiveData
import com.example.anygrocery.data.local.dao.ShoppingListDao
import com.example.anygrocery.data.local.model.ListsWithProducts
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList
import javax.inject.Inject

class ShoppingListRepository @Inject constructor (
    private val shoppingListDao: ShoppingListDao
    ): IShoppingListRepository {

    override suspend fun insertList(shoppingList: ShoppingList) {
        shoppingListDao.insert(shoppingList)
    }
    override suspend fun updateList(shoppingList: ShoppingList) {
        shoppingListDao.update(shoppingList)
    }
    override suspend fun deleteList(shoppingList: ShoppingList) {
        shoppingListDao.delete(shoppingList)
    }


    override suspend fun insertProduct(product: Product) {
        return shoppingListDao.insert(product)
    }
    override suspend fun updateProduct(product: Product) {
        shoppingListDao.update(product)
    }
    override suspend fun deleteProduct(product: Product) {
        shoppingListDao.delete(product)
    }


    override fun allActive(): LiveData<List<ShoppingList>> {
        return shoppingListDao.ofType(false)
    }
    override fun allArchived(): LiveData<List<ShoppingList>> {
        return shoppingListDao.ofType(true)
    }
    override fun listsWithProducts(): LiveData<List<ListsWithProducts>> {
        return shoppingListDao.listsWithProducts()
    }

}