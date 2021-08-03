package com.example.anygrocery.model

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

data class ShoppingListModel(
    var name: String?,
    val productList: ArrayList<Any>?,
    val productCount: Int? = 0,
    val checkedProductCount: Int? = 0,
    var creationDate: String?,
    var isArchived: Boolean? = false,
): Parcelable {

    init {
        // assigning creation date on class instance creation
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.ROOT)
        val currentDate = sdf.format(Date())
        creationDate = currentDate
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readArrayList(ClassLoader.getSystemClassLoader()),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(productCount)
        parcel.writeValue(checkedProductCount)
        parcel.writeString(creationDate)
        parcel.writeValue(isArchived)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingListModel> {
        override fun createFromParcel(parcel: Parcel): ShoppingListModel {
            return ShoppingListModel(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingListModel?> {
            return arrayOfNulls(size)
        }
    }


}



