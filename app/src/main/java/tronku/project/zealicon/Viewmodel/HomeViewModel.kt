package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrackDB

class HomeViewModel : ViewModel() {

    private var mutableUpcomingList = MutableLiveData<ArrayList<EventTrackDB>>()
    val upcomingList: LiveData<ArrayList<EventTrackDB>>

    init {
        upcomingList = mutableUpcomingList
    }

    fun getUpcomingHits(db: RoomDB) {
        viewModelScope.launch {
            mutableUpcomingList.postValue(db.EventDao().getDayEvents(24) as ArrayList<EventTrackDB>)
        }
    }

}
