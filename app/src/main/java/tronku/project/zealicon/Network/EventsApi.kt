package tronku.project.zealicon.Network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tronku.project.zealicon.Model.EventTrack

interface EventsApi {

    @GET("event")
    suspend fun getEventsAsync(@Query("id") string: String): Response<String>

    @POST("reg")
    suspend fun registerUser()

}