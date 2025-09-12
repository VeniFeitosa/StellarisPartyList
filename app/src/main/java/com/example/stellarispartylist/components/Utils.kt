package com.example.stellarispartylist.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.database.Cursor
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun Context.getRealPathFromUri(uri: Uri): String? {
    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        it.moveToFirst()
        val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (displayNameIndex == -1) {
            // Se a coluna não existir, tenta obter o nome do arquivo da URI
            uri.lastPathSegment?.let { path ->
                val file = File(cacheDir, path)
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                return file.absolutePath
            }
            return null
        }

        // Se a coluna existir, obtém o nome do arquivo
        val displayName: String = it.getString(displayNameIndex)
        val file = File(cacheDir, displayName)
        contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath
    }
}

fun shareCsvFile(context: Context, filePath: String) {
    val file = File(filePath)
    if (file.exists()) {
        // Cria um URI para o arquivo usando FileProvider
        val fileUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // Deve corresponder ao authority no XML
            file
        )

        // Cria o Intent para compartilhar o arquivo
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv" // Tipo MIME do arquivo
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Concede permissão de leitura
        }

        // Inicia a atividade de compartilhamento
        context.startActivity(Intent.createChooser(shareIntent, "Compartilhar CSV"))
    } else {
        Toast.makeText(context, "Arquivo não encontrado!", Toast.LENGTH_SHORT).show()
    }
}