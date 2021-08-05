package com.example.anygrocery.database.repository

import androidx.lifecycle.LiveData
import com.example.anygrocery.database.dao.ListDao
import com.example.anygrocery.model.ListWithProducts
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList

class ListRepository (private val listDao: ListDao) {

    val allActive: LiveData<List<ShoppingList>> = listDao.ofType(false)
    val allArchived: LiveData<List<ShoppingList>> = listDao.ofType(true)

    suspend fun insert(shoppingList: ShoppingList) {
        listDao.insert(shoppingList)
    }

    suspend fun update(shoppingList: ShoppingList) {
        listDao.update(shoppingList)
    }

    suspend fun delete(shoppingList: ShoppingList) {
        listDao.delete(shoppingList)
    }

    suspend fun deleteAll() {
        listDao.deleteAll()
    }

    suspend fun getListWithProducts(listName: String): List<ListWithProducts> {
        return listDao.getListWithProducts(listName)
    }

    fun nuke() {
        listDao.nukeTable()
    }
}