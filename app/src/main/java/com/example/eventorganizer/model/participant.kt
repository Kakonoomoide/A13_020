package com.example.eventorganizer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllParticipantResponse (
    val status: Boolean,
    val message: String,
    val data: List<Participant>
)

@Serializable
data class ParticipantDetailResponse (
    val status: Boolean,
    val message: String,
    val data: Participant
)

@Serializable
data class Participant (
    @SerialName("id_peserta")
    val idPeserta: Long,

    @SerialName("nama_peserta")
    val namaPeserta: String,

    val email: String,

    @SerialName("nomor_telepon")
    val nomorTelepon: String
)