package dev.motomoto.bunnyAPI

import org.bukkit.Bukkit

class BunnyAPITest {
    fun testReadingArgs() {
        // Teste das Lesen von Argumenten
        try {
            val result = BunnyAPI.argsInstance().setArgValue("1", "1", "1", "1")
            Bukkit.getLogger().info("Test Reading Args: Result: $result")
        } catch (e: Exception) {
            Bukkit.getLogger().severe("Error during test: ${e.message}")
        }
    }
}
