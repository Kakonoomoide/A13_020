package com.example.eventorganizer.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        // home page
        composable(
            route = DestinasiHomePage.route
        ) {
            HomePageView(
                eventsPageBttn = {
                    navController.navigate(DestinasiHomeEvents.route)
                },
                participantPageBttn = {
                    navController.navigate(DestinasiHomeParticipants.route)
                },
                ticketPageBttn = {
                    navController.navigate(DestinasiHomeTiket.route)
                },
                transactionPageBttn = {
                    navController.navigate(DestinasiHomeTransactions.route)
                }
            )
        }

        //events
        // home
        composable(DestinasiHomeEvents.route){
            EventsHomeView(
                navigateToltemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { idEvent ->
                    navController.navigate("${DestinasiDetail.route}/$idEvent")
                }
            )
        }

        // insert
        composable(DestinasiEntry.route) {
            EventsInsertView(navigateBack = {
                navController.navigate(DestinasiHomeEvents.route){
                    popUpTo(DestinasiHomeEvents.route){
                        inclusive = true
                    }
                }
            })
        }

        // detail
        composable(
            DestinasiDetail.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetail.IdEvent) {
                    type = NavType.IntType }
            )){
            val idEvent = it.arguments?.getInt(DestinasiDetail.IdEvent)
            idEvent?.let { idEvent ->
                EventsDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdate.route}/$idEvent") },
                    navigateBack = { navController.navigate(DestinasiHomeEvents.route) {
                        popUpTo(DestinasiHomeEvents.route) { inclusive = true }
                    }}
                )
            }
        }

        // update
        composable(
            DestinasiUpdate.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetail.IdEvent){
                    type = NavType.IntType }
            )){
            val idEvent = it.arguments?.getInt(DestinasiUpdate.IdEvent)
            idEvent?.let { idEvents ->
                EventsUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }


        //participants
        // home
        composable(DestinasiHomeParticipants.route){
            ParticipantsHomeView(
                navigateToltemEntry = { navController.navigate(DestinasiEntryParticipants.route) },
                onDetailClick = { idPeserta ->
                    Log.d("ParticipantsHome", "ID Peserta: $idPeserta")
                    navController.navigate("${DestinasiDetailParticipants.route}/$idPeserta")
                }
            )
        }

        // insert
        composable(DestinasiEntryParticipants.route) {
            ParticipantsInsertView(navigateBack = {
                navController.navigate(DestinasiHomeParticipants.route){
                    popUpTo(DestinasiHomeParticipants.route){
                        inclusive = true
                    }
                }
            })
        }

        // detail
        composable(
            DestinasiDetailParticipants.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetailParticipants.IdParticipants) {
                    type = NavType.IntType }
            )){
            val idPrcp = it.arguments?.getInt(DestinasiDetailParticipants.IdParticipants)
            idPrcp?.let { idPeserta ->
                ParticipantsDetailView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateParticipants.route}/$idPeserta") },
                    navigateBack = { navController.navigate(DestinasiHomeParticipants.route) {
                        popUpTo(DestinasiHomeParticipants.route) {
                            inclusive = true
                        }
                    }}
                )
            }
        }

        // update
        composable(
            DestinasiUpdateParticipants.routesWithArg, arguments = listOf(
                navArgument(DestinasiUpdateParticipants.IdParticipants){
                    type = NavType.IntType }
            )){
            val idPrcp = it.arguments?.getInt(DestinasiUpdateParticipants.IdParticipants)
            idPrcp?.let { idPeserta ->
                ParticipantsUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // tickets
        // home
        composable(DestinasiHomeTiket.route){
            HomeTicketsView(
                navigateToltemEntry = { navController.navigate(DestinasiEntryTiket.route) },
                onDetailClick = { idTiket ->
                    navController.navigate("${DestinasiDetailTickets.route}/$idTiket")
                }
            )
        }

        // insert
        composable(DestinasiEntryTiket.route) {
            InsertTicketsView(navigateBack = {
                navController.navigate(DestinasiHomeTiket.route){
                    popUpTo(DestinasiHomeTiket.route){
                        inclusive = true
                    }
                }
            })
        }

        // detail
        composable(
            DestinasiDetailTickets.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetailTickets.IdTickets) {
                    type = NavType.IntType }
            )){
            val idTiket = it.arguments?.getInt(DestinasiDetailTickets.IdTickets)
            idTiket?.let { idEvent ->
                DetailTicketsView(
                    navigateToItemUpdate = { navController.navigate("${DestinasiUpdateTickets.route}/$idEvent") },
                    navigateBack = { navController.navigate(DestinasiHomeTiket.route) {
                        popUpTo(DestinasiHomeTiket.route) {
                            inclusive = true
                        }
                    }}
                )
            }
        }

        // update
        composable(
            DestinasiUpdateTickets.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetailTickets.IdTickets){
                    type = NavType.IntType }
            )){
            val idTiket = it.arguments?.getInt(DestinasiUpdateTickets.IdTickets)
            idTiket?.let { idEvents ->
                UpdateTicketsView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // transactions
        // home
        composable(DestinasiHomeTransactions.route){
            HomeTransactionsView(
                navigateToltemEntry = { navController.navigate(DestinasiEntryTransactions.route) },
                onDetailClick = { idTransaksi ->
                    navController.navigate("${DestinasiDetailTransactions.route}/$idTransaksi")
                }
            )
        }

        // insert
        composable(DestinasiEntryTransactions.route) {
            InsertTransactionsView(navigateBack = {
                navController.navigate(DestinasiHomeTransactions.route){
                    popUpTo(DestinasiHomeTransactions.route){
                        inclusive = true
                    }
                }
            })
        }

        // detail
        composable(
            DestinasiDetailTransactions.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetailTransactions.IdTransactions) {
                    type = NavType.IntType }
            )){
            val idTransaksi = it.arguments?.getInt(DestinasiDetailTransactions.IdTransactions)
            idTransaksi?.let { idEvent ->
                DetailTransactionsView(
                    navigateBack = { navController.navigate(DestinasiHomeTransactions.route) {
                        popUpTo(DestinasiHomeTransactions.route) {
                            inclusive = true
                        }
                    }}
                )
            }
        }
    }
}

