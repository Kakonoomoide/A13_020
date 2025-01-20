package com.example.eventorganizer.model

import kotlinx.serialization.*

@Serializable
data class AllEventsResponse (
    val status: Boolean,
    val message: String,
    val data: List<Events>
)

@Serializable
data class EventsDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Events
)

@Serializable
data class Events (
    @SerialName("id_event")
    val idEvent: Int,

    @SerialName("nama_event")
    val namaEvent: String,

    @SerialName("deskripsi_event")
    val deskripsiEvent: String,

    @SerialName("tanggal_event")
    val tanggalEvent: String,

    @SerialName("lokasi_event")
    val lokasiEvent: String
)