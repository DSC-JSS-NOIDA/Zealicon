package tronku.project.zealicon.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_tracks")
data class EventTrackDB (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "rule") var rule: String,
    @ColumnInfo(name = "day") var day: Int,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "firstPrize") var firstPrize: Int,
    @ColumnInfo(name = "secondPrize") var secondPrize: Int,
    @ColumnInfo(name = "contactName") var contactName: String,
    @ColumnInfo(name = "contactNo") var contactNo: String,
    @ColumnInfo(name = "isActive") var isActive: Boolean,
    @ColumnInfo(name = "societyId") var societyId: Int
)