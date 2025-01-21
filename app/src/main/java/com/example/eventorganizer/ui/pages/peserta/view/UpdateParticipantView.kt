package com.example.eventorganizer.ui.pages.peserta.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiUpdateParticipants: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Participants"
    const val IdParticipants = "idPeserta"
    val routesWithArg = "$route/{$IdParticipants}"
}