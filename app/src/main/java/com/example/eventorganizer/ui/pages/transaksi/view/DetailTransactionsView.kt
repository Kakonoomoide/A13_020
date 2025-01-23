package com.example.eventorganizer.ui.pages.transaksi.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.DetailTransactionsUiState
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.DetailTransactionsViewModel

object DestinasiDetailTransactions: DestinasiNavigasi {
    override val route = "detail_transactions"
    override val titleRes = "Detail Transactions"
    const val IdTransactions = "idTransaksi"
    val routesWithArg = "$route/{$IdTransactions}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransactionsView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTransactionsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailTransactions.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTransactionsbyId()
                }
            )
        },
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.transactionsDetailState,
            retryAction = { viewModel.getTransactionsbyId() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailTransactionsUiState
) {
    when (detailUiState) {
        is DetailTransactionsUiState.Loading -> OnLoading(
            modifier = modifier.fillMaxSize()
        )
        is DetailTransactionsUiState.Success -> {
            if (detailUiState.transactions.idTransaksi.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailEvnt(
                    transactions = detailUiState.transactions,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailTransactionsUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailEvnt(
    modifier: Modifier = Modifier,
    transactions: Transactions
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTkt(judul = "Id Ticket", isinya = transactions.idTiket.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            transactions.namaEvent?.let { ComponentDetailTkt(judul = "Nama Event", isinya = it) }
            Spacer(modifier = Modifier.padding(5.dp))
            transactions.namaPeserta?.let { ComponentDetailTkt(judul = "Nama Peserta", isinya = it) }
            Spacer(modifier = Modifier.padding(5.dp))
            transactions.tanggalTransaksi?.let { ComponentDetailTkt(judul = "Tanggal Transaksi", isinya = it) }
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Jumlah Pembayaran", isinya = transactions.jumlahPembayaran.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Jumlah Tiket Di beli", isinya = transactions.jumlahTiket.toString())
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ComponentDetailTkt(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}