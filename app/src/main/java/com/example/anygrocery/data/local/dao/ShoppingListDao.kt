package com.example.anygrocery.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anygrocery.data.local.model.ListsWithProducts
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList

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

    //used for tests
    @Query("SELECT * FROM shopping_lists")
    fun allLists(): LiveData<List<ShoppingList>>

    //used for tests
    @Query("SELECT * FROM products")
    fun allProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM shopping_lists WHERE isArchived = :isArchived ORDER BY datetime(creationDate) DESC")
    fun ofType(isArchived: Boolean): LiveData<List<ShoppingList>>

    @Transaction
    @Query("SELECT * FROM shopping_lists")
    fun listsWithProducts(): LiveData<List<ListsWithProducts>>
}