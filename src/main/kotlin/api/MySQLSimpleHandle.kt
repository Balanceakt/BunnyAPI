package api

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

/**
 * A simple MySQL handler for basic database operations.
 */
class MySQLSimpleHandle {
    private var connection: Connection? = null

    init {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            println("MySQL Driver not found: ${e.message}")
        }
    }

    /**
     * Connects to the MySQL database.
     * @param host The database host.
     * @param database The database name.
     * @param port The database port.
     * @param user The database user.
     * @param password The database password.
     */
    fun connect(host: String, database: String, port: String, user: String, password: String) {
        if (connection != null && !connection!!.isClosed) {
            println("Already connected to the database")
            return
        }
        val url = "jdbc:mysql://$host:$port/$database"
        try {
            connection = DriverManager.getConnection(url, user, password)
            println("Connected to the database")
        } catch (e: SQLException) {
            println("Connection failed: ${e.message}")
        }
    }

    /**
     * Creates a database if it does not exist.
     * @param databaseName The name of the database.
     */
    fun createDatabase(databaseName: String) {
        val sql = "CREATE DATABASE IF NOT EXISTS $databaseName"
        executeUpdateWithMessage(sql)
    }

    /**
     * Drops a database if it exists.
     * @param databaseName The name of the database to drop.
     */
    fun deleteDatabase(databaseName: String) {
        val sql = "DROP DATABASE IF EXISTS $databaseName"
        executeUpdateWithMessage(sql)
    }

    /**
     * Creates a table if it does not exist.
     * @param tableName The name of the table.
     * @param columns The columns of the table.
     */
    fun createTable(tableName: String, vararg columns: String) {
        val columnsDefinition = columns.joinToString(", ") { column ->
            val parts = column.split(" ")
            if (parts.size >= 2) {
                val columnName = parts[0]
                val columnType = mapToSQLType(parts[1])
                val columnConstraints = parts.drop(2).joinToString(" ")
                "$columnName $columnType $columnConstraints".trim()
            } else {
                throw IllegalArgumentException("Invalid column definition: $column")
            }
        }
        val sql = "CREATE TABLE IF NOT EXISTS $tableName ($columnsDefinition)"
        executeUpdateWithMessage(sql)
    }

    private fun mapToSQLType(type: String): String {
        return when (type) {
            "String" -> "VARCHAR(255)"
            "Int" -> "INT"
            "Boolean" -> "BOOLEAN"
            else -> throw IllegalArgumentException("Unsupported type: $type")
        }
    }

    /**
     * Drops a table if it exists.
     * @param tableName The name of the table to drop.
     */
    fun deleteTable(tableName: String) {
        val sql = "DROP TABLE IF EXISTS $tableName"
        executeUpdateWithMessage(sql)
    }

    /**
     * Inserts data into a table.
     * @param tableName The name of the table.
     * @param columns The columns to insert data into.
     * @param values The values to insert.
     */
    fun insertData(tableName: String, columns: String, values: String) {
        val sql = "INSERT INTO $tableName ($columns) VALUES ($values)"
        executeUpdateWithMessage(sql)
    }

    /**
     * Deletes data from a table based on a condition.
     * @param tableName The name of the table.
     * @param condition The condition for deleting data.
     */
    fun deleteData(tableName: String, condition: String) {
        val sql = "DELETE FROM $tableName WHERE $condition"
        executeUpdateWithMessage(sql)
    }

    /**
     * Updates data in a table based on a condition.
     * @param tableName The name of the table.
     * @param setClause The SET clause for updating data.
     * @param condition The condition for updating data.
     */
    fun updateData(tableName: String, setClause: String, condition: String) {
        val sql = "UPDATE $tableName SET $setClause WHERE $condition"
        executeUpdateWithMessage(sql)
    }

    /**
     * Reads all data from a table.
     * @param tableName The name of the table.
     * @return A ResultSet containing all rows from the table.
     */
    fun readAllData(tableName: String): ResultSet? {
        val sql = "SELECT * FROM $tableName"
        return executeQueryWithMessage(sql)
    }

    /**
     * Searches for data in a table based on a search term.
     * @param tableName The name of the table.
     * @param column The column to search in.
     * @param searchTerm The term to search for.
     * @return True if the data exists, false otherwise.
     */
    fun searchData(tableName: String, column: String, searchTerm: String): Boolean {
        val sql = "SELECT $column FROM $tableName WHERE $column LIKE '%$searchTerm%' LIMIT 1"
        return try {
            connection?.createStatement()?.use { statement ->
                val resultSet = statement.executeQuery(sql)
                resultSet.next()
            } ?: false
        } catch (e: SQLException) {
            println("Query failed: ${e.message}")
            false
        }
    }

    private fun executeUpdateWithMessage(sql: String) {
        try {
            connection?.createStatement()?.use { statement ->
                statement.executeUpdate(sql)
                println("Operation successful")
            }
        } catch (e: SQLException) {
            println("Operation failed: ${e.message}")
        }
    }

    private fun executeQueryWithMessage(sql: String): ResultSet? {
        return try {
            connection?.createStatement()?.executeQuery(sql)
        } catch (e: SQLException) {
            println("Query failed: ${e.message}")
            null
        }
    }

    /**
     * Closes the database connection.
     */
    fun close() {
        try {
            connection?.close()
            println("Connection closed")
        } catch (e: SQLException) {
            println("Failed to close connection: ${e.message}")
        }
    }
}