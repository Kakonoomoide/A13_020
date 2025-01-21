package com.example.eventorganizer.ui.pages.tiket.viewmodel

import android.provider.ContactsContract.Intents.Insert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.repository.TicketsRepository
import kotlinx.coroutines.launch

class InsertTicketsViewModel(private val tkt:TicketsRepository): ViewModel(){
    var uiState by mutableStateOf(InsertTicketsUiState())
        private set

    fun updateInsertMhsState(insertTicketsUiEvent: InsertTicketsUiEvent) {
        uiState = InsertTicketsUiState(insertTicketsUiEvent = insertTicketsUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                tkt.insertTickets(uiState.insertTicketsUiEvent.toTkt())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTicketsUiState(
    val insertTicketsUiEvent: InsertTicketsUiEvent = InsertTicketsUiEvent()
)

data class InsertTicketsUiEvent(
    val idTiket: Int = 0,
    val idEvent: Int = 0,
    val idPengguna: Int = 0,
    val kapasitasTiket: Int = 0,
    val hargaTiket: Int = 0,
    val namaEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = "",
    val namaPeserta: String = ""
)

fun InsertTicketsUiEvent.toTkt(): Tickets = Tickets(
    idTiket = idTiket,
    idEvent = idEvent,
    idPengguna = idPengguna,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket,
    namaEvent = namaEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent,
    namaPeserta = namaPeserta
)

fun Tickets.toUiStateTkt(): InsertTicketsUiState = InsertTicketsUiState(
    insertTicketsUiEvent = toInsertTicketsUiEvent()
)

fun Tickets.toInsertTicketsUiEvent(): InsertTicketsUiEvent = InsertTicketsUiEvent(
    idTiket = idTiket,
    idEvent = idEvent,
    idPengguna = idPengguna,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket,
    namaEvent = namaEvent,
    tanggalEvent = tanggalEvent,
    lokasiEvent = lokasiEvent,
    namaPeserta = namaPeserta
)