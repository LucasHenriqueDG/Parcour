package me.luhen.parcour.listeners.customevents

import me.luhen.parcour.enums.LeaveType
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerLeaveParkourEvent(val player: Player, val type: LeaveType): Event() {

    companion object{

        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }

    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

}