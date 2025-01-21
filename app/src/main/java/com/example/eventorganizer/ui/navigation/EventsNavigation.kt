package com.example.eventorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventorganizer.ui.pages.event.view.DestinasiDetail
import com.example.eventorganizer.ui.pages.event.view.DestinasiEntry
import com.example.eventorganizer.ui.pages.event.view.DestinasiHome
import com.example.eventorganizer.ui.pages.event.view.DestinasiUpdate
import com.example.eventorganizer.ui.pages.event.view.EventsDetailView
import com.example.eventorganizer.ui.pages.event.view.EventsHomeView
import com.example.eventorganizer.ui.pages.event.view.EventsInsertView
import com.example.eventorganizer.ui.pages.event.view.EventsUpdateView

@Composable
fun EventsNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        // home
        composable(DestinasiHome.route){
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
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
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
                    navigateBack = { navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
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
    }
}