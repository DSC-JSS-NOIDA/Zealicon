package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.PlaylistDB
import tronku.project.zealicon.Model.Resource
import tronku.project.zealicon.Model.User
import tronku.project.zealicon.Network.ApiClient
import tronku.project.zealicon.Network.EventsApi

class InfoViewModel: ViewModel() {

    private var mutableIsRegistered = MutableLiveData<Boolean>()
    val isRegistered: LiveData<Boolean>

    init {
        isRegistered = mutableIsRegistered
    }

    fun checkIfReg(eventId: Int, db: RoomDB) {
        viewModelScope.launch {
            mutableIsRegistered.postValue(db.PlaylistDao().checkIfRegistered(eventId) == 1)
        }
    }

    fun registerForEvent(currentUser: User, eventId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClient.createService(EventsApi::class.java)
            val response = api.registerForEvent(currentUser.name, currentUser.email, eventId,
                currentUser.zealID.toString(), currentUser.mobile)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(getErrorMessage(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            emit(Resource.error("Something went wrong!"))
        }
    }

    fun searchUser(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClient.createService(EventsApi::class.java)
            val response = api.searchUser(query)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(getErrorMessage(response.errorBody()?.string())))
            }
        } catch (e: Exception) {
            emit(Resource.error("Something went wrong!"))
        }
    }

    fun addAsRegistered(eventId: Int, db: RoomDB) {
        viewModelScope.launch {
            db.PlaylistDao().addToPlaylist(PlaylistDB(eventId, true))
        }
    }

    private fun getErrorMessage(response: String?): String {
        var errorMessage = "Something went wrong!"
        if (!response.isNullOrEmpty()) {
            val errorObject = JSONObject(response)
            errorMessage = errorObject.getString("message")
        }
        return errorMessage
    }

}