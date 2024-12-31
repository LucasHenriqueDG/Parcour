package me.luhen.parcour.data

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class PlayerStatsCache {
    private val cache = ConcurrentHashMap<UUID, PlayerStatsData>()

    fun loadCache(initialData: List<PlayerStatsData>) {
        initialData.forEach { cache[it.uuid] = it }
    }

    fun getStats(uuid: UUID): PlayerStatsData? {
        return cache[uuid]
    }

    fun updateStats(uuid: UUID, newData: PlayerStatsData) {

        val existingData = cache[uuid]

        if (existingData == null || newData.bestTime < existingData.bestTime) {
            cache[uuid] = newData
            println("Updated stats for UUID $uuid with better time: ${newData.bestTime}")
        } else {
            println("Stats for UUID $uuid were not updated. Existing best time: ${existingData.bestTime}, New best time: ${newData.bestTime}")
        }

    }

}