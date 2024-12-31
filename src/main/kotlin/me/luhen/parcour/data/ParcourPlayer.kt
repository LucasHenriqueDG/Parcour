package me.luhen.parcour.data

import me.luhen.parcour.tasks.TimerTask
import org.bukkit.Location
import org.bukkit.entity.Player

class ParcourPlayer(
    val player: Player,
    var checkPoint: Location,
    var startingTime: Long,
    val timerTask: TimerTask,
    var fails: Int = 0)