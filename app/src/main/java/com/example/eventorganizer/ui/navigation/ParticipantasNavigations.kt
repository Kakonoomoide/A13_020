package com.example.eventorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiDetailParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiEntryParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiHomeParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiUpdateParticipants
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsHomeView
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsInsertView
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsUpdateView
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiDetailTickets
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiUpdateTickets
import com.example.eventorganizer.ui.pages.tiket.view.UpdateTicketsView

@Composable
fun ParticipantsNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeParticipants.route,
        modifier = Modifier
    ) {
        // home
        composable(DestinasiHomeParticipants.route){
            ParticipantsHomeView(
                navigateToltemEntry = { navController.navigate(DestinasiEntryParticipants.route) },
                onDetailClick = { idPeserta ->
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
            DestinasiDetailTickets.routesWithArg, arguments = listOf(
                navArgument(DestinasiDetailTickets.IdTickets) {
                    type = NavType.IntType }
            )){
            val idPrcp = it.arguments?.getInt(DestinasiDetailTickets.IdTickets)
            idPrcp?.let { idPeserta ->
                ParticipantsUpdateView(
                    onNavigate = { navController.navigate("${DestinasiUpdateParticipants.route}/$idPeserta") },
                    onBack = { navController.navigate(DestinasiHomeParticipants.route) {
                        popUpTo(DestinasiHomeParticipants.route) {
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
            val idPrcp = it.arguments?.getInt(DestinasiUpdateTickets.IdTickets)
            idPrcp?.let { idPeserta ->
                UpdateTicketsView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}