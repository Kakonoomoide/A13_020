package com.example.eventorganizer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiDetailTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiEntryTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiHomeTransactions
import com.example.eventorganizer.ui.pages.transaksi.view.DetailTransactionsView
import com.example.eventorganizer.ui.pages.transaksi.view.HomeTransactionsView
import com.example.eventorganizer.ui.pages.transaksi.view.InsertTransactionsView

@Composable
fun TransactionNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeTransactions.route,
        modifier = Modifier
    ) {
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