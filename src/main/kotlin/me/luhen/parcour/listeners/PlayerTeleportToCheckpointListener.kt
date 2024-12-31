package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerTeleportToCheckpointEvent
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerTeleportToCheckpointListener: Listener {

    @EventHandler
    fun teleportToCheckpoint(event: PlayerTeleportToCheckpointEvent){
        event.parcourPlayer.player.teleport(event.parcourPlayer.checkPoint)
        VisualUtils.sendComponent(Parcour.instance.messages["checkpoint-use-message"].toString(), event.parcourPlayer.player)
        event.parcourPlayer.fails += 1
    }

}