package tronku.project.zealicon.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.Model.PlaylistDB

@Database(entities = [EventTrackDB::class, PlaylistDB::class], version = 5)
abstract class RoomDB: RoomDatabase() {

    abstract fun EventDao(): EventDao
    abstract fun PlaylistDao(): PlaylistDao

    companion object {
        @Volatile private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, RoomDB::class.java, "events.db")
                .fallbackToDestructiveMigration().build()
    }

}