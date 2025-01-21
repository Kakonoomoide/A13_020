package com.example.eventorganizer.ui.pages.peserta.view

import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiDetailParticipants: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Participants"
    const val IdParticipants = "idPeserta"
    val routesWithArg = "$route/{$IdParticipants}"
}