package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB

class PlaylistViewModel : ViewModel() {

    private var mutablePlayist = MutableLiveData<ArrayList<EventTrackDB>>()
    val playlist: LiveData<ArrayList<EventTrackDB>>

    init {
        playlist = mutablePlayist
    }

    fun getUpcomingHits(db: RoomDB, day: Int = 1) {
        viewModelScope.launch {
            mutablePlayist.postValue(db.EventDao().getDayEvents(day) as ArrayList<EventTrackDB>)
        }
    }

}