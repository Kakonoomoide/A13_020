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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.model.Tickets
import com.example.eventorganizer.model.Transactions
import com.example.eventorganizer.repository.TicketsRepository
import com.example.eventorganizer.repository.TransactionsRepository
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import com.example.eventorganizer.ui.pages.tiket.viewmodel.HomeTicketsUiState
import com.example.eventorganizer.ui.pages.tiket.viewmodel.HomeTicketsViewModel
import okhttp3.internal.wait

object DestinasiHomeTiket : DestinasiNavigasi {
    override val route = "home_tickets"
    override val titleRes = "Home Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTicketsView(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    ticketsData: List<Tickets>, // Accept tickets data
    transactionsData: List<Transactions>, // Accept transactions data
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
            },
            ticketsData = ticketsData,
            transactionsData = transactionsData
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
    ticketsData: List<Tickets>, // Accept tickets data
    transactionsData: List<Transactions> // Accept transactions data
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
                    },
                    ticketsData = ticketsData,
                    transactionsData = transactionsData
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
    ticketsData: List<Tickets>, // Accept tickets data
    transactionsData: List<Transactions> // Accept transactions data
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
                },
                ticketsData = ticketsData,
                transactionsData = transactionsData
            )
        }
    }
}

@Composable
fun TicketsCardDispaly(
    tickets: Tickets,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tickets) -> Unit = {},
    ticketsData: List<Tickets>, // Accept tickets data
    transactionsData: List<Transactions> // Accept transactions data
) {
    val ticketColors = getTicketsCardColor(ticketsData, transactionsData)
    val cardColorCh = ticketColors[tickets.idTiket] ?: Color(0xFF4CAF50)

    // Tentukan apakah tiket habis
    val isSoldOut = cardColorCh == Color(0xFFFD8A8A) // Merah berarti sold out

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColorCh)
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
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )

                // Jika tiket habis, tampilkan "SOLD OUT!!!"
                if (isSoldOut) {
                    Text(
                        text = " SOLD OUT!!!",
                        color = Color(0xFFAF1740), // Warna merah untuk sold out
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(tickets) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }

            // Nama Event
            tickets.namaEvent?.let {
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
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

            // Nama Peserta
            tickets.namaPeserta?.let {
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
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

            // Tanggal Event
            tickets.tanggalEvent?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange, // Ikon untuk tanggal event
                        contentDescription = "Event Date",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

            // Lokasi Event
            tickets.lokasiEvent?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn, // Ikon untuk lokasi event
                        contentDescription = "Event Location",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}


fun getTicketsCardColor(
    tickets: List<Tickets>,
    transactions: List<Transactions>
): Map<Int, Color> {
    val ticketColors = mutableMapOf<Int, Color>()

    val ticketGroups = tickets.groupBy { it.idTiket }

    ticketGroups.forEach { (idTiket, ticketGroup) ->
        val totalCapacity = ticketGroup.sumOf { it.kapasitasTiket }

        val totalSold = transactions.filter { it.idTiket == idTiket }
            .sumOf { it.jumlahTiket }

        val availableCapacity = totalCapacity - totalSold
        val availabilityPercentage = if (totalCapacity > 0) (availableCapacity.toFloat() / totalCapacity) * 100 else 0f

        val color = when {
            availabilityPercentage > 50 -> Color(0xFF708871) // Hijau
            availabilityPercentage > 0 -> Color(0xFFFDFFAB) // Kuning
            else -> Color(0xFFFD8A8A) // Merah (Sold Out)
        }

        ticketColors[idTiket] = color
    }
    return ticketColors
}
