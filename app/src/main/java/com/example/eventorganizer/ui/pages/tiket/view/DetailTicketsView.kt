package com.example.eventorganizer.ui.pages.tiket.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.tiket.viewmodel.DetailTicketsUiState
import com.example.eventorganizer.ui.pages.tiket.viewmodel.DetailTicketsViewModel

object DestinasiDetailTickets: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Tickets"
    const val IdTickets = "idTiket"
    val routesWithArg = "$route/{$IdTickets}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTicketsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailTickets.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTicketsbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tickets"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.TicketsDetailState,
            retryAction = { viewModel.getTicketsbyId() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailTicketsUiState
) {
    when (detailUiState) {
        is DetailTicketsUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailTicketsUiState.Success -> {
            if (detailUiState.tickets.idTiket.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailEvnt(
                    ticket = detailUiState.tickets,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailTicketsUiState.Error -> com.example.eventorganizer.ui.pages.event.view.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailEvnt(
    modifier: Modifier = Modifier,
    ticket: Tickets
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
            ComponentDetailTkt(judul = "Id Ticket", isinya = ticket.idTiket.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Nama Event", isinya = ticket.namaEvent)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Nama Peserta", isinya = ticket.namaPeserta)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Tanggal Event", isinya = ticket.tanggalEvent)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Lokasi", isinya = ticket.lokasiEvent)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Harga Tiket", isinya = ticket.hargaTiket.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTkt(judul = "Kapasitas Tiket", isinya = ticket.kapasitasTiket.toString())
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