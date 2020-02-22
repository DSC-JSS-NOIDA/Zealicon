package tronku.project.zealicon.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import tronku.project.zealicon.Model.EventTrackDB

@Dao
interface EventDao {

    @Query("SELECT * from event_tracks")
    suspend fun getAll(): List<EventTrackDB>

    @Insert
    fun insertEvent(event: EventTrackDB)

}