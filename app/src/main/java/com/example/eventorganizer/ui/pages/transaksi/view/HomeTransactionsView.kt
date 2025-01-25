package com.example.eventorganizer.ui.pages.transaksi.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.HomeTransactionsUiState
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.HomeTransactionsViewModel

object DestinasiHomeTransactions : DestinasiNavigasi {
    override val route = "home_transactions"
    override val titleRes = "Home Transactions"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTransactionsView(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTransactionsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomeTransactions.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTrx()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ){ innerPadding ->
        HomeStatus(
            homeUiState = viewModel.trxUiState,
            retryAction = { viewModel.getTrx() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTrx(it.idTiket)
                viewModel.getTrx()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeTransactionsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Transactions) -> Unit = {},
){
    when (homeUiState) {
        is HomeTransactionsUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTransactionsUiState.Success ->
            if (homeUiState.transactions.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Lagi Gada Tiket...")
                }
            } else {
                MhsLayout(
                    transactions = homeUiState.transactions,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idTransaksi.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTransactionsUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Text("sabar bossssss")
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Text(
            text = "Terjadi kesalahan",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = retryAction
        ) {
            Text("Retry")
        }

    }
}

@Composable
fun MhsLayout(
    transactions: List<Transactions>,
    modifier: Modifier = Modifier,
    onDetailClick: (Transactions) -> Unit,
    onDeleteClick: (Transactions) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transactions) { transactions ->  // Correct usage of 'items' with a List of Transaksi
            TicketsCardDispaly(
                transactions = transactions,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(transactions)
                    },
                onDeleteClick = {
                    onDeleteClick(transactions)
                }
            )
        }
    }
}

@Composable
fun TicketsCardDispaly(
    transactions: Transactions,
    modifier: Modifier = Modifier,
    onDeleteClick: (Transactions) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transactions.idTransaksi.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(transactions) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            // Nama Event
            transactions.namaEvent?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info, // Ikon untuk nama event
                        contentDescription = "Event Name",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Nama Peserta
            transactions.namaPeserta?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person, // Ikon untuk nama peserta
                        contentDescription = "Participant Name",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Tanggal Transaksi
            transactions.tanggalTransaksi?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange, // Ikon untuk tanggal transaksi
                        contentDescription = "Transaction Date",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Jumlah Pembayaran
            transactions.jumlahPembayaran.toString().let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart, // Ikon untuk jumlah pembayaran
                        contentDescription = "Payment Amount",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}