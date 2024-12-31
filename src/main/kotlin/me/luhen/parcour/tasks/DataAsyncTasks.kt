package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.data.PlayerStatsData
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

object DataAsyncTasks {

    fun getDataAsync(player: Player): CompletableFuture<PlayerStatsData>?{

        return if(Parcour.instance.statsCache == null) {

            CompletableFuture.supplyAsync {

                Parcour.instance.statsManager?.getPlayerStats(player)

            }

        } else null

    }

    fun saveDataAsync(data: PlayerStatsData){

        CompletableFuture.runAsync {
            Parcour.instance.statsManager?.updateStats(data)
        }

    }

}