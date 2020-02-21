package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import tronku.project.zealicon.Network.ApiClient
import tronku.project.zealicon.Network.EventsApi
import tronku.project.zealicon.Model.Resource

class MainViewModel: ViewModel() {

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(EventsApi::class.java)
        val response = api.getEventsAsync("1")
        if(response.isSuccessful) {
            emit(Resource.success(response.body()))
        }
    }

}