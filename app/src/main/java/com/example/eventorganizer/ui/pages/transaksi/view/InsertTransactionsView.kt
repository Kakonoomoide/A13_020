package com.example.eventorganizer.ui.pages.transaksi.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.costumwidget.DynamicSelectedField
import com.example.eventorganizer.ui.costumwidget.DynamicSelectedFieldInt
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsUiEvent
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsUiState
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTransactions : DestinasiNavigasi {
    override val route = "item_entry_transactions"
    override val titleRes = "Entry Transactions"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTransactionsView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    ticketsRepository: TicketsRepository,
    viewModel: InsertTransactionsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiEntryTransactions.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::insertDataUpdateTransactionsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMhs()
                    navigateBack()
                }
            },
            ticketsRepository = ticketsRepository,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertTransactionsUiState,
    onSiswaValueChange: (InsertTransactionsUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    ticketsRepository: TicketsRepository,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertTransactionsUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth(),
            ticketsRepository = ticketsRepository
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertTransactionsUiEvent,
    onValueChange: (InsertTransactionsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    ticketsRepository: TicketsRepository,
    enabled: Boolean = true
) {
    val coroutineScope = rememberCoroutineScope()
    var tickets by remember { mutableStateOf(emptyList<Tickets>()) }
    var selectedTicketsId by remember { mutableStateOf(0) }
    val ticketPrice = remember { mutableStateOf(0) }

    // Fetch ticket data asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            tickets = ticketsRepository.getAllTickets().data
            val selectedTicket = tickets.find { it.idTiket == selectedTicketsId }
            ticketPrice.value = selectedTicket?.hargaTiket ?: 0
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // id Tiket
        DynamicSelectedFieldInt(
            selectedValue = selectedTicketsId,
            options = tickets.map { it.idTiket },
            label = "Pilih Tiket",
            onValueChangedEvent = { selectedId ->
                selectedTicketsId = selectedId
                val selectedTicket = tickets.find { it.idTiket == selectedId }
                ticketPrice.value = selectedTicket?.hargaTiket ?: 0
                onValueChange(insertUiEvent.copy(idTiket = selectedTicket?.idTiket ?: 0))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // jumlah Tiket
        OutlinedTextField(
            value = insertUiEvent.jumlahTiket.toString(),
            onValueChange = {
                val newJumlahTiket = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                val newJumlahPembayaran = newJumlahTiket * ticketPrice.value
                onValueChange(insertUiEvent.copy(jumlahTiket = newJumlahTiket, jumlahPembayaran = newJumlahPembayaran))
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // jumlah pembayaran (calculated automatically based on jumlahTiket and ticketPrice)
        OutlinedTextField(
            value = insertUiEvent.jumlahPembayaran.toString(),
            onValueChange = {},
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = false
        )

        // Tanggal Transaksi
        OutlinedTextField(
            value = insertUiEvent.tanggalTransaksi.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalTransaksi = it)) },
            label = { Text("Tanggal Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
