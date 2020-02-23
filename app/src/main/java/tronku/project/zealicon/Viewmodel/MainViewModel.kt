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

class MainViewModel: ViewModel() {

    private val gson by lazy { Gson() }
    private var mutableIsParsed = MutableLiveData<Boolean>()
    val isParsed: LiveData<Boolean>

    init {
        isParsed = mutableIsParsed
        mutableIsParsed.postValue(false)
    }

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(EventsApi::class.java)
        val response = api.getEventsAsync(1)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()))
        }
    }

    fun parse(db: RoomDB, res: String?) {
        if (res.isNullOrEmpty()) {
            //show error message
            mutableIsParsed.postValue(false)
        } else {
            val jsonObject = JSONObject(res)
            val trackList: ArrayList<EventTrack> = gson.fromJson(jsonObject.get("data").toString(),
                object : TypeToken<ArrayList<EventTrack>>() {}.type)
            saveToDB(db, trackList)
        }
    }

    private fun saveToDB(db: RoomDB, trackList: ArrayList<EventTrack>) {
        viewModelScope.launch {
            db.EventDao().deleteEvents()
            trackList.forEach {
                val category: String = getCategory(it.categoryId)
                val eventTrackDB = EventTrackDB(
                    it.id,
                    it.name ?: "",
                    it.description ?: "",
                    it.rule ?: "",
                    it.day + 23,
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