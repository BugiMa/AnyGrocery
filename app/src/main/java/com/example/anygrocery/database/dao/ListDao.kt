package com.example.anygrocery.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anygrocery.model.ListWithProducts
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList

@Dao
interface ListDao {

    @Insert
    suspend fun insert(shoppingList: ShoppingList)
    @Update
    suspend fun update(shoppingList: ShoppingList)
    @Delete
    suspend fun delete(shoppingList: ShoppingList)

    @Query("DELETE FROM shopping_list_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM shopping_list_table WHERE isArchived = :isArchived ORDER BY datetime(creationDate) DESC")
    fun ofType(isArchived: Boolean): LiveData<List<ShoppingList>>

    @Insert
    suspend fun insertProduct(product: Product)

    @Transaction
    @Query("SELECT * FROM shopping_list_table WHERE listName = :listName")
    suspend fun getListWithProducts(listName: String): List<ListWithProducts>

    @Query("DELETE FROM shopping_list_table")
    fun nukeTable()
}