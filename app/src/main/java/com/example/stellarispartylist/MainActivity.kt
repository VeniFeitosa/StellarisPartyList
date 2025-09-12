package com.example.stellarispartylist

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.stellarispartylist.components.AppNavigation
import com.example.stellarispartylist.components.PreferencesHelper
import com.example.stellarispartylist.components.getRealPathFromUri
import com.example.stellarispartylist.ui.theme.StellarisPartyListTheme
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var preferencesHelper: PreferencesHelper
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializa o PreferencesHelper
        preferencesHelper = PreferencesHelper(this)

        setContent {
            StellarisPartyListTheme {
                // Surface é um contêiner que aplica o tema escuro
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicializa o controlador de navegação
                    val navController = rememberNavController()
                    val context = LocalContext.current

                    // Verifica se há um arquivo CSV salvo
                    val savedCsvFilePath = preferencesHelper.getCsvFilePath()
                    if (savedCsvFilePath != null) {
                        LaunchedEffect(Unit) {
                            // Aguarda o NavController estar pronto
                            navController.addOnDestinationChangedListener { _, _, _ -> }

                            // Navega para a MainScreen com o arquivo CSV carregado
                            val encodedFilePath = Uri.encode(savedCsvFilePath)
                            navController.navigate("mainScreen/$encodedFilePath") {
                                popUpTo("loadingScreen") { inclusive = true }
                            }
                        }
                    }

                    // Verifica se a atividade foi iniciada com um Intent de compartilhamento
                    val sharedFileUri = intent?.data
                    if (sharedFileUri != null) {
                        LaunchedEffect(Unit) {
                            // Converte o Uri para um arquivo e carrega o CSV
                            val file = File(context.getRealPathFromUri(sharedFileUri))
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
                    // Navegação normal (sem arquivo compartilhado)
                    AppNavigation(navController, preferencesHelper)

                }
            }
        }
    }
}