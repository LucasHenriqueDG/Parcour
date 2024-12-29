package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerReachCheckpointEvent
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerReachCheckpointListener: Listener {

    @EventHandler
    fun playerReachCheckpoint(event: PlayerReachCheckpointEvent){
        event.parcourPlayer.CheckPoint = event.location
        VisualUtils.sendComponent(Parcour.instance.messages["checkpoint-reached-message"].toString(), event.parcourPlayer.player)
    }

}