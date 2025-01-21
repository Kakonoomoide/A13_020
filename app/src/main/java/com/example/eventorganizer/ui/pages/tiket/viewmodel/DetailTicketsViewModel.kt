package com.example.eventorganizer.ui.pages.tiket.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiDetailTickets
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailTicketsUiState {
    data class Success(val tickets: Tickets) : DetailTicketsUiState()
    object Error : DetailTicketsUiState()
    object Loading : DetailTicketsUiState()
}

class DetailTicketsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tkt : TicketsRepository
) : ViewModel() {

    var TicketsDetailState: DetailTicketsUiState by mutableStateOf(DetailTicketsUiState.Loading)
        private set

    private val _idTiket: Int = checkNotNull(savedStateHandle[DestinasiDetailTickets.IdTickets])

    init {
        getTicketsbyId()
    }

    fun getTicketsbyId() {
        viewModelScope.launch {
            TicketsDetailState = DetailTicketsUiState.Loading
            TicketsDetailState = try {
                val tickets = tkt.getTicketsById(_idTiket)
                Log.d("TicketDetails", "Response: $tickets")
                DetailTicketsUiState.Success(tickets)
            } catch (e: IOException) {
                DetailTicketsUiState.Error
            } catch (e: HttpException) {
                DetailTicketsUiState.Error
            }
        }
    }
}