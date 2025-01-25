package com.example.eventorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventorganizer.dependeciesinjection.EventContainerApp
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.ui.home.view.DestinasiHomePage
import com.example.eventorganizer.ui.home.view.HomePageView
import com.example.eventorganizer.ui.pages.event.view.DestinasiDetail
import com.example.eventorganizer.ui.pages.event.view.DestinasiEntry
import com.example.eventorganizer.ui.pages.event.view.DestinasiHomeEvents
import com.example.eventorganizer.ui.pages.event.view.DestinasiUpdate
import com.example.eventorganizer.ui.pages.event.view.EventsDetailView
import com.example.eventorganizer.ui.pages.event.view.EventsHomeView
import com.example.eventorganizer.ui.pages.event.view.EventsInsertView
import com.example.eventorganizer.ui.pages.event.view.EventsUpdateView
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiDetailParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiEntryParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiHomeParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiUpdateParticipants
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsDetailView
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsHomeView
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsInsertView
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsUpdateView
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiDetailTickets
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiEntryTiket
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiHomeTiket
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiUpdateTickets
import com.example.eventorganizer.ui.pages.tiket.view.DetailTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.HomeTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.InsertTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.UpdateTicketsView
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiDetailTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiEntryTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiHomeTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DetailTransactionsView
import com.example.eventorganizer.ui.pages.transaksi.view.HomeTransactionsView
import com.example.eventorganizer.ui.pages.transaksi.view.InsertTransactionsView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePage.route,
    ) {
        // Home Page
        composable(DestinasiHomePage.route) {
            HomePageView(
                eventsPageBttn = { navController.navigate(DestinasiHomeEvents.route) },
                participantPageBttn = { navController.navigate(DestinasiHomeParticipants.route) },
                ticketPageBttn = { navController.navigate(DestinasiHomeTiket.route) },
                transactionPageBttn = { navController.navigate(DestinasiHomeTransactions.route) }
            )
        }

        // Events
        addEventsNavigation(navController)

        // Participants
        addParticipantsNavigation(navController)

        // Tickets
        addTicketsNavigation(navController)

        // Transactions
        addTransactionsNavigation(navController)
    }
}

// Navigasi untuk Events
private fun NavGraphBuilder.addEventsNavigation(navController: NavHostController) {
    composable(DestinasiHomeEvents.route) {
        EventsHomeView(
            navigateToltemEntry = { navController.navigate(DestinasiEntry.route) },
            onDetailClick = { idEvent ->
                navController.navigate("${DestinasiDetail.route}/$idEvent")
            }
        )
    }
    composable(DestinasiEntry.route) {
        EventsInsertView(navigateBack = {
            navController.navigate(DestinasiHomeEvents.route) {
                popUpTo(DestinasiHomeEvents.route) { inclusive = true }
            }
        })
    }
    composable(
        DestinasiDetail.routesWithArg,
        arguments = listOf(navArgument(DestinasiDetail.IdEvent) { type = NavType.IntType })
    ) {
        val idEvent = it.arguments?.getInt(DestinasiDetail.IdEvent)
        idEvent?.let { id ->
            EventsDetailView(
                navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$id") },
                navigateBack = {
                    navController.navigate(DestinasiHomeEvents.route) {
                        popUpTo(DestinasiHomeEvents.route) { inclusive = true }
                    }
                }
            )
        }
    }
    composable(
        DestinasiUpdate.routesWithArg,
        arguments = listOf(navArgument(DestinasiUpdate.IdEvent) { type = NavType.IntType })
    ) {
        val idEvent = it.arguments?.getInt(DestinasiUpdate.IdEvent)
        idEvent?.let {
            EventsUpdateView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
    }
}

// Navigasi untuk Participants
private fun NavGraphBuilder.addParticipantsNavigation(navController: NavHostController) {
    composable(DestinasiHomeParticipants.route) {
        ParticipantsHomeView(
            navigateToltemEntry = { navController.navigate(DestinasiEntryParticipants.route) },
            onDetailClick = { idPeserta ->
                navController.navigate("${DestinasiDetailParticipants.route}/$idPeserta")
            }
        )
    }
    composable(DestinasiEntryParticipants.route) {
        ParticipantsInsertView(navigateBack = {
            navController.navigate(DestinasiHomeParticipants.route) {
                popUpTo(DestinasiHomeParticipants.route) { inclusive = true }
            }
        })
    }
    composable(
        DestinasiDetailParticipants.routesWithArg,
        arguments = listOf(navArgument(DestinasiDetailParticipants.IdParticipants) { type = NavType.IntType })
    ) {
        val idPeserta = it.arguments?.getInt(DestinasiDetailParticipants.IdParticipants)
        idPeserta?.let {
            ParticipantsDetailView(
                navigateToItemUpdate = { navController.navigate("${DestinasiUpdateParticipants.route}/$it") },
                navigateBack = {
                    navController.navigate(DestinasiHomeParticipants.route) {
                        popUpTo(DestinasiHomeParticipants.route) { inclusive = true }
                    }
                }
            )
        }
    }
    composable(
        DestinasiUpdateParticipants.routesWithArg,
        arguments = listOf(navArgument(DestinasiUpdateParticipants.IdParticipants) { type = NavType.IntType })
    ) {
        val idPeserta = it.arguments?.getInt(DestinasiUpdateParticipants.IdParticipants)
        idPeserta?.let {
            ParticipantsUpdateView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }
    }
}

// Navigasi untuk Tickets
private fun NavGraphBuilder.addTicketsNavigation(navController: NavHostController) {
    val eventContainerApp = EventContainerApp()
    composable(DestinasiHomeTiket.route) {
        var ticketsData by remember { mutableStateOf<List<Tickets>>(emptyList()) }
        var transactionsData by remember { mutableStateOf<List<Transactions>>(emptyList()) }

        LaunchedEffect(Unit) {
            ticketsData = eventContainerApp.ticketsRepository.getAllTickets().data
            transactionsData = eventContainerApp.transactionsRepository.getAllTransactions().data
        }

        HomeTicketsView(
            navigateToltemEntry = { navController.navigate(DestinasiEntryTiket.route) },
            onDetailClick = { idTiket ->
                navController.navigate("${DestinasiDetailTickets.route}/$idTiket")
            },
            ticketsData = ticketsData,
            transactionsData = transactionsData
        )
    }
    composable(DestinasiEntryTiket.route) {
        InsertTicketsView(
            eventsRepo = eventContainerApp.eventsRepository,
            participantsRepo = eventContainerApp.participantsRepository,
            navigateBack = {
                navController.navigate(DestinasiHomeTiket.route) {
                    popUpTo(DestinasiHomeTiket.route) { inclusive = true }
            }
        })
    }

    composable(
        DestinasiDetailTickets.routesWithArg,
        arguments = listOf(navArgument(DestinasiDetailTickets.IdTickets) { type = NavType.IntType })
    ) {
        val idTiket = it.arguments?.getInt(DestinasiDetailTickets.IdTickets)
        idTiket?.let {
            DetailTicketsView(
                navigateToItemUpdate = { navController.navigate("${DestinasiUpdateTickets.route}/$it") },
                navigateBack = {
                    navController.navigate(DestinasiHomeTiket.route) {
                        popUpTo(DestinasiHomeTiket.route) { inclusive = true }
                    }
                }
            )
        }
    }
    composable(
        DestinasiUpdateTickets.routesWithArg,
        arguments = listOf(navArgument(DestinasiUpdateTickets.IdTickets) { type = NavType.IntType })
    ) {
        val idTiket = it.arguments?.getInt(DestinasiUpdateTickets.IdTickets)
        idTiket?.let {
            UpdateTicketsView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                eventsRepo = eventContainerApp.eventsRepository,
                participantsRepo = eventContainerApp.participantsRepository,
            )
        }
    }
}

// Navigasi untuk Transactions
private fun NavGraphBuilder.addTransactionsNavigation(navController: NavHostController) {
    val eventContainerApp = EventContainerApp()
    composable(DestinasiHomeTransactions.route) {
        HomeTransactionsView(
            navigateToltemEntry = { navController.navigate(DestinasiEntryTransactions.route) },
            onDetailClick = { idTransaksi ->
                navController.navigate("${DestinasiDetailTransactions.route}/$idTransaksi")
            }
        )
    }
    composable(DestinasiEntryTransactions.route) {
        InsertTransactionsView(
            ticketsRepository = eventContainerApp.ticketsRepository,
            navigateBack = {
                navController.navigate(DestinasiHomeTransactions.route) {
                    popUpTo(DestinasiHomeTransactions.route) { inclusive = true }
            }
        })
    }
    composable(
        DestinasiDetailTransactions.routesWithArg,
        arguments = listOf(navArgument(DestinasiDetailTransactions.IdTransactions) { type = NavType.IntType })
    ) {
        val idTransaksi = it.arguments?.getInt(DestinasiDetailTransactions.IdTransactions)
        idTransaksi?.let {
            DetailTransactionsView(
                navigateBack = {
                    navController.navigate(DestinasiHomeTransactions.route) {
                        popUpTo(DestinasiHomeTransactions.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
