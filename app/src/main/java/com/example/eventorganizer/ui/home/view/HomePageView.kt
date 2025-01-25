package com.example.eventorganizer.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C7285)) // Set background color of the page
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Row of Buttons with Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Event Page Button
                Button(
                    onClick = eventsPageBttn,
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3C8CF)) // Set background color
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info, // Event Icon
                                contentDescription = "Event Page",
                                modifier = Modifier.size(48.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Halaman Event", color = Color.White)
                        }
                    }
                }

                // Participant Page Button
                Button(
                    onClick = participantPageBttn,
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3C8CF)) // Set background color
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person, // Person Icon
                                contentDescription = "Participant Page",
                                modifier = Modifier.size(48.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Halaman Peserta", color = Color.White)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Ticket Page Button
                Button(
                    onClick = ticketPageBttn,
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3C8CF)) // Set background color
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star, // Ticket Icon
                                contentDescription = "Ticket Page",
                                modifier = Modifier.size(48.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Halaman Tiket", color = Color.White)
                        }
                    }
                }

                // Transaction Page Button
                Button(
                    onClick = transactionPageBttn,
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3C8CF)) // Set background color
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart, // Transaction Icon
                                contentDescription = "Transaction Page",
                                modifier = Modifier.size(48.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Halaman Transaksi", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
