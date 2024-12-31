package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerResetParkourEvent
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerResetParkourListener: Listener {

    @EventHandler
    fun resetParkour(event: PlayerResetParkourEvent){
        event.parcourPlayer.timerTask.reset()
        event.parcourPlayer.fails = 0
        Parcour.instance.startingLocation?.let {
            event.parcourPlayer.checkPoint = it
            event.parcourPlayer.player.teleport(it)
        }
        event.parcourPlayer.startingTime = System.currentTimeMillis()
        VisualUtils.sendComponent(Parcour.instance.messages["reset-message"].toString(), event.parcourPlayer.player)
    }

}