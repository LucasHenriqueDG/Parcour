package me.luhen.parcour.visual

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

object VisualUtils {

    fun convertToComponent(string: String): Component{
        return MiniMessage.miniMessage().deserialize(string)
    }

    fun sendComponent(message: String, player: Player){
        player.sendMessage(convertToComponent(message))
    }

    fun sendComponent(message: String, players: List<Player>){
        val msg = MiniMessage.miniMessage().deserialize(message)
        players.forEach{ player ->
            player.sendMessage(convertToComponent(message))
        }
    }

    fun sendActionBar(message: String, player: Player){
        val msg = MiniMessage.miniMessage().deserialize(message)
        player.sendActionBar(msg)
    }

}