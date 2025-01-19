package com.example.eventorganizer.model

import kotlinx.serialization.*

@Serializable
data class AllEventResponse (
    val status: Boolean,
    val message: String,
    val data: List<Event>
)

@Serializable
data class EventDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Event
)

@Serializable
data class Event (
    @SerialName("id_event")
    val idEvent: Long,

    @SerialName("nama_event")
    val namaEvent: String,

    @SerialName("deskripsi_event")
    val deskripsiEvent: String,

    @SerialName("tanggal_event")
    val tanggalEvent: String,

    @SerialName("lokasi_event")
    val lokasiEvent: String
)