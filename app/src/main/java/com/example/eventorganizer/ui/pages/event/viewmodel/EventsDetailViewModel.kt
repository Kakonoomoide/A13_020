package com.example.eventorganizer.ui.pages.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.repository.EventsRepository
import com.example.eventorganizer.ui.pages.event.view.DestinasiDetail
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailUiState {
    data class Success(val events: Events) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class EventsDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventsRepository
) : ViewModel() {

    var mahasiswaDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idEvent: Int = checkNotNull(savedStateHandle[DestinasiDetail.IdEvent])

    init {
        getEventbyIdEvent()
    }

    fun getEventbyIdEvent() {
        viewModelScope.launch {
            mahasiswaDetailState = DetailUiState.Loading
            mahasiswaDetailState = try {
                val events = evnt.getEventsById(_idEvent)
                DetailUiState.Success(events)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}