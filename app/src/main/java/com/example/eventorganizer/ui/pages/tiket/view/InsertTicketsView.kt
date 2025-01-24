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
import com.example.eventorganizer.model.Events
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.repository.EventsRepository
import com.example.eventorganizer.repository.ParticipantsRepository
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.costumwidget.DynamicSelectedField
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsUiEvent
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsUiState
import com.example.eventorganizer.ui.pages.tiket.viewmodel.InsertTicketsViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "item_entry_tickets"
    override val titleRes = "Entry Tickets"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertTicketsView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTicketsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    eventsRepo: EventsRepository ,
    participantsRepo: ParticipantsRepository
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
                .fillMaxWidth(),
            eventsRepo = eventsRepo,
            participantsRepo = participantsRepo
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertTicketsUiState,
    onSiswaValueChange: (InsertTicketsUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    eventsRepo: EventsRepository,
    participantsRepo: ParticipantsRepository,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertTicketsUiEvent,
            onValueChange = onSiswaValueChange,
            eventsRepository = eventsRepo,
            participantsRepository = participantsRepo,
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
    eventsRepository: EventsRepository,
    participantsRepository: ParticipantsRepository,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    val coroutineScope = rememberCoroutineScope()
    var events by remember { mutableStateOf(emptyList<Events>()) }
    var participants by remember { mutableStateOf(emptyList<Participant>()) }
    var selectedEventName by remember { mutableStateOf("") }
    var selectedParticipantName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            events = eventsRepository.getAllEvents().data
            participants = participantsRepository.getAllParticipants().data
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // id event
        DynamicSelectedField(
            selectedValue = selectedEventName,
            options = events.map { it.namaEvent },
            label = "Pilih Event",  // Corrected the parameter name from lable to label
            onValueChangedEvent = { selectedName ->
                selectedEventName = selectedName
                val selectedEvent = events.find { it.namaEvent == selectedName }
                onValueChange(insertUiEvent.copy(idEvent = selectedEvent?.idEvent ?: 0))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // id pengguna
        DynamicSelectedField(
            selectedValue = selectedParticipantName,
            options = participants.map { it.namaPeserta }, // Tampilkan nama peserta
            label = "Pilih Peserta",
            onValueChangedEvent = { selectedName ->
                selectedParticipantName = selectedName
                val selectedParticipant = participants.find { it.namaPeserta == selectedName }
                onValueChange(insertUiEvent.copy(idPengguna = selectedParticipant?.idPeserta ?: 0))
            },
            modifier = Modifier.fillMaxWidth()
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