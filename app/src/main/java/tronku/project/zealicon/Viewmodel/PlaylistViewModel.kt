package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB

class PlaylistViewModel : ViewModel() {

    private var mutablePlaylist = MutableLiveData<ArrayList<EventTrackDB>>()
    val playlist: LiveData<ArrayList<EventTrackDB>>

    init {
        playlist = mutablePlaylist
    }

    fun getPlaylist(db: RoomDB, day: Int = 1) {
        viewModelScope.launch {
            mutablePlaylist.postValue(db.EventDao().getDayEvents(day) as ArrayList)
        }
    }

}