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
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiDetailParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiEntryParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiHomeParticipants
import com.example.eventorganizer.ui.pages.peserta.view.DestinasiUpdateParticipants
import com.example.eventorganizer.ui.pages.peserta.view.ParticipantsDetailView
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
    }
}