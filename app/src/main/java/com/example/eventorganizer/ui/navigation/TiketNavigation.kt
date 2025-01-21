package com.example.eventorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiDetailTickets
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiEntryTiket
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiHomeTiket
import com.example.eventorganizer.ui.pages.tiket.view.DestinasiUpdateTickets
import com.example.eventorganizer.ui.pages.tiket.view.DetailTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.HomeTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.InsertTicketsView
import com.example.eventorganizer.ui.pages.tiket.view.UpdateTicketsView

@Composable
fun TiketNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeTiket.route,
        modifier = Modifier
    ) {
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
    }
}