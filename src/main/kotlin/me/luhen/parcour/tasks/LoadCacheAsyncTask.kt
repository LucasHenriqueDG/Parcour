package me.luhen.parcour.tasks

import me.luhen.parcour.Parcour
import me.luhen.parcour.data.PlayerStatsCache
import me.luhen.parcour.data.PlayerStatsManager
import org.bukkit.Bukkit

object LoadCacheAsyncTask {

    fun loadCacheAsync(statsManager: PlayerStatsManager, statsCache: PlayerStatsCache) {
        Bukkit.getScheduler().runTaskAsynchronously(Parcour.instance, Runnable {
            val data = statsManager.readStats()
            statsCache.loadCache(data)
            Parcour.instance.logger.info("Cache loaded successfully!")
        })
    }
}