package com.example.eventorganizer.ui.pages.event.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.repository.EventsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed class HomeUiState {
    data class Success(val events: List<Events>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}


class EventsHomeViewModel(
    private val evnt: EventsRepository
): ViewModel() {
    var evntUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getEvnt()
    }

    fun getEvnt(){
        viewModelScope.launch {
            evntUiState = HomeUiState.Loading
            evntUiState = try {
                HomeUiState.Success(evnt.getAllEvents().data)
            }catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteEvnt(idEvent : Int){
        viewModelScope.launch {
            try {
                evnt.deleteEvents(idEvent)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}