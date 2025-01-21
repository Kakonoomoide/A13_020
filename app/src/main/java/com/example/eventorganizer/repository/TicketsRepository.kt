package com.example.eventorganizer.repository

import com.example.eventorganizer.model.AllTicketsResponse
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.service_api.TicketsService
import okio.IOException

interface TicketsRepository {
    suspend fun insertTickets(tickets: Tickets)

    suspend fun getAllTickets(): AllTicketsResponse

    suspend fun updateTickets(IdTickets: Int, tickets: Tickets)

    suspend fun deleteTickets(IdTickets: Int)

    suspend fun getTicketsById(IdTickets: Int): Tickets
}

class NetworkTicketsRepository(
    private val ticketsApiService: TicketsService
): TicketsRepository {
    override suspend fun insertTickets(tickets: Tickets) {
        ticketsApiService.insertTickets(tickets)
    }

    override suspend fun getAllTickets(): AllTicketsResponse {
        return ticketsApiService.getAllTickets()
    }

    override suspend fun updateTickets(IdTickets: Int, tickets: Tickets) {
        ticketsApiService.updateTickets(IdTickets, tickets)
    }

    override suspend fun deleteTickets(IdTickets: Int) {
        try {
            val response = ticketsApiService.deleteTickets(IdTickets)
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

    override suspend fun getTicketsById(IdTickets: Int): Tickets {
        return ticketsApiService.getTicketsById(IdTickets).data
    }
}