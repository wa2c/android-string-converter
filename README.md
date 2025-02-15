Android String Converter Tool
=============================

## About

A tool for generating string resources for Android apps.

## Usage

### Create Spreadsheet

Create string resources spreadsheet in Google Drive.
Share the spreadsheet with everyone.

[Format sample](https://docs.google.com/spreadsheets/d/1y71DyM31liwjcAUuPIk3CuIqxJD2l9Y2Q-YZ0I0XE_E/edit?gid=0#gid=0)

### Add local.properties

Add properties in local.properties.

```
res_path={{App res folder path}}
# Absolute path or relative path from module directory
# Sample: ../../presentation/src/main/res/

spreadsheet_url={{Spreadsheet CSV download URL}}
# Add "/export?format=csv" to spreadsheet url last path
# Sample: https://docs.google.com/spreadsheets/d/1y71DyM31liwjcAUuPIk3CuIqxJD2l9Y2Q-YZ0I0XE_E/export?format=csv#gid=0
```

### Command

Run the command in module directory.

```sh
# In module directory
gradlew convertString
```

## Licence

Copyright &copy; 2025 wa2c [MIT License](https://github.com/wa2c/android-string-converter/blob/main/LICENSE)

## Author

[wa2c](https://github.com/wa2c)
