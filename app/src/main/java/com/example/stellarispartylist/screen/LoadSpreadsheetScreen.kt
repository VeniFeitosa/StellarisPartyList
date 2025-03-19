package com.example.stellarispartylist.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.stellarispartylist.components.PreferencesHelper
import com.example.stellarispartylist.components.getRealPathFromUri
import java.io.File

@Composable
fun LoadSpreadsheetScreen(navController: NavController, preferencesHelper: PreferencesHelper) {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val filePath = context.getRealPathFromUri(it)
            if (filePath != null) {
                val file = File(filePath)
                if (file.exists()) {
                    // Salva o caminho do arquivo
                    preferencesHelper.saveCsvFilePath(file.absolutePath)

                    // Navega para a MainScreen com o arquivo CSV carregado
                    val encodedFilePath = Uri.encode(file.absolutePath)
                    navController.navigate("mainScreen/$encodedFilePath") {
                        popUpTo("loadingScreen") { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { filePickerLauncher.launch("text/csv") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )

        ) {
            Text("Selecionar Planilha CSV")
        }
    }
}