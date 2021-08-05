package com.example.anygrocery.database.repository

import androidx.lifecycle.LiveData
import com.example.anygrocery.database.dao.ProductDao
import com.example.anygrocery.model.Product

class ProductRepository (private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.all()
    val allChecked: LiveData<List<Product>> = productDao.ofType(true)

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }

    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    suspend fun deleteAll() {
        productDao.deleteAll()
    }
}