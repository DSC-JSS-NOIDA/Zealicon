package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import tronku.project.zealicon.Model.Resource
import tronku.project.zealicon.Network.ApiClient
import tronku.project.zealicon.Network.EventsApi

class SubscriptionViewModel : ViewModel() {

    fun regUser(map: HashMap<String, String>) = liveData (Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClient.createService(EventsApi::class.java)
            val response = api.registerUser(map)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error("Something went wrong!"))
            }
        } catch (e: Exception) {
            emit(Resource.error("Something went wrong!"))
        }
    }

}
