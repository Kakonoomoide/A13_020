package com.example.eventorganizer.ui.pages.tiket.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiUpdateTickets: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Event"
    const val IdEvent = "idEvent"
    val routesWithArg = "$route/{$IdEvent}"
}