package com.example.eventorganizer.ui.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.repository.EventsRepository
import com.example.eventorganizer.ui.event.view.DestinasiUpdate
import kotlinx.coroutines.launch

class EventsUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val evnt : EventsRepository
): ViewModel() {
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _idEvent: Int = checkNotNull(savedStateHandle[DestinasiUpdate.IdEvent])

    init {
        viewModelScope.launch {
            updateUiState = evnt.getEventsById(_idEvent).toUiStateEvnt()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateEvent(){
        viewModelScope.launch {
            try {
                evnt.updateEvents(_idEvent, updateUiState.insertUiEvent.toEvnt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}