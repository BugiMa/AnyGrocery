package com.example.anygrocery.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anygrocery.model.ListsWithProducts
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList

@Dao
interface ShoppingListDao {

    @Insert
    suspend fun insert(shoppingList: ShoppingList)
    @Update
    suspend fun update(shoppingList: ShoppingList)
    @Delete
    suspend fun delete(shoppingList: ShoppingList)

    @Insert
    suspend fun insert(product: Product)
    @Update
    suspend fun update(product: Product)
    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM ShoppingList WHERE isArchived = :isArchived ORDER BY datetime(creationDate) DESC")
    fun ofType(isArchived: Boolean): LiveData<List<ShoppingList>>

    @Transaction
    @Query("SELECT * FROM ShoppingList")
    fun listsWithProducts(): LiveData<List<ListsWithProducts>>
}