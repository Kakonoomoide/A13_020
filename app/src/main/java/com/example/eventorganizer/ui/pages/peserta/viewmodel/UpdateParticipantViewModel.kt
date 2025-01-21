package com.example.eventorganizer.ui.pages.peserta.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.repository.ParticipantsRepository
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiUpdateParticipants
import kotlinx.coroutines.launch

class UpdateParticipantViewModel(
    savedStateHandle: SavedStateHandle,
    private val prcp : ParticipantsRepository
): ViewModel() {
    var participantsUpdateUiState by mutableStateOf(ParticipantsInsertUiState())
        private set

    private val _idPeserta: Int = checkNotNull(savedStateHandle[DestinasiUpdateParticipants.IdParticipants])

    init {
        viewModelScope.launch {
            participantsUpdateUiState = prcp
                .getParticipantsById(_idPeserta)
                .toUiStatePrcp()
        }
    }

    fun updateInsertPrcpState(participantsInsertUiEvent: ParticipantsInsertUiEvent){
        participantsUpdateUiState = ParticipantsInsertUiState(
            participantsInsertUiEvent = participantsInsertUiEvent
        )
    }

    suspend fun updatePrcp(){
        viewModelScope.launch {
            try {
                prcp.updateParticipants(
                    _idPeserta,
                    participantsUpdateUiState.participantsInsertUiEvent.toPrcp()
                )
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}