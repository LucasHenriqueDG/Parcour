package me.luhen.parcour.listeners.customevents

import me.luhen.parcour.enums.JoinType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerStartParkourEvent(val player: Player, val type: JoinType): Event() {

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