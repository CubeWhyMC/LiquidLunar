package org.cubewhy.lunarcn.module.impl.cheats

import net.minecraft.item.ItemSword
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.AttackEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo


@ModuleInfo(
    name = "AutoSag",
    description = "Auto block your sword when you're targeting",
    category = ModuleCategory.CHEATS
)
class AutoSag : Module() {
    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (mc.thePlayer.heldItem.item is ItemSword) {
            mc.rightClickMouse()
        }
    }
}