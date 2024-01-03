package com.example.experiments2.viewmodel

import android.os.Parcel
import android.os.Parcelable

data class ViewModelData<T>(
    val status: ViewModelEnum,
    val data: T?,
    val errorMessage: String? = null
) : Parcelable where T : Parcelable? {
    constructor(parcel: Parcel) : this(
        ViewModelEnum.values()[parcel.readInt()],
        parcel.readParcelable<T>(ClassLoader.getSystemClassLoader()),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status.ordinal)
        parcel.writeParcelable(data, flags)
        parcel.writeString(errorMessage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ViewModelData<*>> {
        override fun createFromParcel(parcel: Parcel): ViewModelData<*> {
            return ViewModelData<Parcelable?>(parcel)
        }

        override fun newArray(size: Int): Array<ViewModelData<*>?> {
            return arrayOfNulls(size)
        }
    }
}

