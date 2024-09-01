// File: src/main/kotlin/service/FileService.kt
package service
import utils.FolderHandle
import  utils.FilePath

interface FileService {
    //  Function to check if the folder exists, if not, create the folder
    fun checkAndCreateFolder(folderPath: String)
}