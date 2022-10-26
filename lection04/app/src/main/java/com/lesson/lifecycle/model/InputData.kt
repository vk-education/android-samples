package com.lesson.lifecycle.model

import android.os.Parcel
import android.os.Parcelable

class InputData(
    val field1: String,
    val field2: Int
): Parcelable {



    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(field1)
        parcel.writeInt(field2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InputData> {
        override fun createFromParcel(parcel: Parcel): InputData {
            return InputData(parcel)
        }

        override fun newArray(size: Int): Array<InputData?> {
            return arrayOfNulls(size)
        }
    }
}