package com.example.anygrocery.model

import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity (foreignKeys = [
    ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = CASCADE
        )
    ])
data class Product(
    //@PrimaryKey (autoGenerate = true)
    //val id: Int,
    //@ColumnInfo (index = true)
    @PrimaryKey
    val name: String,
    val listId : Int,
    var amount: Int,
    var isChecked: Boolean? = false,
)