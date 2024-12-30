package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.data.PlayerStatsData
import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

object GetDataAsyncTask {

    fun getDataAsync(player: Player): CompletableFuture<PlayerStatsData>?{

        if(Parcour.instance.statsCache == null) {

            return CompletableFuture.supplyAsync {

                Parcour.instance.statsManager?.getPlayerStats(player)

            }

        } else return null

    }

}