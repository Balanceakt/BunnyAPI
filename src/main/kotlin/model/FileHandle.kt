// File: src/main/kotlin/model/FileHandle.kt
package model

import service.FileService
import utils.FilePath

abstract class FileHandle(private val fileService: FileService) {
    init {
        // Check if the folder exists, if not, create the folder
        fileService.checkAndCreateFolder(FilePath.folderPath)
    }
}