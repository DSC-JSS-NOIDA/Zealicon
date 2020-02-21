package tronku.project.zealicon.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class EventTrack(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("rule") val rule: String?,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("winner1") val firstPrize: Int,
    @SerializedName("winner2") val secondPrize: Int?,
    @SerializedName("contact_name") val contactName: String?,
    @SerializedName("contact_no") val contactNo: String?,
    @SerializedName("is_active") val isActive: Boolean? = true,
    @SerializedName("society_id") val societyId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(rule)
        parcel.writeInt(categoryId)
        parcel.writeInt(firstPrize)
        parcel.writeValue(secondPrize)
        parcel.writeString(contactName)
        parcel.writeString(contactNo)
        parcel.writeValue(isActive)
        parcel.writeInt(societyId)
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
