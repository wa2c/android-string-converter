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

val localPropertiesFile = project.rootProject.file("local.properties")
val property = if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        val properties = Properties()
        properties.load(localPropertiesFile.inputStream())
        val spreadsheetUrl = properties.getProperty("spreadsheet_url") ?: System.getenv("spreadsheet_url") ?: ""
        val resPath = properties.getProperty("res_path") ?: System.getenv("res_path") ?: ""
        spreadsheetUrl to resPath
    }
} else {
    val spreadsheetUrl = System.getenv("spreadsheet_url") ?: ""
    val resPath = System.getenv("res_path") ?: ""
    spreadsheetUrl to resPath
}

val spreadsheetUrl = property.first
val resPath = property.second

val resAbsolutePath = File(resPath).let {
    if (it.isAbsolute) it else project.file(resPath)
}.canonicalPath

/**
 * CSV-XML conversion task.
 */
val convertString by tasks.registering(JavaExec::class) {
    group = "tools"
    classpath = java.sourceSets["main"].runtimeClasspath
    mainClass.set("com.wa2c.android.string_converter.MainKt")
    args(
        File(projectDir, "strings.csv"),
        resAbsolutePath,
        spreadsheetUrl,
    )
}
