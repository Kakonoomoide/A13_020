package com.example.eventorganizer.ui.pages.tiket.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.repository.TicketsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeTicketsUiState {
    data class Success(val tickets: List<Tickets>) : HomeTicketsUiState()
    object Error : HomeTicketsUiState()
    object Loading : HomeTicketsUiState()
}

class HomeTicketsViewModel (
    private val tkt: TicketsRepository
) : ViewModel() {
    var tktUiState: HomeTicketsUiState by mutableStateOf(HomeTicketsUiState.Loading)
        private set

    init {
        getTkt()
    }

    fun getTkt() {
        viewModelScope.launch {
            tktUiState = HomeTicketsUiState.Loading
            tktUiState = try {
                HomeTicketsUiState.Success(tkt.getAllTickets().data)
            } catch (e: IOException) {
                HomeTicketsUiState.Error
            } catch (e: HttpException) {
                HomeTicketsUiState.Error
            }
        }
    }

    fun deleteTkt(IdTickets: Int) {
        viewModelScope.launch {
            try {
                tkt.deleteTickets(IdTickets)
            } catch (e: IOException) {
                HomeTicketsUiState.Error
            } catch (e: HttpException) {
                HomeTicketsUiState.Error
            }
        }
    }
}