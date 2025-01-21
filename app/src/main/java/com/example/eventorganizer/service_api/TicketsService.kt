package com.example.eventorganizer.service_api

import com.example.eventorganizer.model.AllTicketsResponse
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.model.TicketsDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketsService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    
    // post data
    @POST("add")
    suspend fun insertTickets(@Body tickets: Tickets)

    // get data
    @GET(".")
    suspend fun getAllTickets(): AllTicketsResponse

    // get data by id
    @GET("{id_tiket}")
    suspend fun getTicketsById(@Path("id_tiket") IdTickets: Int): TicketsDetailResponse

    // update data by id
    @PUT("{id_tiket}")
    suspend fun updateTickets(@Path("id_tiket") IdTickets: Int, @Body tickets: Tickets)

    // delete data by id
    @DELETE("{id_tiket}")
    suspend fun deleteTickets(@Path("id_tiket") IdTickets: Int): Response<Void>
}