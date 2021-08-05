package com.example.anygrocery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.anygrocery.database.dao.ProductDao
import com.example.anygrocery.database.dao.ListDao
import com.example.anygrocery.model.Product
import com.example.anygrocery.model.ShoppingList
import com.example.anygrocery.util.AnyGroceryTypeConverters

@Database(entities = [ShoppingList::class, Product::class], version = 1, exportSchema = false)
@TypeConverters(AnyGroceryTypeConverters::class)
abstract class AnyGroceryDatabase : RoomDatabase() {

    //abstract val listDao: ListDao
    //abstract val productDao: ProductDao

    abstract fun listDao(): ListDao
    abstract fun productDao(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: AnyGroceryDatabase? = null

        fun getInstance(context: Context): AnyGroceryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnyGroceryDatabase::class.java,
                        "any_grocery_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}

