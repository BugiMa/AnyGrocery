package com.example.anygrocery.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.anygrocery.util.AnyGroceryTypeConverters
import java.time.OffsetDateTime

@Entity (tableName = "shopping_list_table")
@TypeConverters (AnyGroceryTypeConverters::class)
data class ShoppingList(
    @PrimaryKey (autoGenerate = false)
    @NonNull
    //val id: Int,
    val listName: String,
    val allCount: Int? = 0,
    val checkedCount: Int? = 0,
    var creationDate: OffsetDateTime? = OffsetDateTime.now(),
    var isArchived: Boolean? = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(OffsetDateTime::class.java.classLoader) as? OffsetDateTime,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(listName)
        parcel.writeValue(allCount)
        parcel.writeValue(checkedCount)
        parcel.writeValue(isArchived)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingList> {
        override fun createFromParcel(parcel: Parcel): ShoppingList {
            return ShoppingList(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingList?> {
            return arrayOfNulls(size)
        }
    }
}



