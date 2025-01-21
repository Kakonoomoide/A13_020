package com.example.eventorganizer.ui.pages.tiket.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiUpdateTickets
import kotlinx.coroutines.launch

class UpdateTicketsViewModel (
    savedStateHandle: SavedStateHandle,
    private val tkt: TicketsRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertTicketsUiState())
        private set

    private val _idTiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTickets.IdEvent])

    init {
        viewModelScope.launch {
            updateUiState = tkt.getTicketsById(_idTiket)
                .toUiStateTkt()
        }
    }

    fun updateInsertTktState(insertTicketsUiEvent: InsertTicketsUiEvent){
        updateUiState = InsertTicketsUiState(insertTicketsUiEvent = insertTicketsUiEvent)
    }

    suspend fun updateTkt(){
        viewModelScope.launch {
            try {
                tkt.updateTickets(_idTiket, updateUiState.insertTicketsUiEvent.toTkt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}