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
            instance ?: throw IllegalStateException("BunnyAPI is not initialized")

        fun initialize(plugin: BunnyAPI) {
            synchronized(this) {
                if (instance == null) {
                    instance = plugin
                } else {
                    throw IllegalStateException("BunnyAPI is already initialized")
                }
            }
        }

        fun clearInstance() {
            instance = null
        }
    }

    override fun onEnable() {
        if (instance == null) {
            initialize(this)
            // Plugin startup logic
            logger.info("BunnyAPI has been enabled!")
        } else {
            logger.warning("BunnyAPI is already initialized!")
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("BunnyAPI has been disabled!")
        clearInstance()
    }

    fun retrieveFileService(): FileService {
        return fileService
    }
}