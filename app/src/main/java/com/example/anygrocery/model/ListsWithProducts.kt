package com.example.anygrocery.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ListsWithProducts(
    @Embedded
    val shoppingList: ShoppingList,

    @Relation(
        parentColumn = "id",
        entityColumn = "listId",
        entity = Product::class
    )
    val products: List<Product>
)