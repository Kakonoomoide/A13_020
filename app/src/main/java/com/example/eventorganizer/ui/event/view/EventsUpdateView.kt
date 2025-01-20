package com.example.eventorganizer.ui.event.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiUpdate: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mhs"
    const val IdEvent = "idEvent"
    val routesWithArg = "${DestinasiDetail.route}/{$IdEvent}"
}