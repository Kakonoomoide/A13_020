package com.example.eventorganizer.ui.pages.peserta.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.repository.ParticipantsRepository
import kotlinx.coroutines.launch

class InsertParticipantViewModel(
    private val prcp: ParticipantsRepository
): ViewModel() {
    var uiState by mutableStateOf(ParticipantsInsertUiState())
        private set

    fun updateInsertMhsState(participantsInsertUiEvent: ParticipantsInsertUiEvent) {
        uiState = ParticipantsInsertUiState(participantsInsertUiEvent = participantsInsertUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                prcp.insertParticipants(uiState.participantsInsertUiEvent.toPrcp())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class ParticipantsInsertUiState(
    val participantsInsertUiEvent: ParticipantsInsertUiEvent = ParticipantsInsertUiEvent()
)

data class ParticipantsInsertUiEvent(
    val idPeserta: Int = 0,
    val namaPeserta: String = "",
    val email: String = "",
    val nomorTelepon: String = ""
)

fun ParticipantsInsertUiEvent.toPrcp(): Participant = Participant(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email, 
    nomorTelepon = nomorTelepon
)

fun Participant.toUiStatePrcp(): ParticipantsInsertUiState = ParticipantsInsertUiState(
    participantsInsertUiEvent = toPrcpInsertUiEvent()
)

fun Participant.toPrcpInsertUiEvent(): ParticipantsInsertUiEvent = ParticipantsInsertUiEvent(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)