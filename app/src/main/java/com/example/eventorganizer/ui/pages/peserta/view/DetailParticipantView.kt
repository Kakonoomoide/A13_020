package com.example.eventorganizer.ui.pages.peserta.view

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
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.peserta.viewmodel.DetailParticipantsViewModel
import com.example.eventorganizer.ui.pages.peserta.viewmodel.ParticipantsDetailUiState

object DestinasiDetailParticipants: DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Participants"
    const val IdParticipants = "idPeserta"
    val routesWithArg = "$route/{$IdParticipants}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsDetailView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailParticipantsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiDetailParticipants.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getParticipantsbyId()
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
                    contentDescription = "Edit Event"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.participantsDetailState,
            retryAction = { viewModel.getParticipantsbyId() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: ParticipantsDetailUiState
) {
    when (detailUiState) {
        is ParticipantsDetailUiState.Loading -> com.example.eventorganizer.ui.pages.event.view.OnLoading(
            modifier = modifier.fillMaxSize()
        )
        is ParticipantsDetailUiState.Success -> {
            if (detailUiState.prcp.idPeserta.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.") }
            } else {
                ItemDetailEvnt(
                    participants = detailUiState.prcp,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is ParticipantsDetailUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailEvnt(
    modifier: Modifier = Modifier,
    participants : Participant
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
            ComponentDetailEvnt(judul = "Id Event", isinya = participants.idPeserta.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailEvnt(judul = "Nama Event", isinya = participants.namaPeserta)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailEvnt(judul = "Deskripsi Event", isinya = participants.email)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailEvnt(judul = "Tanggal Event", isinya = participants.nomorTelepon)
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ComponentDetailEvnt(
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