package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerUseCommandListener: Listener {

    @EventHandler
    fun playerUseCommand(event: PlayerCommandPreprocessEvent){
        if(Parcour.instance.playersPlaying.containsKey(event.player)){
            event.isCancelled = true
            VisualUtils.sendComponent(Parcour.instance.messages["commands-blocked-message"].toString(), event.player)
        }
    }

}