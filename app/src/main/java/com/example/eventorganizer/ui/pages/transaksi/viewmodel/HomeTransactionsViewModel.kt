package com.example.eventorganizer.ui.pages.transaksi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.repository.TransactionsRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeTransactionsUiState {
    data class Success(val transactions: List<Transactions>) : HomeTransactionsUiState()
    object Error : HomeTransactionsUiState()
    object Loading : HomeTransactionsUiState()
}

class HomeTransactionsViewModel (
    private val trx: TransactionsRepository
) : ViewModel() {
    var trxUiState: HomeTransactionsUiState by mutableStateOf(HomeTransactionsUiState.Loading)
        private set

    init {
        getTrx()
    }

    fun getTrx() {
        viewModelScope.launch {
            trxUiState = HomeTransactionsUiState.Loading
            trxUiState = try {
                HomeTransactionsUiState.Success(trx.getAllTransactions().data)
            } catch (e: IOException) {
                HomeTransactionsUiState.Error
            } catch (e: HttpException) {
                HomeTransactionsUiState.Error
            }
        }
    }

    fun deleteTrx(IdTransactions: Int) {
        viewModelScope.launch {
            try {
                trx.deleteTransactions(IdTransactions)
            } catch (e: IOException) {
                HomeTransactionsUiState.Error
            } catch (e: HttpException) {
                HomeTransactionsUiState.Error
            }
        }
    }
}