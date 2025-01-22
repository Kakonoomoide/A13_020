package com.example.eventorganizer.ui.pages.transaksi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.repository.TransactionsRepository
import kotlinx.coroutines.launch

class InsertTicketsViewModel(private val trx: TransactionsRepository): ViewModel(){
    var uiState by mutableStateOf(InsertTransactionsUiState())
        private set

    fun insertDataUpdateTicktetState(insertTransactionsUiEvent: InsertTransactionsUiEvent) {
        uiState = InsertTransactionsUiState(insertTransactionsUiEvent = insertTransactionsUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                trx.insertTransactions(uiState.insertTransactionsUiEvent.toTrx())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransactionsUiState(
    val insertTransactionsUiEvent: InsertTransactionsUiEvent = InsertTransactionsUiEvent()
)

data class InsertTransactionsUiEvent(
    val idTransaksi: Int = 0,
    val idTiket: Int = 0,
    val jumlahTiket: Int = 0,
    val jumlahPembayaran: Int = 0,
    val tanggalTransaksi: String? = "",
    val namaEvent: String? = "",
    val namaPeserta: String? = ""
)

fun InsertTransactionsUiEvent.toTrx(): Transactions = Transactions(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi,
    namaEvent = namaEvent,
    namaPeserta = namaPeserta
)

fun Transactions.toUiStateTrx(): InsertTransactionsUiState = InsertTransactionsUiState(
    insertTransactionsUiEvent = toInsertTransactionsUiEvent()
)

fun Transactions.toInsertTransactionsUiEvent(): InsertTransactionsUiEvent = InsertTransactionsUiEvent(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi,
    namaEvent = namaEvent,
    namaPeserta = namaPeserta
)