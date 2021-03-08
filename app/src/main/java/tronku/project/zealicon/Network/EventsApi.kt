package tronku.project.zealicon.Network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface EventsApi {

    @GET("https://dheerajkotwani.github.io/custom-apis/zealicon/api/events.json")
    suspend fun getEventsAsync(): Response<JsonObject>

    @Headers("device:android")
    @POST("api/v1/reg")
    suspend fun registerUser(@Body map: HashMap<String, String>): Response<JsonObject>

    @GET("api/v1/reg/search")
    suspend fun searchUser(@Query("tag") query: String): Response<JsonObject>

    @GET("http://backoffice.zealicon.in/api/event/participate")
    suspend fun registerForEvent(@Query("name") name: String, @Query("email") email: String,
                                 @Query("event_id") id: Int, @Query("zeal_id") zealId: String,
                                 @Query("contact_no") contactNo: String): Response<JsonObject>
}