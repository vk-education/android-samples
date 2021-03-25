package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

class DroidParcel(): Parcelable {
    var id: Int = 0
    var name: String = "0"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DroidParcel> {
        override fun createFromParcel(parcel: Parcel): DroidParcel {
            return DroidParcel(parcel)
        }

        override fun newArray(size: Int): Array<DroidParcel?> {
            return arrayOfNulls(size)
        }
    }
}