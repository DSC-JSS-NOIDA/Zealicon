package tronku.project.zealicon.Model

import android.os.Parcel
import android.os.Parcelable

data class EventTrack(
    val id: Int,
    val name: String?,
    val description: String?,
    val startDateTime: String?,
    val endDateTime: String?,
    val category: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(startDateTime)
        parcel.writeString(endDateTime)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventTrack> {
        override fun createFromParcel(parcel: Parcel): EventTrack {
            return EventTrack(parcel)
        }

        override fun newArray(size: Int): Array<EventTrack?> {
            return arrayOfNulls(size)
        }
    }
}