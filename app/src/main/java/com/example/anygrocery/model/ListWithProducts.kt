package com.example.anygrocery.model

import androidx.room.Embedded
import androidx.room.Relation

data class ListWithProducts(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "listName",
        entityColumn = "productName"
    )
    val products: List<Product>
)