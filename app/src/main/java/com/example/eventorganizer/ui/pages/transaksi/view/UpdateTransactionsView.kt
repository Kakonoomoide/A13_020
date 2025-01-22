package com.example.eventorganizer.ui.pages.transaksi.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiUpdateTransactions: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Transactions"
    const val IdTransactions = "idTransaksi"
    val routesWithArg = "$route/{$IdTransactions}"
}

