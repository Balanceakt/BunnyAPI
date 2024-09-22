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
    private val fileService: FileService = FileServiceImpl()
    private val dataHandleSimpleArgs: DataHandleSimpleArgs = DataHandleSimpleArgs(fileService)
    private val dataHandleSimpleDelete: DataHandleSimpleDelete = DataHandleSimpleDelete(fileService)
    private val dataHandleSimpleNullCheck: DataHandleSimpleNullCheck = DataHandleSimpleNullCheck(fileService)
    private val mySQLSimpleHandle: MySQLSimpleHandle = MySQLSimpleHandle()

    companion object {
        @Volatile
        private var instance: BunnyAPI? = null

        // Zugriff auf die Instanz der BunnyAPI
        fun getInstance(): BunnyAPI =
            instance ?: synchronized(this) {
                instance ?: BunnyAPI().also { instance = it }
            }

        // Direkter Zugriff auf die Handler
        fun argsInstance(): DataHandleSimpleArgs {
            return instance?.dataHandleSimpleArgs ?: throw IllegalStateException("BunnyAPI is not initialized")
        }

        fun deleteInstance(): DataHandleSimpleDelete {
            return instance?.dataHandleSimpleDelete ?: throw IllegalStateException("BunnyAPI is not initialized")
        }

        fun nullCheckInstance(): DataHandleSimpleNullCheck {
            return instance?.dataHandleSimpleNullCheck ?: throw IllegalStateException("BunnyAPI is not initialized")
        }

        fun mySQLInstance(): MySQLSimpleHandle {
            return instance?.mySQLSimpleHandle ?: throw IllegalStateException("BunnyAPI is not initialized")
        }

        fun retrieveFileService(): FileService {
            return instance?.fileService ?: throw IllegalStateException("BunnyAPI is not initialized")
        }
    }

    override fun onEnable() {
        instance = this
        logger.info("BunnyAPI has been enabled!")
    }

    override fun onDisable() {
        logger.info("BunnyAPI has been disabled!")
        instance = null
    }
}
