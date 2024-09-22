// src/main/kotlin/dev/motomoto/bunnyAPI/BunnyAPI.kt
package dev.motomoto.bunnyAPI

import org.bukkit.plugin.java.JavaPlugin
import api.DataHandleSimpleArgs
import api.DataHandleSimpleDelete
import api.DataHandleSimpleNullCheck
import api.MySQLSimpleHandle
import service.FileService
import service.FileServiceImpl

class BunnyAPI : JavaPlugin() {

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

    override fun onEnable() {
        instance = this
        // Plugin startup logic
        logger.info("BunnyAPI has been enabled!")
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("BunnyAPI has been disabled!")
        instance = null
    }

    fun retrieveFileService(): FileService {
        return fileService
    }
}