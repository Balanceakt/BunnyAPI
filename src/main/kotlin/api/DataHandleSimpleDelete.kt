package api

import model.FileHandle
import service.FileService
import utils.FilePath
import java.io.*
import java.util.*

class DataHandleSimpleDelete(private val fileService: FileService) {


    /**
     * Recursively deletes all contents of a given folder.
     *
     * @param folder The folder whose contents are to be deleted.
     * @return True if all contents were successfully deleted, false otherwise.
     */
    private fun deleteContents(folder: File): Boolean {
        val files = folder.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    if (!deleteContents(file)) {
                        return false
                    }
                } else {
                    if (!file.delete()) {
                        return false
                    }
                }
            }
        }
        return folder.delete()
    }

    /**
     * Deletes a value at a specified index for a given key in a properties file located in a specified folder and table.
     *
     * @param folder        The name of the folder containing the properties file.
     * @param table         The name of the properties file.
     * @param key           The key for which the value is to be removed.
     * @param indexToRemove The index of the value to remove if it's a comma-separated list.
     */
    fun deleteArgValue(folder: String, table: String, key: String, indexToRemove: Int) {
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
                    if (indexToRemove in currentValues.indices) {
                        currentValues.removeAt(indexToRemove)
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
                properties.store(output, "Updated by removeSimpleArgValueByIndex method")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Deletes a key and its associated value from a properties file located in a specified folder and table.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file.
     * @param key    The key to be deleted.
     */
    fun deleteKey(folder: String, table: String, key: String) {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        properties.remove(key)
        try {
            FileOutputStream(settingFile).use { output ->
                properties.store(output, "Updated by deleteEntry method")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Deletes a properties file located in a specified folder.
     *
     * @param folder The name of the folder containing the properties file.
     * @param table  The name of the properties file to be deleted.
     */
    fun deleteTable(folder: String, table: String) {
        val folderFile = File(FilePath.folderPath, folder)
        val fileToDelete = File(folderFile, table)
        try {
            if (fileToDelete.exists() && fileToDelete.delete()) {
                println("File deleted: ${fileToDelete.absolutePath}")
            } else {
                System.err.println("Failed to delete file: ${fileToDelete.absolutePath}")
            }
        } catch (e: SecurityException) {
            System.err.println("SecurityException: ${e.message}")
        }
    }

    /**
     * Deletes a folder and its contents located in a specified path.
     *
     * @param folder The name of the folder to be deleted.
     */
    fun deleteFolder(folder: String) {
        val folderToDelete = File(FilePath.folderPath, folder)
        try {
            if (folderToDelete.exists() && folderToDelete.isDirectory) {
                if (deleteContents(folderToDelete)) {
                    println("Folder and its contents deleted: ${folderToDelete.absolutePath}")
                } else {
                    System.err.println("Failed to delete folder and its contents: ${folderToDelete.absolutePath}")
                }
            } else {
                System.err.println("Folder not found: ${folderToDelete.absolutePath}")
            }
        } catch (e: SecurityException) {
            System.err.println("SecurityException: ${e.message}")
        }
    }

    fun deleteKeys(folder: String, table: String, key: String) {
        val folderFile = File(FilePath.folderPath, folder)
        val settingFile = File(folderFile, table)
        val properties = Properties()
        try {
            FileInputStream(settingFile).use { input ->
                properties.load(input)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val keysToRemove = properties.stringPropertyNames().filter { it.startsWith("$key.") }

        for (keyToRemove in keysToRemove) {
            properties.remove(keyToRemove)
        }

        try {
            FileOutputStream(settingFile).use { output ->
                properties.store(output, "Updated by deleteBlocks method")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}