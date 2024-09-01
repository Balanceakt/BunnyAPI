package dev.motomoto.bunnyAPI

import api.DataHandleSimpleArgs
import api.DataHandleSimpleDelete
import api.DataHandleSimpleNullCheck
import api.MySQLSimpleHandle
import service.FileService
import service.FileServiceImpl

class BunnyAPI public constructor() {

    val fileService: FileService = FileServiceImpl()
    val dataHandleSimpleArgs: DataHandleSimpleArgs = DataHandleSimpleArgs(fileService)
    val dataHandleSimpleDelete: DataHandleSimpleDelete = DataHandleSimpleDelete(fileService)
    val dataHandleSimpleNullCheck: DataHandleSimpleNullCheck = DataHandleSimpleNullCheck(fileService)
    val mySQLSimpleHandle: MySQLSimpleHandle = MySQLSimpleHandle()

    companion object {
        @Volatile
        private var instance: BunnyAPI? = null

        fun getInstance(): BunnyAPI =
            instance ?: synchronized(this) {
                instance ?: BunnyAPI().also { instance = it }
            }
    }

    fun retrieveFileService(): FileService {
        return fileService
    }
}