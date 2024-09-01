// File: src/main/kotlin/utils/FilePath.kt
package utils

import java.io.File

class FilePath {
    companion object {
        // The folder path is set to the parent directory of the project
        val folderPath: String =
            System.getProperty("user.dir") + File.separator + ".." + File.separator + ".." + File.separator + "DATASTORE"
    }
}