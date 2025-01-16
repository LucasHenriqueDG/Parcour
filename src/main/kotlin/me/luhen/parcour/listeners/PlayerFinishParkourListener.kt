package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.data.PlayerStatsData
import me.luhen.parcour.enums.LeaveType
import me.luhen.parcour.listeners.customevents.PlayerFinishParkourEvent
import me.luhen.parcour.listeners.customevents.PlayerLeaveParkourEvent
import me.luhen.parcour.tasks.DataAsyncTasks
import me.luhen.parcour.utils.DataUtils
import me.luhen.parcour.utils.PlaceholderUtils
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerFinishParkourListener: Listener {

    @EventHandler
    fun playerFinishParkour(event: PlayerFinishParkourEvent){

        Parcour.instance.playersPlaying[event.player]?.let{ parcourPlayer ->
            //Get the time
            val currentTime = System.currentTimeMillis()
            val parkourTime = currentTime - parcourPlayer.startingTime
            VisualUtils.sendComponent(
                PlaceholderUtils.replacePlaceholder(
                    PlaceholderUtils.replacePlaceholder(
                    Parcour.instance.messages["parkour-finished"].toString(),
                    "%time%",
                    DataUtils.formatTime(parkourTime)
                    ),
                    "%fails%",
                    parcourPlayer.fails.toString()
                ),
                event.player
            )

            //Save the player time
            val stats = PlayerStatsData(event.player.uniqueId, parkourTime )
            if(Parcour.instance.statsCache != null) {
                Parcour.instance.statsCache?.updateStats(event.player.uniqueId, stats)
            } else {
                DataAsyncTasks.saveDataAsync(stats)
            }

            //Make player leave
            Bukkit.getPluginManager().callEvent(PlayerLeaveParkourEvent(event.player, LeaveType.REGULAR))
        }


    }

}