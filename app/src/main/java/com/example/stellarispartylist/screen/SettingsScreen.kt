package com.example.stellarispartylist.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stellarispartylist.components.PreferencesHelper
import com.example.stellarispartylist.components.getRealPathFromUri
import com.example.stellarispartylist.components.shareCsvFile
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SettingsScreen(preferencesHelper: PreferencesHelper, navController: NavHostController) {
    val savedCsvFilePath = preferencesHelper.getCsvFilePath()
    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    preferencesHelper.clearCsvFilePath()
                    navController.navigate("loadingScreen") {
                        popUpTo("loadingScreen") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Limpar Planilha CSV Salva")
            }
            Spacer(modifier = Modifier.height(16.dp))
            if(savedCsvFilePath !== null){
                Button(
                    onClick = {
                        shareCsvFile(context, savedCsvFilePath)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Exportar Planilha CSV")
                }
            }
        }
    }
}