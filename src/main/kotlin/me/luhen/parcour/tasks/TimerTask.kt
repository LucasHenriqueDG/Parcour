package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.utils.PlaceholderUtils
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class TimerTask(val player: Player): BukkitRunnable() {

    var time = 0

    override fun run() {
        val msg = PlaceholderUtils.replacePlaceholder(
            Parcour.instance.messages["timer-message"].toString(),
            "%time%",
            time.toString()
        )
        VisualUtils.sendActionBar(msg, player)
        time += 1
    }

    fun reset(){
        time = 0
    }

}