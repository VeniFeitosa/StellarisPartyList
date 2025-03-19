package com.example.stellarispartylist.components

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stellarispartylist.screen.LoadSpreadsheetScreen
import com.example.stellarispartylist.screen.LoadingScreen
import com.example.stellarispartylist.screen.MainScreen
import com.example.stellarispartylist.screen.SettingsScreen
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController, preferencesHelper: PreferencesHelper) {
    NavHost(
        navController = navController,
        startDestination = "loadingScreen"
    ) {
        composable("loadingScreen") {
            LoadingScreen(navController)
        }
        composable("loadSpreadsheet") {
            LoadSpreadsheetScreen(navController, preferencesHelper)
        }
        composable(
            route = "mainScreen/{filePath}",
            arguments = listOf(navArgument("filePath") { type = NavType.StringType })
        ) { backStackEntry ->
            val filePath = backStackEntry.arguments?.getString("filePath")?.let { Uri.decode(it) }
            if (filePath != null) {
                val file = File(filePath)
                if (file.exists()) {
                    MainScreen(csvFile = file)
                    Toast.makeText(LocalContext.current, filePath, Toast.LENGTH_SHORT).show()
                } else {
                    // Exibir mensagem de erro: arquivo não encontrado
                    Toast.makeText(LocalContext.current, "Arquivo não encontrado!", Toast.LENGTH_SHORT).show()
                    navController.navigate("loadingScreen")
                }
            }
        }
        composable("settingsScreen") {
            SettingsScreen(preferencesHelper)
        }
    }
}