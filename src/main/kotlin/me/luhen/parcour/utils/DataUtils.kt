package me.luhen.parcour.utils

import me.luhen.parcour.Parcour
import org.bukkit.Bukkit
import org.bukkit.Location


object DataUtils {

    fun updateMessages(){
        Parcour.instance.reloadConfig()
        val messages = Parcour.instance.config.getConfigurationSection("messages")
        messages?.getValues(false)?.forEach { (key, value) ->
            Parcour.instance.messages[key] = value.toString()
        }

        Parcour.instance.saveConfig()
    }

    fun getLocationFromFile(location: String): Location {
        val file = Parcour.instance.config
        return Location(
            Bukkit.getWorld(file.getString("${location}.world").toString()),
            file.getString("${location}.x")?.toDouble() ?: 0.0,
            file.getString("${location}.y")?.toDouble() ?: 0.0,
            file.getString("${location}.z")?.toDouble() ?: 0.0,
            file.getString("${location}.yaw")?.toFloat() ?: 0.0f,
            file.getString("${location}.pitch")?.toFloat() ?: 0.0f
        )

    }

    fun getCheckpoints(){

        val file = Parcour.instance.config

        file.getStringList("checkpoints").forEach{ string ->
            val key = string.split(",")
            Parcour.instance.checkPoints[Location(
                Bukkit.getWorld(file.getString("starting-location.world").toString()),
                key[0].toDouble(),
                key[1].toDouble(),
                key[2].toDouble()
            ).toBlockLocation()] = Location(
                Bukkit.getWorld(file.getString("starting-location.world").toString()),
                key[0].toDouble(),
                key[1].toDouble(),
                key[2].toDouble(),
                key[3].toFloat(),
                key[4].toFloat()
            )
        }

    }

    fun formatTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }

}