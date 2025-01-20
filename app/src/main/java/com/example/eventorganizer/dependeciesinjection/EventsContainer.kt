package com.example.eventorganizer.dependeciesinjection

import com.example.eventorganizer.repository.EventsApiHandler
import com.example.eventorganizer.repository.EventsRepository
import com.example.eventorganizer.service_api.EventsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppEventsContainer{
    val eventsRepository : EventsRepository
}

class EventsContainer: AppEventsContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/event/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val eventsService: EventsService by lazy {
        retrofit.create(EventsService::class.java)
    }

    override val eventsRepository: EventsRepository by lazy {
        EventsApiHandler(eventsService)
    }
}