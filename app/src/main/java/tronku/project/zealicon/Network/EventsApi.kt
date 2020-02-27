package tronku.project.zealicon.Network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface EventsApi {

    @GET("http://backoffice.zealicon.in/api/event")
    suspend fun getEventsAsync(@Query("id") id: Int): Response<JsonObject>

    @Headers("device:android")
    @POST("api/v1/reg")
    suspend fun registerUser(@Body map: HashMap<String, String>): Response<JsonObject>

    @GET("api/v1/reg/search")
    suspend fun searchUser(@Query("tag") query: String): Response<JsonObject>
}