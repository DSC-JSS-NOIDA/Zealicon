package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var mutablePlaylist = MutableLiveData<ArrayList<EventTrackDB>>()
    val playlist: LiveData<ArrayList<EventTrackDB>>

    init {
        playlist = mutablePlaylist
    }

    fun getPlaylist(db: RoomDB) {
        viewModelScope.launch {
            mutablePlaylist.postValue(db.EventDao().getAll() as ArrayList)
        }
    }
}
