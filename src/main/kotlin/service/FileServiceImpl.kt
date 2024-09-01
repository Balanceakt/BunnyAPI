// File: src/main/kotlin/service/FileServiceImpl.kt
// File: src/main/kotlin/service/FileServiceImpl.kt
package service

import utils.FolderHandle

class FileServiceImpl : FileService {
    override fun checkAndCreateFolder(folderPath: String) {
        FolderHandle.folderCheck(folderPath)
    }
}