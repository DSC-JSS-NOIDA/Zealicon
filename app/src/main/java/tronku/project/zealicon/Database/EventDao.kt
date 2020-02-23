package tronku.project.zealicon.Database

import androidx.room.*
import tronku.project.zealicon.Model.EventTrackDB

@Dao
interface EventDao {

    @Query("SELECT * from event_tracks")
    suspend fun getAll(): List<EventTrackDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventTrackDB)

    @Query("SELECT * from event_tracks where day = :day")
    suspend fun getDayEvents(day: Int): List<EventTrackDB>

    @Query("SELECT * from event_tracks where category = :category")
    suspend fun getCategEvents(category: String): List<EventTrackDB>

    @Query("DELETE from event_tracks")
    suspend fun deleteEvents()

    @Query("SELECT * from event_tracks where isAdded = 1")
    suspend fun getMyPlaylist(): List<EventTrackDB>

    @Query("UPDATE event_tracks SET isAdded = 1 where id = :eventId")
    suspend fun addToPlaylist(eventId: Int)

    @Query("UPDATE event_tracks SET isAdded = 0 where id = :eventId")
    suspend fun removeFromPlaylist(eventId: Int)
}