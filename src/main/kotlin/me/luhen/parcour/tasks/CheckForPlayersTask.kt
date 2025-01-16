package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.enums.JoinType
import me.luhen.parcour.listeners.customevents.PlayerStartParkourEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class CheckForPlayersTask(val location: Location): BukkitRunnable() {
    override fun run() {
        location.world.getNearbyEntities(location, 2.0, 1.0, 2.0).forEach{
            if(it.type == EntityType.PLAYER){
                val player = it as Player
                if(!Parcour.instance.playersPlaying.containsKey(player)){
                    //Player can start pacour :)
                    Bukkit.getPluginManager().callEvent(PlayerStartParkourEvent(player, JoinType.REGULAR))
                }
            }
        }
    }
}