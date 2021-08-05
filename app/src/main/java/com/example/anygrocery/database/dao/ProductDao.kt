package com.example.anygrocery.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anygrocery.model.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)
    @Update
    suspend fun update(product: Product)
    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_table")
    fun all(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE isChecked = :isChecked")
    fun ofType(isChecked: Boolean): LiveData<List<Product>>

}