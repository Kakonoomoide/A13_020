package com.example.eventorganizer.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventorganizer.EventsApplications
import com.example.eventorganizer.ui.event.viewmodel.EventsHomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // home viewmodel
        initializer {
            EventsHomeViewModel(
                aplikasiEO()
                    .container
                    .eventsRepository
            )
        }
    }
}

fun CreationExtras.aplikasiEO(): EventsApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventsApplications)