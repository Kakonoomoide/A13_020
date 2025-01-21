package com.example.eventorganizer.dependeciesinjection

import com.example.eventorganizer.repository.EventsApiHandler
import com.example.eventorganizer.repository.EventsRepository
import com.example.eventorganizer.repository.NetworkTicketsRepository
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.service_api.EventsService
import com.example.eventorganizer.service_api.TicketsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppEventsContainer {
    val eventsRepository: EventsRepository
    val ticketsRepository: TicketsRepository
}

class EventContainerApp : AppEventsContainer {
    // Base URL untuk endpoint
    private val eventsBaseUrl = "http://10.0.2.2:3000/api/event/"
    private val ticketsBaseUrl = "http://10.0.2.2:3000/api/tiket/"
    private val json = Json { ignoreUnknownKeys = true }

    // Retrofit instance untuk komunikasi dengan API
    private val retrofitEvents: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(eventsBaseUrl).build()

    private val retrofitTickets: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(ticketsBaseUrl).build()

    // Layanan API untuk events
    private val eventsService: EventsService by lazy {
        retrofitEvents.create(EventsService::class.java)
    }

    private val ticketsService: TicketsService by lazy {
        retrofitTickets.create(TicketsService::class.java)
    }

    // Repository untuk events
    override val eventsRepository: EventsRepository by lazy {
        EventsApiHandler(eventsService)
    }

    override val ticketsRepository: TicketsRepository by lazy {
        NetworkTicketsRepository(ticketsService)
    }
}
