package com.example.stellarispartylist.components

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CSVHandler {
    fun readCSV(file: File): List<Array<String>> {
        return CSVReader(FileReader(file)).use { reader ->
            reader.readAll()
        }
    }

    fun writeCSV(file: File, data: List<Array<String>>) {
        CSVWriter(FileWriter(file, false)).use { writer -> // 'false' garante que sobrescreva corretamente
            writer.writeAll(data)
        }
    }

}