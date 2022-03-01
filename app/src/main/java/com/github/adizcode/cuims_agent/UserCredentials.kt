package com.github.adizcode.cuims_agent

import android.os.Parcel
import android.os.Parcelable

data class UserCredentials(val uid: String, val password: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserCredentials> {
        override fun createFromParcel(parcel: Parcel): UserCredentials {
            return UserCredentials(parcel)
        }

        override fun newArray(size: Int): Array<UserCredentials?> {
            return arrayOfNulls(size)
        }
    }
}
