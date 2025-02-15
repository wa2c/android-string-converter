package com.wa2c.android.string_converter.model

/**
 * CSV Row
 */
data class CsvRow(
    /** Title */
    val title: String,
    /** String resource ID */
    val resourceId: String,
    /** Language text map (key: lang code, value: text) */
    val langText: Map<String, String>,
)
