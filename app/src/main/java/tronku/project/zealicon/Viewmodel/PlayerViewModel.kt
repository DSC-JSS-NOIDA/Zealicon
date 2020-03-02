package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.PlaylistDB

class PlayerViewModel(private var db: RoomDB) : ViewModel() {

    private var mutableIsAdded = MutableLiveData<Boolean>()
    private var mutableIsRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean>
    val isAdded : LiveData<Boolean>

    init {
        isAdded = mutableIsAdded
        isRegistered = mutableIsRegistered
    }

    fun checkIfReg(eventId: Int) {
        viewModelScope.launch {
            mutableIsRegistered.postValue(db.PlaylistDao().checkIfRegistered(eventId) == 1)
        }
    }

    fun checkAdded(eventId: Int) {
        viewModelScope.launch {
            mutableIsAdded.postValue(db.PlaylistDao().checkIfAdded(eventId) == 1)
        }
    }

    fun addToPlaylist(eventId: Int) {
        viewModelScope.launch {
            mutableIsAdded.postValue(true)
            db.PlaylistDao().addToPlaylist(PlaylistDB(eventId, false))
        }
    }

    fun removeFromPlaylist(eventId: Int) {
        viewModelScope.launch {
            mutableIsAdded.postValue(false)
            db.PlaylistDao().removeFromPlaylist(eventId)
        }
    }
}
