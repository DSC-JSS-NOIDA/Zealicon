package tronku.project.zealicon.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import tronku.project.zealicon.Model.EventTrack
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

    fun parse(res: String?) {
        if (res.isNullOrEmpty()) {
            //show error message
            mutableIsParsed.postValue(false)
        } else {
            val jsonObject = JSONObject(res)
            val trackList: ArrayList<EventTrack> = gson.fromJson(jsonObject.get("data").toString(),
                object : TypeToken<ArrayList<EventTrack>>() {}.type)
            Log.e("TRACKLIST", gson.toJson(trackList))
            mutableIsParsed.postValue(true)
        }
    }

}