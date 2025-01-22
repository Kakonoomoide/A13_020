package com.example.eventorganizer.ui.pages.transaksi.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiDetailTransactions: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Transactions"
    const val IdTransactions = "idTransaksi"
    val routesWithArg = "$route/{$IdTransactions}"
}