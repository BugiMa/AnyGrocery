package com.example.anygrocery.database.repository

import androidx.lifecycle.LiveData
import com.example.anygrocery.database.dao.ShoppingListDao
import com.example.anygrocery.model.ListsWithProducts
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList

class ShoppingListRepository (private val shoppingListDao: ShoppingListDao) {

    val allActive: LiveData<List<ShoppingList>> = shoppingListDao.ofType(false)
    val allArchived: LiveData<List<ShoppingList>> = shoppingListDao.ofType(true)
    val listsWithProducts: LiveData<List<ListsWithProducts>> = shoppingListDao.listsWithProducts()

    suspend fun insertList(shoppingList: ShoppingList) {
        shoppingListDao.insert(shoppingList)
    }
    suspend fun updateList(shoppingList: ShoppingList) {
        shoppingListDao.update(shoppingList)
    }
    suspend fun deleteList(shoppingList: ShoppingList) {
        shoppingListDao.delete(shoppingList)
    }

    suspend fun insertProduct(product: Product) {
        return shoppingListDao.insert(product)
    }
    suspend fun updateProduct(product: Product) {
        shoppingListDao.update(product)
    }
    suspend fun deleteProduct(product: Product) {
        shoppingListDao.delete(product)
    }



    //fun listsWithProducts(): LiveData<List<ListsWithProducts>> {
    //    return shoppingListDao.listsWithProducts()
    //}

    //TODO: save list products function
}