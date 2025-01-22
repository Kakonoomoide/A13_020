package com.example.eventorganizer.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllTransactionResponse (
    val status: Boolean,
    val message: String,
    val data: List<Transactions>
)

@Serializable
data class TransactionDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Transactions
)

@Serializable
data class Transactions (
    @SerialName("id_transaksi")
    val idTransaksi: Int,

    @SerialName("id_tiket")
    val idTiket: Int,

    @SerialName("jumlah_tiket")
    val jumlahTiket: Int,

    @SerialName("jumlah_pembayaran")
    val jumlahPembayaran: Int,

    @SerialName("tanggal_transaksi")
    val tanggalTransaksi: String? = "",

    @SerialName("nama_event")
    val namaEvent: String? = "",

    @SerialName("nama_peserta")
    val namaPeserta: String? = ""
)