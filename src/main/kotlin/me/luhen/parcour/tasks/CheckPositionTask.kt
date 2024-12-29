package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerFinishParkourEvent
import me.luhen.parcour.listeners.customevents.PlayerReachCheckpointEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

class CheckPositionTask: BukkitRunnable() {

    override fun run() {
        Parcour.instance.playersPlaying.forEach { (player, parcourPlayer) ->
            val currentLoc = Location(player.world, player.x, player.y, player.z).toBlockLocation()
            if(currentLoc == Parcour.instance.finalLocation){
                //Player has finished the parkour
                Bukkit.getPluginManager().callEvent(PlayerFinishParkourEvent(player))
            } else if(Parcour.instance.checkPoints.containsKey(currentLoc)){
                //Player is above a checkpoint, check if it is already his current checkpoint
                Parcour.instance.checkPoints[currentLoc]?.let{ newCheckpoint ->
                if(parcourPlayer.CheckPoint != newCheckpoint){
                        Bukkit.getPluginManager().callEvent(PlayerReachCheckpointEvent(parcourPlayer, newCheckpoint))
                    }
                }
            }
        }
    }

}