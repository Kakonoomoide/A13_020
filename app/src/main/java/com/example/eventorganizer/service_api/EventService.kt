package com.example.eventorganizer.service_api

import com.example.eventorganizer.model.AllEventResponse
import com.example.eventorganizer.model.Event
import com.example.eventorganizer.model.EventDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // post data
    @POST("add")
    suspend fun insertEvent(@Body event: Event)

    // get data
    @GET(".")
    suspend fun getAllEvent(): AllEventResponse

    // get data by id
    @GET("{id_event}")
    suspend fun getEventById(@Path("id_event") idEvent: String): EventDetailResponse

    // update data by id
    @PUT("{id_event}")
    suspend fun updateEvent(@Path("id_event") idEvent: String, @Body event: Event)

    // delete data by id
    @DELETE("{id_event}")
    suspend fun deleteEvent(@Path("id_event") idEvent: String): Response<Void>
}