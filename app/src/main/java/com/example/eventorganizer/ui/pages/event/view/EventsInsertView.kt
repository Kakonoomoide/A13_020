package com.example.eventorganizer.ui.pages.event.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.pages.event.viewmodel.EventsInsertViewModel
import com.example.eventorganizer.ui.pages.event.viewmodel.InsertUiEvent
import com.example.eventorganizer.ui.pages.event.viewmodel.InsertUiState
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry_events"
    override val titleRes = "Entry Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsInsertView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EventsInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
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
    insertUiState: InsertUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
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
    insertUiEvent: InsertUiEvent,
    onValueChange: (InsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // date picker
        var selectedDate by remember { mutableStateOf(insertUiEvent.tanggalEvent) }

        val context = LocalContext.current
        val datePickerDialog = remember {
            DatePickerDialog(context).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDateString = "$dayOfMonth/${month + 1}/$year"  // Format as dd/MM/yyyy
                    selectedDate = selectedDateString
                    onValueChange(insertUiEvent.copy(tanggalEvent = selectedDateString))  // Update the event with selected date
                }
            }
        }

        // nama event
        OutlinedTextField(
            value = insertUiEvent.namaEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(namaEvent = it)) },
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )

        // deskripsi event
        OutlinedTextField(
            value = insertUiEvent.deskripsiEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiEvent = it)) },
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )


        // tanggal event
        OutlinedTextField(
            value = insertUiEvent.tanggalEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalEvent = it)) },
            label = { Text("Tanggal Event") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    if (enabled) {
                        datePickerDialog.show()
                    }
                },
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text // Use Text, since the date picker will handle input
            )

        )

        // lokasi event
        OutlinedTextField(
            value = insertUiEvent.lokasiEvent,
            onValueChange = { onValueChange(insertUiEvent.copy(lokasiEvent = it)) },
            label = { Text("Lokasi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
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