package tronku.project.zealicon.Network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventsApi {

    @GET("event")
    suspend fun getEventsAsync(@Query("id") id: Int): Response<JsonObject>

    @POST("reg")
    suspend fun registerUser()

}