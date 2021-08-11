package com.example.anygrocery.di

import android.content.Context
import androidx.room.Room
import com.example.anygrocery.data.local.database.AnyGroceryDatabase
import com.example.anygrocery.data.local.dao.ShoppingListDao
import com.example.anygrocery.repository.IShoppingListRepository
import com.example.anygrocery.repository.ShoppingListRepository
import com.example.anygrocery.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAnyGroceryDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AnyGroceryDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingListDao(
        database: AnyGroceryDatabase
    ) = database.shoppingListDao()
}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideShoppingListRepository(
        dao: ShoppingListDao
    ) = ShoppingListRepository(dao) as IShoppingListRepository
}