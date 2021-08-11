package com.example.anygrocery.data.local.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity ( tableName = "products",
    primaryKeys = ["name", "listId"] ,
    foreignKeys = [ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = CASCADE
    )]
)
data class Product(
    var name: String,
    @ColumnInfo(index = true)
    val listId : Int,
    var amount: Int,
    var isChecked: Boolean? = false,
)