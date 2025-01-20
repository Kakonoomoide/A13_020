package com.example.eventorganizer.ui.event.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiDetail: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Event"
    const val IdEvent = "idEvent"
    val routesWithArg = "$route/{$IdEvent}"
}