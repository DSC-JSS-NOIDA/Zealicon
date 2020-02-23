package tronku.project.zealicon.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tronku.project.zealicon.Model.PlaylistDB

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToPlaylist(playlistDB: PlaylistDB)

    @Query("DELETE from playlist where eventId = :eventId")
    suspend fun removeFromPlaylist(eventId: Int)

    @Query("SELECT COUNT(*) from playlist p where p.eventId = :eventId")
    suspend fun checkIfAdded(eventId: Int): Int
}