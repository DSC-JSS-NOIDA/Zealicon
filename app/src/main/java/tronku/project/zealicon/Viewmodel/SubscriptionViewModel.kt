package tronku.project.zealicon.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
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

    private fun getErrorMessage(response: String?): String {
        var errorMessage = "Something went wrong!"
        if (!response.isNullOrEmpty()) {
            val errorObject = JSONObject(response)
            errorMessage = errorObject.getString("message")
        }
        return errorMessage
    }

}
