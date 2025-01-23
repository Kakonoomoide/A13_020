package com.example.eventorganizer.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi

object DestinasiHomePage : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Page"
}

@Composable
fun HomePageView(
    eventsPageBttn: () -> Unit,
    participantPageBttn: () -> Unit,
    ticketPageBttn: () -> Unit,
    transactionPageBttn: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // events page
        Button(
            onClick = {
                eventsPageBttn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text("Halaman Event")
        }

        // peserta
        Button(
            onClick = {
                participantPageBttn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text("Halaman Peserta")
        }

        // tiket
        Button(
            onClick = {
                ticketPageBttn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text("Halaman Tiket")
        }

        // transaksi
        Button(
            onClick = {
                transactionPageBttn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text("Halaman Transaksi")
        }
    }
}