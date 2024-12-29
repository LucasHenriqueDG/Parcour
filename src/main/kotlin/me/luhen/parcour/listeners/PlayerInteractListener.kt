package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.items.ParcourItems
import me.luhen.parcour.listeners.customevents.PlayerLeaveParkourEvent
import me.luhen.parcour.listeners.customevents.PlayerResetParkourEvent
import me.luhen.parcour.listeners.customevents.PlayerTeleportToCheckpointEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class PlayerInteractListener: Listener {

    @EventHandler
    fun playerUseItem(event: PlayerInteractEvent){

        if(Parcour.instance.playersPlaying.containsKey(event.player)){
            if(event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK){

                when(event.player.inventory.itemInMainHand.type){

                    ParcourItems.leaveItem.type -> {
                        Bukkit.getPluginManager().callEvent(PlayerLeaveParkourEvent(event.player))
                    }
                    ParcourItems.resetItem.type -> {
                        Parcour.instance.playersPlaying[event.player]?.let{ parcourPlayer ->
                            Bukkit.getPluginManager().callEvent(PlayerResetParkourEvent(parcourPlayer))
                        }
                    }
                    ParcourItems.returnItem.type -> {
                        Parcour.instance.playersPlaying[event.player]?.let{ parcourPlayer ->
                            Bukkit.getPluginManager().callEvent(PlayerTeleportToCheckpointEvent(parcourPlayer))
                        }
                    }

                    else -> {}

                }

            }
            event.isCancelled = true
        }

    }

}