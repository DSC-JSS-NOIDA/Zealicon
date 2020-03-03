package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import tronku.project.zealicon.Database.RoomDB
import tronku.project.zealicon.Model.EventTrack
import tronku.project.zealicon.Model.EventTrackDB
import tronku.project.zealicon.Network.ApiClient
import tronku.project.zealicon.Network.EventsApi
import tronku.project.zealicon.Model.Resource
import java.lang.Exception
import java.net.SocketTimeoutException

class MainViewModel: ViewModel() {

    private val gson by lazy { Gson() }
    private var mutableHasLocalData = MutableLiveData<Boolean>()
    private var mutableIsParsed = MutableLiveData<Boolean>()
    val isParsed: LiveData<Boolean>
    val hasLocalData: LiveData<Boolean>

    init {
        isParsed = mutableIsParsed
        hasLocalData = mutableHasLocalData
        mutableIsParsed.postValue(false)
    }

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClient.createService(EventsApi::class.java)
            val response = api.getEventsAsync()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            }
        } catch (e: Exception) {
            if (e is SocketTimeoutException)
                emit(Resource.error("Something went wrong!"))
        }
    }

    fun parse(db: RoomDB, res: String?) {
        if (res.isNullOrEmpty()) {
            mutableIsParsed.postValue(false)
        } else {
            val jsonObject = JSONObject(res)
            val trackList: ArrayList<EventTrack> = gson.fromJson(jsonObject.get("data").toString(),
                object : TypeToken<ArrayList<EventTrack>>() {}.type)
            saveToDB(db, trackList)
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

    private fun getErrorMessage(response: String?): String {
        var errorMessage = "Something went wrong!"
        if (!response.isNullOrEmpty()) {
            val errorObject = JSONObject(response)
            errorMessage = errorObject.getString("message")
        }
        return errorMessage
    }

    private fun saveToDB(db: RoomDB, trackList: ArrayList<EventTrack>) {
        viewModelScope.launch {
            db.EventDao().deleteAllEvents()
            trackList.forEach {
                val category: String = getCategory(it.categoryId)
                val eventTrackDB = EventTrackDB(
                    it.id,
                    it.name ?: "",
                    it.description ?: "",
                    it.rule ?: "",
                    it.day,
                    category,
                    it.firstPrize,
                    it.secondPrize ?: 0,
                    it.contactName ?: "",
                    it.contactNo ?: "",
                    it.isActive == 1,
                    it.societyId
                )
                db.EventDao().insertEvent(eventTrackDB)
            }
            mutableIsParsed.postValue(true)
        }
    }

    private fun checkForLocalData(db: RoomDB) {
        viewModelScope.launch {
            if (db.EventDao().getEventsCount() != 0)
                mutableHasLocalData.postValue(true)
            else
                mutableHasLocalData.postValue(false)
        }
    }

    private fun getCategory(id: Int): String {
        return when(id) {
            1 -> "Colorado"
            2 -> "Mechavoltz"
            3 -> "Play-it-on"
            4 -> "Robotiles"
            5 -> "Coderz"
            else -> "Z-wars"
        }
    }

}