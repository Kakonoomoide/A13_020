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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsUiEvent
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsUiState
import com.example.eventorganizer.ui.pages.transaksi.viewmodel.InsertTransactionsViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTransactions : DestinasiNavigasi {
    override val route = "transactions_entry"
    override val titleRes = "Entry Transactions"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTransactionsView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
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
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertTransactionsUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
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
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // id Tiket
        OutlinedTextField(
            value = insertUiEvent.idTiket.toString(),
            onValueChange = {
                val newIdTiket = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(idTiket = newIdTiket))
            },
            label = { Text("Id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // jumlah Tiket
        OutlinedTextField(
            value = insertUiEvent.jumlahTiket.toString(),
            onValueChange = {
                val newJumlahTiket = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(jumlahTiket = newJumlahTiket))
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // jumlah pembayaran
        OutlinedTextField(
            value = insertUiEvent.jumlahPembayaran.toString(),
            onValueChange = {
                val newJumlahPembayaran = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(jumlahPembayaran = newJumlahPembayaran))
            },
            label = { Text("Jumalah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // Tanggal Transaksi
        OutlinedTextField(
            value = insertUiEvent.tanggalTransaksi.toString(),
            onValueChange = {onValueChange(insertUiEvent.copy(tanggalTransaksi = it))},
            label = { Text("Tanggal Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        if(enabled) {
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