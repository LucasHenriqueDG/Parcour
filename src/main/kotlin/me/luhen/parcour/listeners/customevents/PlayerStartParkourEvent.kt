package me.luhen.parcour.listeners.customevents

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerStartParkourEvent(val player: Player): Event() {

    companion object{

        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList{
            return HANDLERS
        }

    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}