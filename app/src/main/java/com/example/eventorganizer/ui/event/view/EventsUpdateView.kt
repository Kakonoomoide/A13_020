package com.example.eventorganizer.ui.event.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventorganizer.ui.PenyediaViewModel
import com.example.eventorganizer.ui.costumwidget.CoustumeTopAppBar
import com.example.eventorganizer.ui.event.viewmodel.EventsUpdateViewModel
import com.example.eventorganizer.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdate: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mhs"
    const val IdEvent = "idEvent"
    val routesWithArg = "${DestinasiDetail.route}/{$IdEvent}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsUpdateView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: EventsUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onSiswaValueChange = viewModel::updateInsertEvntState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvent()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}