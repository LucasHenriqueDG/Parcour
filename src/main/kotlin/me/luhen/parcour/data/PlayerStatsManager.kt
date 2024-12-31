package me.luhen.parcour.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.luhen.parcour.Parcour
import org.bukkit.entity.Player
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.util.UUID

class PlayerStatsManager {

    private val gson = Gson()
    private var jsonFilePath: String? = null
    private var connection: Connection? = null

    constructor(jsonFilePath: String) {
        this.jsonFilePath = jsonFilePath
        val file = File(Parcour.instance.dataFolder, jsonFilePath)
        if(!file.exists()){
            file.createNewFile()
        }
    }

    constructor(host: String, port: Int, database: String, user: String, password: String) {
        val url = "jdbc:mysql://${host}:${port}/${database}?enabledTLSProtocols=TLSv1.2"
        connection = DriverManager.getConnection(url, user, password)
        initializeDatabase()
    }

    private fun initializeDatabase() {
        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS player_parcour_stats (
                uuid VARCHAR(36) PRIMARY KEY,
                best_time BIGINT NOT NULL,
            )
        """
        connection?.createStatement()?.executeUpdate(createTableSQL)
    }

    fun readStats(): List<PlayerStatsData> {
        return if (jsonFilePath != null) {
            readStatsFromJson()
        } else {
            readStatsFromMySQL()
        }
    }

    fun getPlayerStats(player: Player): PlayerStatsData?{
        val data = readStats()
        return data.find { it.uuid == player.uniqueId }
    }

    fun updateStats(newData: PlayerStatsData) {
        if (jsonFilePath != null) {
            updateStatsInJson(newData)
        } else {
            updateStatsInMySQL(newData)
        }
    }

    private fun readStatsFromJson(): MutableList<PlayerStatsData> {
        jsonFilePath?.let {
            val file = File(Parcour.instance.dataFolder, it)
            if (!file.exists()) return mutableListOf()

            val json = file.readText()
            val type = object : TypeToken<MutableList<PlayerStatsData>>() {}.type
            return gson.fromJson(json, type) ?: mutableListOf()
        } ?: return mutableListOf()
    }

    private fun updateStatsInJson(newData: PlayerStatsData) {

        val stats = readStatsFromJson()

        val existingPlayer = stats.find { it.uuid == newData.uuid }
        if (existingPlayer != null) {
            if (newData.bestTime < existingPlayer.bestTime) {
                existingPlayer.bestTime = newData.bestTime
            }
        } else {
            stats.add(newData)
        }

        val json = gson.toJson(stats)
        jsonFilePath?.let { File(Parcour.instance.dataFolder, it).writeText(json) }

    }

    private fun readStatsFromMySQL(): MutableList<PlayerStatsData> {

        val stats = mutableListOf<PlayerStatsData>()
        val query = "SELECT uuid, best_time FROM player_parcour_stats"
        val statement = connection?.createStatement()
        val resultSet = statement?.executeQuery(query)

        while (resultSet?.next() == true) {
            stats.add(
                PlayerStatsData(
                    uuid = UUID.fromString(resultSet.getString("uuid")),
                    bestTime = resultSet.getLong("best_time")
                )
            )
        }
        return stats

    }

    private fun updateStatsInMySQL(newData: PlayerStatsData) {

        val querySelect = "SELECT best_time FROM player_parcour_stats WHERE uuid = ?"
        val statementSelect = connection?.prepareStatement(querySelect)
        statementSelect?.setString(1, newData.uuid.toString())
        val resultSet = statementSelect?.executeQuery()

        if (resultSet?.next() == true) {
            val currentBestTime = resultSet.getLong("best_time")

            val updatedBestTime = minOf(currentBestTime, newData.bestTime)

            val queryUpdate = """
                UPDATE player_parcour_stats 
                SET best_time = ?
                WHERE uuid = ?
            """
            val statementUpdate = connection?.prepareStatement(queryUpdate)
            statementUpdate?.setLong(1, updatedBestTime)
            statementUpdate?.setString(3, newData.uuid.toString())
            statementUpdate?.executeUpdate()
        } else {
            val queryInsert = """
                INSERT INTO player_parcour_stats (uuid, best_time) 
                VALUES (?, ?, ?)
            """
            val statementInsert = connection?.prepareStatement(queryInsert)
            statementInsert?.setString(1, newData.uuid.toString())
            statementInsert?.setLong(2, newData.bestTime)
            statementInsert?.executeUpdate()
        }

    }

}
