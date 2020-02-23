package tronku.project.zealicon.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_tracks")
data class EventTrackDB (
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "rule") var rule: String?,
    @ColumnInfo(name = "day") var day: Int,
    @ColumnInfo(name = "category") var category: String?,
    @ColumnInfo(name = "firstPrize") var firstPrize: Int,
    @ColumnInfo(name = "secondPrize") var secondPrize: Int,
    @ColumnInfo(name = "contactName") var contactName: String?,
    @ColumnInfo(name = "contactNo") var contactNo: String?,
    @ColumnInfo(name = "isActive") var isActive: Boolean,
    @ColumnInfo(name = "societyId") var societyId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(rule)
        parcel.writeInt(day)
        parcel.writeString(category)
        parcel.writeInt(firstPrize)
        parcel.writeInt(secondPrize)
        parcel.writeString(contactName)
        parcel.writeString(contactNo)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeInt(societyId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventTrackDB> {
        override fun createFromParcel(parcel: Parcel): EventTrackDB {
            return EventTrackDB(parcel)
        }

        override fun newArray(size: Int): Array<EventTrackDB?> {
            return arrayOfNulls(size)
        }
    }
}