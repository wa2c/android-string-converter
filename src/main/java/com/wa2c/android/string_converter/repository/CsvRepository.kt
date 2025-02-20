package com.wa2c.android.string_converter.repository

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.wa2c.android.string_converter.model.CsvRow
import java.io.FileInputStream
import java.net.URI
import java.nio.charset.Charset

/**
 * CSV file repository
 */
class CsvRepository {

    private val parser = CSVParserBuilder()
        .withEscapeChar(Char.MIN_VALUE)
        .build()

    fun downloadCsv(url: String): String {
        return URI(url).toURL().openStream().bufferedReader(charset = Charsets.UTF_8).use { input ->
            input.readText()
        }
    }

    fun readCsv(csvFilePath: String): List<CsvRow> {
        return FileInputStream(csvFilePath).reader(CSV_CHARSET).use { reader ->
            val rawList = CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .build()
                .use { it.readAll() }

            val codeMap = rawList[2].drop(2).mapIndexed { index, code -> index to code }.toMap()
            val nameRow = CsvRow(
                title = "",
                resourceId = "",
                langText = rawList[1].drop(2).mapIndexed { index, text -> codeMap[index]!! to text }.toMap(),
            )

            rawList
                .drop(3)
                .map { row ->
                    // Data
                    val langText = row.drop(2).mapIndexed { index, text -> codeMap[index]!! to text }.toMap()
                    CsvRow(
                        title = row[0],
                        resourceId = row[1],
                        langText = langText,
                    )
                }.let {
                    listOf(nameRow) + it
                }
        }
    }

    companion object {
        private val CSV_CHARSET = Charset.forName("UTF-8")
    }
}
