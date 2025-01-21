package com.example.eventorganizer.ui.pages.peserta.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.repository.ParticipantsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class ParticipantsHomeUiState {
    data class Success(val participants: List<Participant>) : ParticipantsHomeUiState()
    object Error : ParticipantsHomeUiState()
    object Loading : ParticipantsHomeUiState()
}


class HomeParticipantViewModel(
    private val prcp: ParticipantsRepository
): ViewModel() {
    var prcpUiState: ParticipantsHomeUiState by mutableStateOf(ParticipantsHomeUiState.Loading)
        private set

    init {
        getPrcp()
    }

    fun getPrcp(){
        viewModelScope.launch {
            prcpUiState = ParticipantsHomeUiState.Loading
            prcpUiState = try {
                ParticipantsHomeUiState.Success(prcp.getAllParticipants().data)
            }catch (e: IOException) {
                ParticipantsHomeUiState.Error
            } catch (e: HttpException) {
                ParticipantsHomeUiState.Error
            }
        }
    }

    fun deletePrcp(idPeserta : Int){
        viewModelScope.launch {
            try {
                prcp.deleteParticipants(idPeserta)
            } catch (e: IOException) {
                ParticipantsHomeUiState.Error
            } catch (e: HttpException) {
                ParticipantsHomeUiState.Error
            }
        }
    }
}