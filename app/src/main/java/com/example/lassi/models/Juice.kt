package com.example.lassi.models

import android.os.Parcel
import android.os.Parcelable

data class Juice (
    var id: String = "",
    var title: String = "",
    var image: String = "",
    var recipe: ArrayList<String> = ArrayList(),
    var popular: Boolean = true,
    var mood: ArrayList<String> = ArrayList(),
    var ingredients: ArrayList<String> = ArrayList()
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeStringList(recipe)
        parcel.writeByte(if (popular) 1 else 0)
        parcel.writeStringList(mood)
        parcel.writeStringList(ingredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Juice> {
        override fun createFromParcel(parcel: Parcel): Juice {
            return Juice(parcel)
        }

        override fun newArray(size: Int): Array<Juice?> {
            return arrayOfNulls(size)
        }
    }

}