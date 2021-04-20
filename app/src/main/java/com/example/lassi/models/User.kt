package com.example.lassi.models

import android.os.Parcel
import android.os.Parcelable

data class User (
    var userId: String = "",
    var likedJuices: ArrayList<String> = ArrayList(),
    var savedList: ArrayList<String> = ArrayList(),
    var postedJuices: ArrayList<Juice> = ArrayList()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.createTypedArrayList(Juice)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeStringList(likedJuices)
        parcel.writeStringList(savedList)
        parcel.writeTypedList(postedJuices)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}