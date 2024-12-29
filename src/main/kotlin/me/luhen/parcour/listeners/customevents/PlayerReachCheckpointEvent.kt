package me.luhen.parcour.listeners.customevents

import me.luhen.parcour.data.ParcourPlayer
import org.bukkit.Location
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerReachCheckpointEvent(val parcourPlayer: ParcourPlayer, val location: Location): Event() {

    companion object{

        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList{
            return HANDLERS
        }

    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}