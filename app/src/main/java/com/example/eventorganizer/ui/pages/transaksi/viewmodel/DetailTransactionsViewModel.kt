package com.example.eventorganizer.ui.pages.transaksi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.repository.TransactionsRepository
import com.example.eventorganizer.ui.pages.transaksi.view.DestinasiDetailTransactions
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailTransactionsUiState {
    data class Success(val transactions: Transactions) : DetailTransactionsUiState()
    object Error : DetailTransactionsUiState()
    object Loading : DetailTransactionsUiState()
}

class DetailTransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    private val trx : TransactionsRepository
) : ViewModel() {

    var transactionsDetailState: DetailTransactionsUiState by mutableStateOf(DetailTransactionsUiState.Loading)
        private set

    private val _idTransactions: Int = checkNotNull(savedStateHandle[DestinasiDetailTransactions.IdTransactions])

    init {
        getTransactionsbyId()
    }

    fun getTransactionsbyId() {
        viewModelScope.launch {
            transactionsDetailState = DetailTransactionsUiState.Loading
            transactionsDetailState = try {
                val tickets = trx.getTransactionsById(_idTransactions)
                Log.d("TicketDetails", "Response: $tickets")
                DetailTransactionsUiState.Success(tickets)
            } catch (e: IOException) {
                DetailTransactionsUiState.Error
            } catch (e: HttpException) {
                DetailTransactionsUiState.Error
            }
        }
    }
}