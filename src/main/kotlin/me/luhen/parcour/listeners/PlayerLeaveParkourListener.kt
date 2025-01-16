package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.enums.LeaveType
import me.luhen.parcour.listeners.customevents.PlayerLeaveParkourEvent
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerLeaveParkourListener: Listener {

    @EventHandler
    fun playerLeaveParkour(event: PlayerLeaveParkourEvent){
        if(Parcour.instance.playersPlaying.containsKey(event.player)){
            VisualUtils.sendComponent(Parcour.instance.messages["left-parkour"].toString(), event.player)
            val parcourPlayer = Parcour.instance.playersPlaying[event.player]
            parcourPlayer?.timerTask?.cancel()
            Parcour.instance.exitLocation?.let{
                if(event.type == LeaveType.REGULAR){
                event.player.teleport(it)
                    }
            }
            Parcour.instance.playersPlaying.remove(event.player)
            event.player.inventory.clear()

            if(Parcour.instance.playersPlaying.isEmpty()){
                Parcour.instance.checkPosTask?.cancel()
                Parcour.instance.checkPosTask = null
            }
        }
    }

}