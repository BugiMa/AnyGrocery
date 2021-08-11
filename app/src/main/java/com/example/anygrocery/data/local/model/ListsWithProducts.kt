package com.example.anygrocery.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class ListsWithProducts(
    @Embedded
    val shoppingList: ShoppingList,

    @Relation(
        parentColumn = "id",
        entityColumn = "listId",
        entity = Product::class
    )
    var products: List<Product>
)