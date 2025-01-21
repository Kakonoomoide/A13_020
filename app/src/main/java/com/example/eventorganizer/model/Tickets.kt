package com.example.eventorganizer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllTicketsResponse (
    val status: Boolean,
    val message: String,
    val data: List<Tickets>
)

@Serializable
data class TicketsDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Tickets
)

@Serializable
data class Tickets (
    @SerialName("id_tiket")
    val idTiket: Int,

    @SerialName("id_event")
    val idEvent: Int,

    @SerialName("id_pengguna")
    val idPengguna: Int,

    @SerialName("Kapasitas_tiket")
    val kapasitasTiket: Int,

    @SerialName("harga_tiket")
    val hargaTiket: Int,

    @SerialName("nama_event")
    val namaEvent: String? = "",

    @SerialName("tanggal_event")
    val tanggalEvent: String? = "",

    @SerialName("lokasi_event")
    val lokasiEvent: String? = "",

    @SerialName("nama_peserta")
    val namaPeserta: String? = ""
)