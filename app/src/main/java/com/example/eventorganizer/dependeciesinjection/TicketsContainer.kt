package com.example.eventorganizer.dependeciesinjection

import com.example.eventorganizer.repository.NetworkTicketsRepository
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.service_api.TicketsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val ticketsRepository: TicketsRepository
}

class MahasiswaContainer: AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/event/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val ticketsService: TicketsService by lazy {
        retrofit.create(TicketsService::class.java)
    }

    override val ticketsRepository: TicketsRepository by lazy {
        NetworkTicketsRepository(ticketsService)
    }
}