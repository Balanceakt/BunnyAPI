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

    override fun onEnable() {
        instance = this
        // Plugin-Startlogik
        logger.info("BunnyAPI wurde aktiviert!")
    }

    override fun onDisable() {
        // Plugin-Abschaltlogik
        logger.info("BunnyAPI wurde deaktiviert!")
    }

    companion object {
        lateinit var instance: BunnyAPI
            private set
    }
}