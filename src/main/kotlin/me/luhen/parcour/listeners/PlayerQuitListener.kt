package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerLeaveParkourEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener: Listener {

    @EventHandler
    fun playerQuit(event: PlayerQuitEvent){
        if(Parcour.instance.playersPlaying.containsKey(event.player)){
            Bukkit.getPluginManager().callEvent(PlayerLeaveParkourEvent(event.player))
        }
    }

}