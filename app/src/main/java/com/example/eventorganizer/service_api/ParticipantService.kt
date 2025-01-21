package com.example.eventorganizer.service_api

import com.example.eventorganizer.model.AllParticipantResponse
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.model.ParticipantDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ParticipantService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // post data
    @POST("add")
    suspend fun insertParticipants(@Body participant: Participant)

    // get data
    @GET(".")
    suspend fun getAllParticipants(): AllParticipantResponse

    // get data by id
    @GET("{id_transaksi}")
    suspend fun getParticipantsById(@Path("id_transaksi") idPeserta: Int): ParticipantDetailResponse

    // update data by id
    @PUT("{id_transaksi}")
    suspend fun updateParticipants(@Path("id_transaksi") idPeserta: Int, @Body participant: Participant)

    // delete data by id
    @DELETE("{id_transaksi}")
    suspend fun deleteParticipants(@Path("id_transaksi") idPeserta: Int): Response<Void>
}