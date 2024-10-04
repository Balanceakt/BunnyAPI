package dev.motomoto.bunnyAPI

import org.bukkit.plugin.java.JavaPlugin
import api.DataHandleSimpleArgs
import api.DataHandleSimpleDelete
import api.DataHandleSimpleNullCheck
import api.MySQLSimpleHandle
import service.FileService
import service.FileServiceImpl

class BunnyAPI : JavaPlugin() {

    // Initialisiere die Services und Handler
    private lateinit var fileService: FileService
    private lateinit var dataHandleSimpleArgs: DataHandleSimpleArgs
    private lateinit var dataHandleSimpleDelete: DataHandleSimpleDelete
    private lateinit var dataHandleSimpleNullCheck: DataHandleSimpleNullCheck
    private lateinit var mySQLSimpleHandle: MySQLSimpleHandle

    companion object {
        @Volatile
        private var instance: BunnyAPI? = null

        // Zugriff auf die Instanz der BunnyAPI
        fun getInstance(): BunnyAPI =
            instance ?: throw IllegalStateException("BunnyAPI is not initialized")

        // Direkter Zugriff auf die Handler
        fun argsInstance(): DataHandleSimpleArgs {
            return getInstance().dataHandleSimpleArgs
        }

        fun deleteInstance(): DataHandleSimpleDelete {
            return getInstance().dataHandleSimpleDelete
        }

        fun nullCheckInstance(): DataHandleSimpleNullCheck {
            return getInstance().dataHandleSimpleNullCheck
        }

        fun mySQLInstance(): MySQLSimpleHandle {
            return getInstance().mySQLSimpleHandle
        }

        fun retrieveFileService(): FileService {
            return getInstance().fileService
        }
    }

    override fun onEnable() {
        // Initialisiere die Dienste in onEnable()
        instance = this
        fileService = FileServiceImpl()
        dataHandleSimpleArgs = DataHandleSimpleArgs(fileService)
        dataHandleSimpleDelete = DataHandleSimpleDelete(fileService)
        dataHandleSimpleNullCheck = DataHandleSimpleNullCheck(fileService)
        mySQLSimpleHandle = MySQLSimpleHandle()

        logger.info("BunnyAPI has been enabled!")
    }

    override fun onDisable() {
        logger.info("BunnyAPI has been disabled!")
        instance = null
    }
}
