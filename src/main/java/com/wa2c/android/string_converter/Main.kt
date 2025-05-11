package com.wa2c.android.string_converter

import com.wa2c.android.string_converter.repository.CsvRepository
import com.wa2c.android.string_converter.repository.ResourceRepository
import java.io.File

/**
 * CSV-XML conversion
 */
fun main(args: Array<String>) {
    val (
        inputFilePath: String,
        resourceDirPath: String,
        csvUrl: String,
        resourceName: String,
    ) = args

    val csvRepository = CsvRepository()
    val resourceRepository = ResourceRepository()

    // CSV reading
    val csvText = csvRepository.downloadCsv(csvUrl)
    File(inputFilePath).writeText(csvText, Charsets.UTF_8)
    val csvList = csvRepository.readCsv(inputFilePath)

    // CSV writing
    resourceRepository.saveMultiLanguage(
        csvUrl = csvUrl,
        csvList = csvList,
        resourceDirPath = resourceDirPath,
        resourceName = resourceName,
    )
}
