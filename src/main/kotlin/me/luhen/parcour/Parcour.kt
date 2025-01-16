package me.luhen.parcour

import me.luhen.parcour.commands.ParcourCommand
import me.luhen.parcour.data.ParcourPlayer
import me.luhen.parcour.data.PlayerStatsCache
import me.luhen.parcour.data.PlayerStatsManager
import me.luhen.parcour.items.ParcourItems
import me.luhen.parcour.listeners.*
import me.luhen.parcour.tasks.CheckForPlayersTask
import me.luhen.parcour.tasks.CheckPositionTask
import me.luhen.parcour.tasks.LoadCacheAsyncTask
import me.luhen.parcour.utils.DataUtils
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Parcour : JavaPlugin() {

    val messages = mutableMapOf<String, String>()
    var exitLocation: Location? = null
    var startingLocation: Location? = null
    val checkPoints = mutableMapOf<Location, Location>()
    var finalLocation: Location? = null
    var items: ParcourItems? = null
    var checkPosTask: CheckPositionTask? = null
    var statsCache: PlayerStatsCache? = null
    var statsManager: PlayerStatsManager? = null
    var checkingTask: CheckForPlayersTask? = null

    companion object{
        lateinit var instance: Parcour
    }

    init {
        instance = this
    }

    val playersPlaying = mutableMapOf<Player, ParcourPlayer>()

    override fun onEnable() {
        saveDefaultConfig()
        DataUtils.updateMessages()
        exitLocation = DataUtils.getLocationFromFile("exit-location")
        startingLocation = DataUtils.getLocationFromFile("starting-location")
        finalLocation = DataUtils.getLocationFromFile("final-location").toBlockLocation()
        items = ParcourItems()
        DataUtils.getCheckpoints()

        server.pluginManager.registerEvents(PlayerFinishParkourListener(), this)
        server.pluginManager.registerEvents(PlayerLeaveParkourListener(), this)
        server.pluginManager.registerEvents(PlayerReachCheckpointListener(), this)
        server.pluginManager.registerEvents(PlayerStartParkourListener(), this)
        server.pluginManager.registerEvents(PlayerQuitListener(), this)
        server.pluginManager.registerEvents(PlayerUseCommandListener(), this)
        server.pluginManager.registerEvents(PlayerResetParkourListener(), this)
        server.pluginManager.registerEvents(PlayerTeleportToCheckpointListener(), this)
        server.pluginManager.registerEvents(PlayerInteractListener(), this)

        getCommand("parcour")?.setExecutor(ParcourCommand())

        statsManager = if(config.getString("database-type").toString().lowercase() == "json"){
            PlayerStatsManager(config.getString("json-file-name").toString())
        } else {
            PlayerStatsManager(
                config.getString("mysql.host").toString(),
                config.getInt("mysql.port"),
                config.getString("mysql.database").toString(),
                config.getString("mysql.user").toString(),
                config.getString("mysql.password").toString(),
            )
        }

        if(config.getBoolean("cache")){
            statsCache = PlayerStatsCache()
            statsManager?.let { manager ->
                statsCache?.let { cache ->
                    LoadCacheAsyncTask.loadCacheAsync(manager, cache)
                }
            }
        }
        startingLocation?.let {
            val task = CheckForPlayersTask(it)
            checkingTask = task.apply { runTaskTimer(instance, 20L, 20L) }
        }

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
