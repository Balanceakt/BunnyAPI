package dev.motomoto.bunnyAPI

import org.bukkit.plugin.java.JavaPlugin
import api.DataHandleSimpleArgs
import api.DataHandleSimpleDelete
import api.DataHandleSimpleNullCheck
import api.MySQLSimpleHandle
import service.FileService
import service.FileServiceImpl
import java.util.concurrent.ConcurrentHashMap

class BunnyAPI : JavaPlugin() {

    // Initialisiere die Services und Handler
    private lateinit var fileService: FileService
    private lateinit var dataHandleSimpleArgs: DataHandleSimpleArgs
    private lateinit var dataHandleSimpleDelete: DataHandleSimpleDelete
    private lateinit var dataHandleSimpleNullCheck: DataHandleSimpleNullCheck
    private lateinit var mySQLSimpleHandle: MySQLSimpleHandle

    // HashMap zum Speichern von Instanzen
    private val instances: MutableMap<String, Any> = ConcurrentHashMap()

    companion object {
        @Volatile
        private var instance: BunnyAPI? = null

        fun getInstance(): BunnyAPI =
            instance ?: throw IllegalStateException("BunnyAPI is not initialized")

        fun argsInstance(): DataHandleSimpleArgs {
            return getInstance().instances["DataHandleSimpleArgs"] as DataHandleSimpleArgs
        }

        fun deleteInstance(): DataHandleSimpleDelete {
            return getInstance().instances["DataHandleSimpleDelete"] as DataHandleSimpleDelete
        }

        fun nullCheckInstance(): DataHandleSimpleNullCheck {
            return getInstance().instances["DataHandleSimpleNullCheck"] as DataHandleSimpleNullCheck
        }

        fun mySQLInstance(): MySQLSimpleHandle {
            return getInstance().instances["MySQLSimpleHandle"] as MySQLSimpleHandle
        }

        fun retrieveFileService(): FileService {
            return getInstance().instances["FileService"] as FileService
        }
    }

    override fun onEnable() {
        instance = this
        fileService = FileServiceImpl()
        dataHandleSimpleArgs = DataHandleSimpleArgs(fileService)
        dataHandleSimpleDelete = DataHandleSimpleDelete(fileService)
        dataHandleSimpleNullCheck = DataHandleSimpleNullCheck(fileService)
        mySQLSimpleHandle = MySQLSimpleHandle()

        instances["FileService"] = fileService
        instances["DataHandleSimpleArgs"] = dataHandleSimpleArgs
        instances["DataHandleSimpleDelete"] = dataHandleSimpleDelete
        instances["DataHandleSimpleNullCheck"] = dataHandleSimpleNullCheck
        instances["MySQLSimpleHandle"] = mySQLSimpleHandle

        logger.info("BunnyAPI has been enabled!")
    }

    override fun onDisable() {
        logger.info("BunnyAPI has been disabled!")
        instance = null
        instances.clear()
    }
}
