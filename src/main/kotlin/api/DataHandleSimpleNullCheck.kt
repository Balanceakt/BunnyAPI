package api

import service.FileService
import utils.FilePath
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

class DataHandleSimpleNullCheck(private val fileService: FileService) {

    /**
     * Checks if a specified folder exists.
     *
     * @param folder The name of the folder to check.
     * @return True if the folder exists and is a directory, otherwise false.
     */
    fun isFolderExists(folder: String): Boolean {
        val folderFile = File(FilePath.folderPath, folder)
        return folderFile.exists() && folderFile.isDirectory
    }

    /**
     * Checks if a specified table exists within a folder.
     *
     * @param folder The name of the folder containing the table.
     * @param table The name of the table to check.
     * @return True if both the folder and table exist, otherwise false.
     */
    fun isTableExists(folder: String, table: String): Boolean {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        return folderFile.exists() && settingFile.exists()
    }

    /**
     * Checks if a specified key exists within a table.
     *
     * @param folder The name of the folder containing the table.
     * @param table The name of the table.
     * @param key The key to check for existence.
     * @return True if the key exists in the table, otherwise false.
     */
    fun isKeyExists(folder: String, table: String, key: String): Boolean {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        if (!folderFile.exists() || !settingFile.exists()) {
            return false
        }
        return try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
                properties.getProperty(key) != null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Checks if a specified value exists at the given index within a table.
     *
     * @param folder The name of the folder containing the table.
     * @param table The name of the table.
     * @param key The key within the table.
     * @param indexToCheck The index of the value to check.
     * @return True if the value exists at the specified index in the table, otherwise false.
     */
    fun isValueExists(folder: String, table: String, key: String, indexToCheck: Int): Boolean {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        if (!folderFile.exists() || !settingFile.exists()) {
            return false
        }
        return try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
                val existingValues = properties.getProperty(key)
                if (existingValues != null) {
                    val currentValues = existingValues.split(",").toMutableList()
                    indexToCheck in currentValues.indices
                } else {
                    false
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Checks if a specified value exists within any key-value pair in a table.
     *
     * @param folder The name of the folder containing the table.
     * @param table The name of the table.
     * @param valueToCheck The value to check for existence.
     * @return True if the value exists in any key-value pair in the table, otherwise false.
     */
    fun isValueExistsInTable(folder: String, table: String, valueToCheck: String): Boolean {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        if (!folderFile.exists() || !settingFile.exists()) {
            return false
        }
        return try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
                properties.stringPropertyNames().any { key ->
                    properties.getProperty(key)?.contains(valueToCheck) == true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}