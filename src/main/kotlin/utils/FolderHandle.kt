// File: src/main/kotlin/utils/FolderHandle.kt
package utils

import java.io.File

object FolderHandle {
    // Function to check if the folder exists, if not, create the folder
    @JvmStatic
    fun folderCheck(folderPath: String) {
        val folder = File(folderPath)
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                println("Folder created: $folderPath")
            } else {
                System.err.println("Failed to create folder: $folderPath")
            }
        }
    }
}