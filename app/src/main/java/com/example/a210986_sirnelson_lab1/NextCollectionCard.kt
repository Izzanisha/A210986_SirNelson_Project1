package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextCollectionCard(snackbarHostState: SnackbarHostState, viewModel: TrashViewModel) {
    val trashInfo = viewModel.trashInfo.observeAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Next Collection", style = MaterialTheme.typography.titleMedium)

            trashInfo.value?.let {
                Text("Area: ${it.area}", style = MaterialTheme.typography.bodyLarge)
                Text("Date: ${it.nextCollectionDate}", style = MaterialTheme.typography.bodyLarge)
            } ?: Text("No collection info yet. Please check your area.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}