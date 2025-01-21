package com.example.eventorganizer.ui.pages.peserta.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.repository.ParticipantsRepository
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiDetailParticipants
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class ParticipantsDetailUiState {
    data class Success(val prcp: Participant) : ParticipantsDetailUiState()
    object Error : ParticipantsDetailUiState()
    object Loading : ParticipantsDetailUiState()
}

class DetailParticipantsViewModel(
    savedStateHandle: SavedStateHandle,
    private val prcp: ParticipantsRepository
) : ViewModel() {

    var participantsDetailState: ParticipantsDetailUiState by mutableStateOf(ParticipantsDetailUiState.Loading)
        private set

    private val _idPeserta: Int = checkNotNull(savedStateHandle[DestinasiDetailParticipants.IdParticipants])

    init {
        getParticipantsbyId()
    }

    fun getParticipantsbyId() {
        viewModelScope.launch {
            participantsDetailState = ParticipantsDetailUiState.Loading
            participantsDetailState = try {
                val events = prcp.getParticipantsById(_idPeserta)
                ParticipantsDetailUiState.Success(events)
            } catch (e: IOException) {
                ParticipantsDetailUiState.Error
            } catch (e: HttpException) {
                ParticipantsDetailUiState.Error
            }
        }
    }
}