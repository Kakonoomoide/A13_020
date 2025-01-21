package com.example.eventorganizer.ui.pages.peserta.view

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
import androidx.compose.material.icons.filled.Delete
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
import com.example.eventorganizer.model.Participant
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.peserta.viewmodel.HomeParticipantViewModel
import com.example.eventorganizer.ui.pages.peserta.viewmodel.ParticipantsHomeUiState

object DestinasiHomeParticipants : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Participants"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsHomeView(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeParticipantViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomeParticipants.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPrcp()
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
            homeUiState = viewModel.prcpUiState,
            retryAction = { viewModel.getPrcp() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePrcp(it.idPeserta)
                viewModel.getPrcp()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: ParticipantsHomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Participant) -> Unit = {},
){
    when (homeUiState) {
        is ParticipantsHomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is ParticipantsHomeUiState.Success ->
            if (homeUiState.participants.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Lagi Gada Event...")
                }
            } else {
                MhsLayout(
                    prcp = homeUiState.participants,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPeserta.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is ParticipantsHomeUiState.Error -> OnError(
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
    prcp: List<Participant>,
    modifier: Modifier = Modifier,
    onDetailClick: (Participant) -> Unit,
    onDeleteClick: (Participant) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(prcp) { prcp ->  // Correct usage of 'items' with a List of Participants
            EventsCardDispaly(
                prcp = prcp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(prcp)
                    },
                onDeleteClick = {
                    onDeleteClick(prcp)
                }
            )
        }
    }
}

@Composable
fun EventsCardDispaly(
    prcp: Participant,
    modifier: Modifier = Modifier,
    onDeleteClick: (Participant) -> Unit = {}
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
                    text = prcp.idPeserta.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(prcp) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(
                text = prcp.namaPeserta,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = prcp.email,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = prcp.nomorTelepon,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}