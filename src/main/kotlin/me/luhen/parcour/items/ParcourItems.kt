package me.luhen.parcour.items

import me.luhen.parcour.Parcour
import me.luhen.parcour.visual.VisualUtils
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ParcourItems {

    companion object{
        lateinit var returnItem: ItemStack
        lateinit var resetItem: ItemStack
        lateinit var leaveItem: ItemStack
    }

    init {
        createItems()
    }

    fun giveItems(player: Player){
        player.inventory.setItem(0, returnItem)
        player.inventory.setItem(4, resetItem)
        player.inventory.setItem(8, leaveItem)
    }

    private fun createItems(){

        val returnName = Parcour.instance.config.getString("items.return.displayname").toString()
        val resetName = Parcour.instance.config.getString("items.reset.displayname").toString()
        val leaveName = Parcour.instance.config.getString("items.leave.displayname").toString()

        val returnMaterial = ItemStack(Material.valueOf(Parcour.instance.config.getString("items.return.material").toString().uppercase()))
        val resetMaterial = ItemStack(Material.valueOf(Parcour.instance.config.getString("items.reset.material").toString().uppercase()))
        val leaveMaterial = ItemStack(Material.valueOf(Parcour.instance.config.getString("items.leave.material").toString().uppercase()))

        val returnLore = mutableListOf<Component>()
        Parcour.instance.config.getStringList("items.return.lore").forEach{ string ->
            returnLore.add(VisualUtils.convertToComponent(string))
        }
        val resetLore = mutableListOf<Component>()
        Parcour.instance.config.getStringList("items.reset.lore").forEach{ string ->
            resetLore.add(VisualUtils.convertToComponent(string))
        }
        val leaveLore = mutableListOf<Component>()
        Parcour.instance.config.getStringList("items.leave.lore").forEach{ string ->
            leaveLore.add(VisualUtils.convertToComponent(string))
        }

        val tempReturn = ItemStack(returnMaterial)
        val tempReturnMeta = tempReturn.itemMeta
        tempReturnMeta.displayName(VisualUtils.convertToComponent(returnName))
        tempReturnMeta.lore(returnLore)
        tempReturn.setItemMeta(tempReturnMeta)

        val tempReset = ItemStack(resetMaterial)
        val tempResetMeta = tempReset.itemMeta
        tempResetMeta.displayName(VisualUtils.convertToComponent(resetName))
        tempResetMeta.lore(resetLore)
        tempReset.setItemMeta(tempResetMeta)

        val tempLeave = ItemStack(leaveMaterial)
        val tempLeaveMeta = tempLeave.itemMeta
        tempLeaveMeta.displayName(VisualUtils.convertToComponent(leaveName))
        tempLeaveMeta.lore(leaveLore)
        tempLeave.setItemMeta(tempLeaveMeta)

        returnItem = tempReturn
        resetItem = tempReset
        leaveItem = tempLeave

    }

}