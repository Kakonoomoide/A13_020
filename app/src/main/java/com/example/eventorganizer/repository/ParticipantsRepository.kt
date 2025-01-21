package com.example.eventorganizer.repository

import com.example.eventorganizer.model.AllParticipantResponse
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.service_api.ParticipantsService
import okio.IOException


interface ParticipantsRepository{
    suspend fun insertParticipants(participant: Participant)

    suspend fun getAllParticipants(): AllParticipantResponse

    suspend fun updateParticipants(idPeserta: Int, participant: Participant)

    suspend fun deleteParticipants(idPeserta: Int)

    suspend fun getParticipantsById(idPeserta: Int): Participant
}

class ParticipantsApiHandler(
    private val participantsApiService: ParticipantsService
): ParticipantsRepository {
    override suspend fun insertParticipants(participant: Participant) {
        participantsApiService.insertParticipants(participant)
    }

    override suspend fun getAllParticipants(): AllParticipantResponse {
        return participantsApiService.getAllParticipants()
    }

    override suspend fun updateParticipants(idPeserta: Int, participant: Participant) {
        participantsApiService.updateParticipants(idPeserta,participant)
    }

    override suspend fun deleteParticipants(idPeserta: Int) {
        try {
            val response = participantsApiService.deleteParticipants(idPeserta)
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

    override suspend fun getParticipantsById(idPeserta: Int): Participant {
        return participantsApiService.getParticipantsById(idPeserta).data
    }
}