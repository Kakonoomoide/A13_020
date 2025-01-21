package com.example.eventorganizer.ui.pages.tiket.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiDetailTickets: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Tickets"
    const val IdTickets = "idTiket"
    val routesWithArg = "$route/{$IdTickets}"
}