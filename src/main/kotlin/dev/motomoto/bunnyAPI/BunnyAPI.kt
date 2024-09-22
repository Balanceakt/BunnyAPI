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
    val fileService: FileService = FileServiceImpl()
    val dataHandleSimpleArgs: DataHandleSimpleArgs = DataHandleSimpleArgs(fileService)
    val dataHandleSimpleDelete: DataHandleSimpleDelete = DataHandleSimpleDelete(fileService)
    val dataHandleSimpleNullCheck: DataHandleSimpleNullCheck = DataHandleSimpleNullCheck(fileService)
    val mySQLSimpleHandle: MySQLSimpleHandle = MySQLSimpleHandle()

    // Map zur Speicherung der Instanzen
    private val instances: MutableMap<String, Any> = mutableMapOf()

    init {
        // Speichere die Instanzen in der Map
        instances[DataHandleSimpleArgs::class.java.simpleName] = dataHandleSimpleArgs
        instances[DataHandleSimpleDelete::class.java.simpleName] = dataHandleSimpleDelete
        instances[DataHandleSimpleNullCheck::class.java.simpleName] = dataHandleSimpleNullCheck
        instances[MySQLSimpleHandle::class.java.simpleName] = mySQLSimpleHandle
    }

    companion object {
        @Volatile
        private var instance: BunnyAPI? = null

        // Stelle sicher, dass die API von anderen Plugins aufgerufen werden kann
        fun getInstance(): BunnyAPI =
            instance ?: synchronized(this) {
                instance ?: BunnyAPI().also { instance = it }
            }
    }

    override fun onEnable() {
        instance = this
        init()
        logger.info("BunnyAPI has been enabled!")
    }

    override fun onDisable() {
        logger.info("BunnyAPI has been disabled!")
        instance = null
    }

    // Initialisiere die API und registriere alle Klassen
    private fun init() {
        registerClass(dataHandleSimpleArgs)
        registerClass(dataHandleSimpleDelete)
        registerClass(dataHandleSimpleNullCheck)
        registerClass(mySQLSimpleHandle)
    }

    // Registriere eine Klasse und gib eine Logmeldung aus
    private fun registerClass(instance: Any) {
        logger.info("BunnyAPI: Registered - ${instance.javaClass.name}")
    }

    // Beispielmethoden zum Abrufen von Instanzen
    fun argsInstance(): DataHandleSimpleArgs {
        return instances[DataHandleSimpleArgs::class.java.simpleName] as DataHandleSimpleArgs
    }

    fun deleteInstance(): DataHandleSimpleDelete {
        return instances[DataHandleSimpleDelete::class.java.simpleName] as DataHandleSimpleDelete
    }

    fun nullCheckInstance(): DataHandleSimpleNullCheck {
        return instances[DataHandleSimpleNullCheck::class.java.simpleName] as DataHandleSimpleNullCheck
    }

    fun mySQLInstance(): MySQLSimpleHandle {
        return instances[MySQLSimpleHandle::class.java.simpleName] as MySQLSimpleHandle
    }

    fun retrieveFileService(): FileService {
        return fileService
    }
}
