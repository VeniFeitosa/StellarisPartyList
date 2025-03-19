package com.example.stellarispartylist.components

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("StellarisPartyListPrefs", Context.MODE_PRIVATE)

    // Salva o caminho do arquivo CSV
    fun saveCsvFilePath(filePath: String) {
        sharedPreferences.edit().putString("csvFilePath", filePath).apply()
    }

    // Obtém o caminho do arquivo CSV
    fun getCsvFilePath(): String? {
        return sharedPreferences.getString("csvFilePath", null)
    }

    // Limpa o caminho do arquivo CSV (opcional)
    fun clearCsvFilePath() {
        sharedPreferences.edit().remove("csvFilePath").apply()
    }
}