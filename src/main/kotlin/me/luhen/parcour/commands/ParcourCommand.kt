package me.luhen.parcour.commands

import me.luhen.parcour.Parcour
import me.luhen.parcour.listeners.customevents.PlayerStartParkourEvent
import me.luhen.parcour.tasks.DataAsyncTasks
import me.luhen.parcour.utils.DataUtils
import me.luhen.parcour.utils.PlaceholderUtils
import me.luhen.parcour.visual.VisualUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ParcourCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, str: String, args: Array<out String>?): Boolean {

        args?.let{
            if(args.isNotEmpty()){
                when(args[0]){

                    "reload" -> {
                        if(sender.hasPermission("parcour.adm")){
                            DataUtils.updateMessages()
                            if(sender is Player){
                                VisualUtils.sendComponent(Parcour.instance.messages["reload-message"].toString(), sender)
                            } else {
                                Parcour.instance.logger.info("Parcour plugin config reloaded.")
                            }
                        } else {
                            if(sender is Player) {
                                VisualUtils.sendComponent(Parcour.instance.messages["no-permission-message"].toString(), sender)
                            }
                        }
                    }

                    "start" -> {
                        if(sender is Player) {
                            Bukkit.getPluginManager().callEvent(PlayerStartParkourEvent(sender))
                        }
                    }

                    "stats" -> {
                        if(sender is Player){
                            Parcour.instance.statsCache?.let{ cache ->
                                val stats = cache.getStats(sender.uniqueId)
                                stats?.let {
                                    val bestTime = DataUtils.formatTime(stats.bestTime)

                                    VisualUtils.sendComponent(
                                        PlaceholderUtils.replacePlaceholder(
                                            Parcour.instance.messages["stats-message"].toString(),
                                            "%best_time%",
                                            bestTime
                                        ),
                                        sender
                                    )
                                } ?: VisualUtils.sendComponent(Parcour.instance.messages["no-stats-message"].toString(),
                                    sender
                                )
                            } ?: temp1(sender)
                        }
                    }

                    else ->{}

                }
            }
        }



        return true
    }

    private fun temp1(player: Player){
        DataAsyncTasks.getDataAsync(player)?.thenAccept{ stats ->

            stats?.let {
                val bestTime = DataUtils.formatTime(stats.bestTime)
                VisualUtils.sendComponent(
                    PlaceholderUtils.replacePlaceholder(
                        Parcour.instance.messages["stats-message"].toString(),
                        "%best_time%",
                        bestTime
                    ),
                    player
                )
            } ?: VisualUtils.sendComponent(Parcour.instance.messages["no-stats-message"].toString(),
                player
            )
        }

    }

}