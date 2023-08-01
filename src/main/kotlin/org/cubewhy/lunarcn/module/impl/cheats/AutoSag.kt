package org.cubewhy.lunarcn.module.impl.cheats

import net.minecraft.item.ItemSword
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.AttackEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.MSTimer
import org.cubewhy.lunarcn.value.DoubleValue


@ModuleInfo(
    name = "AutoSag",
    description = "Auto block your sword when you're targeting",
    category = ModuleCategory.CHEATS
)
class AutoSag : Module() {
    private val delay = DoubleValue("SagDelay", 0.2, 0.0, 10.0)

    private val timer = MSTimer()

    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (mc.thePlayer.heldItem.item is ItemSword && timer.hasTimePassed((delay.value * 1000).toLong())) {
            mc.rightClickMouse()
            timer.reset()
        }
    }
}