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

sealed class HomeUiState {
    data class Success(val tickets: List<Tickets>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeTicketsViewModel (
    private val tkt: TicketsRepository
) : ViewModel() {
    var tktUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTkt()
    }

    private fun getTkt() {
        viewModelScope.launch {
            tktUiState = HomeUiState.Loading
            tktUiState = try {
                HomeUiState.Success(tkt.getAllTickets().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteTkt(IdTickets: Int) {
        viewModelScope.launch {
            try {
                tkt.deleteTickets(IdTickets)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}