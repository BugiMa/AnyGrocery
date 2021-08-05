package com.example.anygrocery.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "product_table")
data class Product(
    @PrimaryKey (autoGenerate = false)
    @NonNull
    //val id: Int,
    val productName: String,
    var amount: Int,
    var isChecked: Boolean? = false,
)