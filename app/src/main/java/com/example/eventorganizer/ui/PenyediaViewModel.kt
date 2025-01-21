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
import com.example.eventorganizer.ui.pages.peserta.viewmodel.DetailParticipantsViewModel
import com.example.eventorganizer.ui.pages.peserta.viewmodel.HomeParticipantViewModel
import com.example.eventorganizer.ui.pages.peserta.viewmodel.InsertParticipantViewModel
import com.example.eventorganizer.ui.pages.peserta.viewmodel.UpdateParticipantViewModel
import com.example.eventorganizer.ui.pages.tiket.viewmodel.DetailTicketsViewModel
import com.example.eventorganizer.ui.pages.tiket.viewmodel.HomeTicketsViewModel
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsViewModel
import com.example.eventorganizer.ui.pages.tiket.viewmodel.UpdateTicketsViewModel

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

        // Tickets
        // home viewmodel
        initializer {
            HomeTicketsViewModel(
                aplikasiEO()
                    .container
                    .ticketsRepository
            )
        }
        // insert viewmodel
        initializer {
            InsertTicketsViewModel(
                aplikasiEO()
                    .container
                    .ticketsRepository
            )
        }
        // detail viewmodel
        initializer {
            DetailTicketsViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .ticketsRepository
            )
        }
        // update viewmodel
        initializer {
            UpdateTicketsViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .ticketsRepository
            )
        }

        // Participants
        // home viewmodel
        initializer {
            HomeParticipantViewModel(
                aplikasiEO()
                    .container
                    .participantsRepository
            )
        }
        // insert viewmodel
        initializer {
            InsertParticipantViewModel(
                aplikasiEO()
                    .container
                    .participantsRepository
            )
        }
        // detail viewmodel
        initializer {
            DetailParticipantsViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .participantsRepository
            )
        }
        // update viewmodel
        initializer {
            UpdateParticipantViewModel(
                createSavedStateHandle(),
                aplikasiEO()
                    .container
                    .participantsRepository
            )
        }
    }
}

fun CreationExtras.aplikasiEO(): EventsApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventsApplications)