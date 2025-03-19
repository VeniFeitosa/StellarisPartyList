package com.example.stellarispartylist.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.stellarispartylist.components.CSVHandler
import com.example.stellarispartylist.components.FilterOptions
import java.io.File
import java.time.LocalDateTime
import com.example.stellarispartylist.components.SearchBar
import java.time.format.DateTimeFormatter

/*
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(csvFile: File?) {
    val context = LocalContext.current
    val csvData = remember { mutableStateListOf<Array<String>>() }

    // Carrega os dados do CSV ao iniciar a tela
    LaunchedEffect(csvFile) {
        if (csvFile != null) {
            csvData.addAll(CSVHandler.readCSV(csvFile))
        }
    }

    // Exibe os dados em uma lista
    LazyColumn{
        items(csvData.size) { index ->
            val row = csvData[index]
            val (id, name, phone, checkIn) = row

            ListItem(
                headlineContent = { Text(name) },
                supportingContent = { Text(phone) },
                trailingContent = {
                    if (checkIn.isBlank()) {
                        Button(onClick = {
                            // Registra o horário do check-in
                            val currentTime = LocalDateTime.now().toString()
                            row[3] = currentTime
                            CSVHandler.writeCSV(csvFile!!, csvData)
                        }) {
                            Text("Check-in")
                        }
                    } else {
                        Text(checkIn)
                    }
                }
            )
        }
    }
}*/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(csvFile: File) {
    val context = LocalContext.current
    val csvData = remember { mutableStateListOf<Array<String>>() }

    // Carrega os dados do CSV ao iniciar a tela
    LaunchedEffect(csvFile) {
        if (csvFile.exists()) {
            csvData.addAll(CSVHandler.readCSV(csvFile))
        } else {
            // Exibir mensagem de erro se o arquivo não existir
            Toast.makeText(context, "Arquivo CSV não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }

    // Estado para a barra de pesquisa
    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }

    // Estado para o filtro de check-in
    val (filter, setFilter) = remember { mutableStateOf("all") } // "all", "checkedIn", "notCheckedIn"

    // Estado para controlar o processo de check-in
    val (isCheckingIn, setIsCheckingIn) = remember { mutableStateOf(false) }

    // Filtra os dados com base na pesquisa e no filtro
    val filteredData = csvData.drop(1).filter { row ->
        val (id, name, phone, checkIn) = row
        val matchesSearch = name.contains(searchQuery, ignoreCase = true) ||
                phone.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (filter) {
            "checkedIn" -> checkIn.isNotBlank()
            "notCheckedIn" -> checkIn.isBlank()
            else -> true // "all"
        }
        matchesSearch && matchesFilter
    }
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val filteredDataWithoutHeader = filteredData
    // Calcula as contagens para cada filtro
    val allCount = csvData.drop(1).size
    val checkedInCount = csvData.drop(1).count { it[3].isNotBlank() } // Check-in feito
    val notCheckedInCount = csvData.drop(1).count { it[3].isBlank() } // Check-in pendente

    // Layout da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // Barra de pesquisa
            SearchBar(searchQuery, setSearchQuery)
        }

        // Filtros de check-in
        FilterOptions(
            filter,
            setFilter,
            allCount,
            checkedInCount,
            notCheckedInCount
        )

        // Lista de pessoas
        LazyColumn {
            items(filteredDataWithoutHeader) { row  ->
                val (id, name, phone, checkIn) = row
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(phone) },
                    trailingContent = {
                        if (checkIn.isBlank()) {
                            if (isCheckingIn) {
                                // Exibe o botão desativado com o texto "Fazendo check-in..."
                                Button(
                                    onClick = {  },
                                    enabled = false
                                ) {
                                    Text("Fazendo check-in...")
                                }
                            } else {
                                // Botão para iniciar o check-in
                                Button(
                                onClick = {
                                    // Inicia o processo de check-in
                                    setIsCheckingIn(true)

                                    /*// Registra o horário do check-in
                                    val currentTime = LocalDateTime.now().format(formatter)
                                    row[3] = currentTime
                                    CSVHandler.writeCSV(csvFile, csvData)

                                    // Finaliza o processo de check-in
                                    setIsCheckingIn(false)*/

                                    // Criar uma cópia dos dados para evitar problemas de recomposição
                                    val updatedData = csvData.toList().map { it.copyOf() }

                                    // Atualiza o valor do check-in
                                    val currentTime = LocalDateTime.now().format(formatter)
                                    val index = csvData.indexOf(row) // Encontra o índice na lista original
                                    if (index != -1) {
                                        updatedData[index][3] = currentTime
                                        csvData[index] = updatedData[index] // Atualiza a lista observável
                                    }

                                    // Escrever no CSV
                                    CSVHandler.writeCSV(csvFile, updatedData)

                                    setIsCheckingIn(false)

                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                )
                                ) {
                                    Text("Check-in")
                                }
                            }
                        } else {
                            Column (
                                horizontalAlignment = Alignment.End,
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Check-in realizado",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(end = 4.dp),
                                        tint = Color.Green
                                    )
                                    Text(
                                        text = "Check-in",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Text(
                                    text = checkIn,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}