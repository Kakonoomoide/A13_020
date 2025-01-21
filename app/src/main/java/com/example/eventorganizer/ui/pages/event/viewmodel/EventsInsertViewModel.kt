package com.example.eventorganizer.ui.pages.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.repository.EventsRepository
import kotlinx.coroutines.launch


class EventsInsertViewModel(
    private val evnt:EventsRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                evnt.insertEvents(uiState.insertUiEvent.toEvnt())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idEvent: Int = 0,
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = ""
)

fun InsertUiEvent.toEvnt(): Events = Events(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)

fun Events.toUiStateEvnt(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Events.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent
)