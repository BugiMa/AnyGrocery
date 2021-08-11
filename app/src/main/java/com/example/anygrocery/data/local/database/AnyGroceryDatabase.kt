package com.example.anygrocery.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.anygrocery.data.local.dao.ShoppingListDao
import com.example.anygrocery.data.local.model.Product
import com.example.anygrocery.data.local.model.ShoppingList
import com.example.anygrocery.util.AnyGroceryTypeConverters

@Database(
    entities = [ShoppingList::class, Product::class],
    version = 1,
    exportSchema = false)
@TypeConverters(AnyGroceryTypeConverters::class)
abstract class AnyGroceryDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao
}

