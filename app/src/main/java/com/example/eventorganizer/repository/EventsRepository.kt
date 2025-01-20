package com.example.eventorganizer.repository

import com.example.eventorganizer.model.AllEventsResponse
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.service_api.EventsService
import okio.IOException

interface EventsRepository{
    suspend fun insertEvents(events: Events)

    suspend fun getAllEvents(): AllEventsResponse

    suspend fun updateEvents(idEvent: String, events: Events)

    suspend fun deleteEvents(idEvent: String)

    suspend fun getEventsById(idEvent: String): Events
}

class EventsApiHandler(
    private val eventsApiService: EventsService
): EventsRepository {
    override suspend fun insertEvents(events: Events) {
        eventsApiService.insertEvents(events)
    }

    override suspend fun getAllEvents(): AllEventsResponse {
        return eventsApiService.getAllEvents()
    }

    override suspend fun updateEvents(idEvent: String, events: Events) {
        eventsApiService.updateEvents(idEvent, events)
    }

    override suspend fun deleteEvents(idEvent: String) {
        try {
            val response = eventsApiService.deleteEvents(idEvent)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:" + "${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getEventsById(idEvent: String): Events {
        return eventsApiService.getEventsById(idEvent).data
    }

}