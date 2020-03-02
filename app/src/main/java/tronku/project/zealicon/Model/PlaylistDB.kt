package tronku.project.zealicon.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistDB (
    @PrimaryKey val eventId: Int,
    var isRegistered: Boolean
)