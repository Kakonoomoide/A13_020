package com.example.eventorganizer.service_api

import com.example.eventorganizer.model.AllEventsResponse
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.model.EventsDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventsService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // post data
    @POST("add")
    suspend fun insertEvents(@Body event: Events)

    // get data
    @GET(".")
    suspend fun getAllEvents(): AllEventsResponse

    // get data by id
    @GET("{id_event}")
    suspend fun getEventsById(@Path("id_event") idEvent: Int): EventsDetailResponse

    // update data by id
    @PUT("{id_event}")
    suspend fun updateEvents(@Path("id_event") idEvent: Int, @Body event: Events)

    // delete data by id
    @DELETE("{id_event}")
    suspend fun deleteEvents(@Path("id_event") idEvent: Int): Response<Void>
}