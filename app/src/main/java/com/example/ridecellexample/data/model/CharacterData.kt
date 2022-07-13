package com.example.ridecellexample.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterData(
    @PrimaryKey()
    var id: String = "",
    val name: String?,
    val species: String?,
    val image: String?,
    val gender: String?,
    val status: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(species)
        parcel.writeString(image)
        parcel.writeString(gender)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterData> {
        override fun createFromParcel(parcel: Parcel): CharacterData {
            return CharacterData(parcel)
        }

        override fun newArray(size: Int): Array<CharacterData?> {
            return arrayOfNulls(size)
        }
    }
}