package com.example.eventorganizer.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventorganizer.EventsApplications
import com.example.eventorganizer.ui.pages.event.viewmodel.EventsDetailViewModel
import com.example.eventorganizer.ui.pages.event.viewmodel.EventsHomeViewModel
import com.example.eventorganizer.ui.pages.event.viewmodel.EventsInsertViewModel
import com.example.eventorganizer.ui.pages.event.viewmodel.EventsUpdateViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // events
        // home viewmodel
        initializer {
            EventsHomeViewModel(
                aplikasiEO()
                    .container
                    .eventsRepository
            )
        }
        // insert viewmodel
        initializer {
            EventsInsertViewModel(
                aplikasiEO()
                    .container
                    .eventsRepository
            )
        }
        // detail viewmodel
        initializer {
            EventsDetailViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .eventsRepository
            )
        }
        // update viewmodel
        initializer {
            EventsUpdateViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .eventsRepository
            )
        }
    }
}

fun CreationExtras.aplikasiEO(): EventsApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventsApplications)