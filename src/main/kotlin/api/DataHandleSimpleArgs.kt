// File: src/main/kotlin/api/DataHandleSimpleArgs.kt
package api

import service.FileService
import utils.FilePath
import java.io.*
import java.util.*

class DataHandleSimpleArgs(private val fileService: FileService) {

    /**
     * Loads properties from a specified folder and table.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @return The loaded properties or null if an error occurs.
     */
    fun loadProperties(folder: String, table: String): Properties? {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        if (!folderFile.exists()) {
            folderFile.mkdirs()
        }
        if (!settingFile.exists()) {
            settingFile.createNewFile()
        }
        return try {
            FileInputStream(settingFile).use { input ->
                Properties().apply { load(input) }
            }
        } catch (e: IOException) {
            println("An error occurred while reading properties: ${e.message}")
            null
        }
    }

    /**
     * Saves properties to a specified folder and table.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param properties The properties to save.
     */
    fun saveProperties(folder: String, table: String, properties: Properties) {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        if (!folderFile.exists() && !folderFile.mkdirs()) {
            throw IOException("Failed to create folder: ${folderFile.absolutePath}")
        }
        if (!settingFile.exists() && !settingFile.createNewFile()) {
            throw IOException("Failed to create file: ${settingFile.absolutePath}")
        }
        try {
            FileOutputStream(settingFile).use { output ->
                properties.store(output, "Updated by DataHandleSimpleArgs")
            }
        } catch (e: IOException) {
            System.err.println("An error occurred: ${e.message}")
        }
    }

    /**
     * Reads a value from the properties file at a specified index.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to read the value for.
     * @param argIndex The index of the value to read.
     * @return The value at the specified index or null if not found.
     */
    fun readArgs(folder: String, table: String, key: String, argIndex: Int): String? {
        val properties = loadProperties(folder, table) ?: return null
        val value = properties.getProperty(key) ?: return null.also { println("Key not found: $key") }
        val valuesArray = value.split(",").toTypedArray()
        return if (argIndex in valuesArray.indices) {
            valuesArray[argIndex]
        } else {
            println("Index out of range for key: $key")
            null
        }
    }

    /**
     * Sets a single argument value in the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to set the value for.
     * @param value The value to set.
     */
    fun setArgValue(folder: String, table: String, key: String, value: String) {
        val properties = loadProperties(folder, table) ?: Properties()
        properties.setProperty(key, value)
        saveProperties(folder, table, properties)
    }

    /**
     * Sets multiple argument values in the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to set the values for.
     * @param values The list of values to set.
     */
    fun setArgsValues(folder: String, table: String, key: String, values: List<String>) {
        val properties = loadProperties(folder, table) ?: Properties()
        properties.setProperty(key, values.joinToString(","))
        saveProperties(folder, table, properties)
    }

    /**
     * Reads color codes from the properties file at a specified index.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to read the value for.
     * @param argIndex The index of the value to read.
     * @return The value with color codes converted or null if not found.
     */
    fun readColorCodes(folder: String, table: String, key: String, argIndex: Int): String? {
        val properties = loadProperties(folder, table) ?: return null
        val value = properties.getProperty(key) ?: return null.also { println("Key not found: $key") }
        val valuesArray = value.split(",").toTypedArray()
        return if (argIndex in valuesArray.indices) {
            convertColorCodes(valuesArray[argIndex])
        } else {
            println("Index out of range for key: $key")
            null
        }
    }

    /**
     * Reads all values at a specified index from the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param argIndex The index of the values to read.
     * @return A list of values at the specified index or null if an error occurs.
     */
    fun readAllArgsAtIndex(folder: String, table: String, argIndex: Int): List<String>? {
        val properties = loadProperties(folder, table) ?: return null
        val valuesAtIndex = mutableListOf<String>()
        properties.forEach { (key, value) ->
            val valuesArray = value.toString().split(",").toTypedArray()
            if (argIndex in valuesArray.indices) {
                valuesAtIndex.add(valuesArray[argIndex])
            } else {
                println("Index out of range for key: $key")
            }
        }
        return valuesAtIndex
    }

    /**
     * Reads the count of keys in the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @return The count of keys in the properties file.
     */
    fun readTableCountKeys(folder: String, table: String): Int {
        val properties = loadProperties(folder, table) ?: return 0
        return properties.size
    }

    /**
     * Reads all keys from the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @return A set of keys in the properties file or null if an error occurs.
     */
    fun readTableKeys(folder: String, table: String): Set<String>? {
        val properties = loadProperties(folder, table) ?: return null
        return properties.stringPropertyNames()
    }

    // Helper method to convert color codes
    private fun convertColorCodes(input: String): String {
        return input.replace("&", "§")
    }

    /**
     * Replaces a value at a specified index in the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to replace the value for.
     * @param indexToReplace The index of the value to replace.
     * @param newValue The new value to set.
     */
    fun replaceArgValue(folder: String, table: String, key: String, indexToReplace: Int, newValue: String) {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        if (!folderFile.exists() && !folderFile.mkdirs()) {
            System.err.println("Failed to create folder: ${folderFile.absolutePath}")
            return
        }
        if (!settingFile.exists()) {
            System.err.println("File not found: ${settingFile.absolutePath}")
            return
        }
        try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
                val existingValues = properties.getProperty(key)
                if (existingValues != null) {
                    val currentValues = existingValues.split(",").toMutableList()
                    if (indexToReplace in currentValues.indices) {
                        currentValues[indexToReplace] = newValue
                        properties.setProperty(key, currentValues.joinToString(","))
                    } else {
                        System.err.println("Index out of range for key: $key")
                        return
                    }
                } else {
                    System.err.println("Key not found: $key")
                    return
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }
        try {
            FileOutputStream(settingFile).use { output ->
                properties.store(output, "Updated by replaceArgValue method")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Reads a message with placeholders from the properties file.
     * @param folder The folder containing the properties file.
     * @param table The properties file name.
     * @param key The key to read the message for.
     * @param argIndex The index of the message to read.
     * @param placeholders The placeholders to replace in the message.
     * @return The message with placeholders replaced or null if an error occurs.
     */
    fun readMessageWithPlaceholders(folder: String, table: String, key: String, argIndex: Int, vararg placeholders: String): String? {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        try {
            if (!folderFile.exists() || !settingFile.exists()) {
                println("Folder or table does not exist: ${folderFile.absolutePath}")
                return null
            }
            FileInputStream(settingFile).use { input ->
                properties.load(input)
                val value = properties.getProperty(key)
                if (value != null) {
                    val valuesArray = value.split(",").toTypedArray()
                    if (argIndex in valuesArray.indices) {
                        val messageWithPlaceholders = valuesArray[argIndex]
                        return replacePlaceholders(messageWithPlaceholders, *placeholders)
                    } else {
                        println("Index out of range for key: $key")
                        return null
                    }
                } else {
                    println("Key not found: $key")
                    return null
                }
            }
        } catch (e: IOException) {
            println("An error occurred while reading properties: ${e.message}")
            return null
        }
    }

    /**
     * Replaces placeholders in a message with the provided replacements.
     * @param messageWithPlaceholders The message containing placeholders.
     * @param replacements The placeholders and their corresponding replacements.
     * @return The message with placeholders replaced.
     */
    private fun replacePlaceholders(messageWithPlaceholders: String, vararg replacements: String): String {
        var result = messageWithPlaceholders
        for (i in replacements.indices step 2) {
            if (i + 1 < replacements.size) {
                val placeholder = replacements[i]
                val replacement = replacements[i + 1]
                result = result.replace(placeholder, replacement)
            }
        }
        return result
    }
}