package com.example.stellarispartylist.components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.database.Cursor
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