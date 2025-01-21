package com.example.eventorganizer.ui.pages.tiket.view

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
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.tiket.viewmodel.HomeTicketsUiState
import com.example.eventorganizer.ui.pages.tiket.viewmodel.HomeTicketsViewModel


object DestinasiHomeTiket : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTicketsView(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeTicketsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTkt()
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
            homeUiState = viewModel.tktUiState,
            retryAction = { viewModel.getTkt() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTkt(it.idTiket)
                viewModel.getTkt()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeTicketsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Tickets) -> Unit = {},
){
    when (homeUiState) {
        is HomeTicketsUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTicketsUiState.Success ->
            if (homeUiState.tickets.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Lagi Gada Tiket...")
                }
            } else {
                MhsLayout(
                    tickets = homeUiState.tickets,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idEvent.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeTicketsUiState.Error -> OnError(
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
    tickets: List<Tickets>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tickets) -> Unit,
    onDeleteClick: (Tickets) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tickets) { ticket ->  // Correct usage of 'items' with a List of Tickets
            TicketsCardDispaly(
                tickets = ticket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDetailClick(ticket)
                    },
                onDeleteClick = {
                    onDeleteClick(ticket)
                }
            )
        }
    }
}

@Composable
fun TicketsCardDispaly(
    tickets: Tickets,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tickets) -> Unit = {}
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
                    text = tickets.idTiket.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(tickets) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            tickets.namaEvent?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            tickets.namaPeserta?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            tickets.tanggalEvent?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            tickets.lokasiEvent?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}