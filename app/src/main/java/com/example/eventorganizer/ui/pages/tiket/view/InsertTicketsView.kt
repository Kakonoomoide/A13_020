package com.example.eventorganizer.ui.pages.tiket.view

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
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsUiEvent
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsUiState
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "tickets_entry"
    override val titleRes = "Entry Tickets"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsInsertView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTicketsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::insertDataUpdateTicktetState,
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
    insertUiState: InsertTicketsUiState,
    onSiswaValueChange: (InsertTicketsUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertTicketsUiEvent,
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
    insertUiEvent: InsertTicketsUiEvent,
    onValueChange: (InsertTicketsUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // id event
        OutlinedTextField(
            value = insertUiEvent.idEvent.toString(),
            onValueChange = {
                val newIdEvent = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(idEvent = newIdEvent))
            },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // id pengguna
        OutlinedTextField(
            value = insertUiEvent.idPengguna.toString(),
            onValueChange = {
                val newIdPengguna = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(idPengguna = newIdPengguna))
            },
            label = { Text("Nama Pengguna") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // kapasitas tiket
        OutlinedTextField(
            value = insertUiEvent.kapasitasTiket.toString(),
            onValueChange = {
                val newKapasitas = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(kapasitasTiket = newKapasitas))
            },
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // harga tiket
        OutlinedTextField(
            value = insertUiEvent.hargaTiket.toString(),
            onValueChange = {
                val newHargaTiket = it.toIntOrNull() ?: 0  // Convert back to Int (handle invalid input)
                onValueChange(insertUiEvent.copy(hargaTiket = newHargaTiket))
            },
            label = { Text("Harga Tiket") },
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