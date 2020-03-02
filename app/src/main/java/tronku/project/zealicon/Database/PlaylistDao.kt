package tronku.project.zealicon.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tronku.project.zealicon.Model.PlaylistDB

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToPlaylist(playlistDB: PlaylistDB)

    @Query("DELETE from playlist where eventId = :eventId and isRegistered = 0")
    suspend fun removeFromPlaylist(eventId: Int)

    @Query("SELECT COUNT(*) from playlist p where p.eventId = :eventId")
    suspend fun checkIfAdded(eventId: Int): Int

    @Query("SELECT COUNT(*) from playlist p where p.eventId = :eventId and p.isRegistered = 1")
    suspend fun checkIfRegistered(eventId: Int): Int
}