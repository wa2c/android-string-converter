import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm") version "2.1.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.opencsv:opencsv:5.10")
    implementation("org.redundent:kotlin-xml-builder:1.9.1")
}

private val spreadsheetUrlKey = "string_converter_spreadsheet_url"
private val resPathKey = "string_converter_res_path"
private val resNameKey = "string_converter_res_name"

/**
 * CSV-XML conversion task.
 */
val convertString by tasks.registering(JavaExec::class) {
    group = "tools"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("com.wa2c.android.string_converter.MainKt")

    // Get parameters
    val localPropertiesFile = project.file("local.properties")
    val property = if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use {
            val properties = Properties()
            properties.load(it)
            properties
        }
    } else {
        null
    }
    // Spreadsheet URL
    val spreadsheetUrl = (
            property?.getProperty(spreadsheetUrlKey)
                ?: System.getenv(spreadsheetUrlKey)
                ?: System.getProperty(spreadsheetUrlKey)
            ) ?: ""
    // res folder path
    val resPath = (
            property?.getProperty(resPathKey)
                ?: System.getenv(resPathKey)
                ?: System.getProperty(resPathKey)
            )?.let {
            File(it).let {
                if (it.isAbsolute) it else project.file(it)
            }.canonicalPath
        } ?: ""

    val resName = (
            property?.getProperty(resNameKey)
                ?: System.getenv(resNameKey)
                ?: System.getProperty(resNameKey)
            ) ?: "strings"

    args(
        File(projectDir, "strings.csv"),
        resPath,
        spreadsheetUrl,
        resName,
    )
}
