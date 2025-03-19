package com.example.stellarispartylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterOptions(
    filter: String,
    onFilterChange: (String) -> Unit,
    allCount: Int,
    checkedInCount: Int,
    notCheckedInCount: Int
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FilterChip(
            selected = filter == "all",
            onClick = { onFilterChange("all") },
            label = { Text("Todos: $allCount",
                style = MaterialTheme.typography.bodySmall) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle, // Ícone de aviso, mas você pode trocar
                    contentDescription = "Todos",
                    modifier = Modifier.size(14.dp), // Ajuste o tamanho do ícone se necessário
                    tint = Color.Gray // Cor do ícone
                )
            }
        )
        FilterChip(
            selected = filter == "checkedIn",
            onClick = { onFilterChange("checkedIn") },
            label = { Text("Check-in: $checkedInCount",
                style = MaterialTheme.typography.bodySmall) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Check, // Ícone de aviso, mas você pode trocar
                    contentDescription = "Checkins",
                    modifier = Modifier.size(14.dp), // Ajuste o tamanho do ícone se necessário
                    tint = Color.Green // Cor do ícone
                )
            }
        )
        FilterChip(
            selected = filter == "notCheckedIn",
            onClick = { onFilterChange("notCheckedIn") },
            label = { Text("Pendentes: $notCheckedInCount",
                style = MaterialTheme.typography.bodySmall) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Warning, // Ícone de aviso, mas você pode trocar
                    contentDescription = "Pendentes",
                    modifier = Modifier.size(14.dp), // Ajuste o tamanho do ícone se necessário
                    tint = Color.Yellow // Cor do ícone
                )
            }
        )
    }
}