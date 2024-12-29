package me.luhen.parcour.listeners

import me.luhen.parcour.Parcour
import me.luhen.parcour.data.ParcourPlayer
import me.luhen.parcour.listeners.customevents.PlayerStartParkourEvent
import me.luhen.parcour.tasks.CheckPositionTask
import me.luhen.parcour.tasks.TimerTask
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerStartParkourListener: Listener {

    @EventHandler
    fun playerStartParkour(event: PlayerStartParkourEvent){
        Parcour.instance.startingLocation?.let { startLoc ->
            val currentTime = System.currentTimeMillis()
            Parcour.instance.playersPlaying[event.player] = ParcourPlayer(
                event.player,
                startLoc,
                currentTime,
                TimerTask(event.player)
            )
            Parcour.instance.playersPlaying[event.player]?.timerTask?.apply {
                runTaskTimer(Parcour.instance, 0L, 20L)
            }

            event.player.inventory.clear()
            Parcour.instance.items?.giveItems(event.player)
            event.player.teleport(startLoc)
            VisualUtils.sendComponent(Parcour.instance.messages["joined-parkour"].toString(), event.player)

            if (Parcour.instance.checkPosTask == null) {
                Parcour.instance.checkPosTask = CheckPositionTask()
                Parcour.instance.checkPosTask?.apply { runTaskTimer(Parcour.instance, 0L, 2L) }
            }
        }

    }

}