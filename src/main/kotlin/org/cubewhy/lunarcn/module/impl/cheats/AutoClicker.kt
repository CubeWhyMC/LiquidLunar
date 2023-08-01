package org.cubewhy.lunarcn.module.impl.cheats

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.TickEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.ClickUtils
import org.cubewhy.lunarcn.utils.MSTimer
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.IntValue
import org.lwjgl.input.Mouse
import kotlin.random.Random

@ModuleInfo(name = "AutoClicker", description = "Click agent", category = ModuleCategory.CHEATS)
class AutoClicker : Module() {
    private val maxCPS = IntValue("MaxCPS", 6, 0, 40)
    private val minCPS = IntValue("MinCPS", 2, 0, 40)

    private val targetClick = BooleanValue("TargetClick", false)

    private val leftLastPressed: Long = -1
    private val rightLastPressed: Long = -1
    private var delay: Long = -1;

    private val leftHasPressed = false
    private val rightHasPressed = false

    private val clicksLeft = ArrayList<Long>()
    private val clicksRight = ArrayList<Long>()

    private val timer = MSTimer()

    @EventTarget
    fun onTick(event: TickEvent) {
        if (minCPS.value > maxCPS.value) {
            maxCPS.value = minCPS.value // min can't > max
        }
        if ((targetClick.value && mc.pointedEntity != null) || Mouse.isButtonDown(0)) {
            handleClickLeft() // click the mouse
        }
    }

    /**
     * Click the mouse with the cps
     * */
    private fun handleClickLeft() {
        delay = ClickUtils.getRandomClickCPS(minCPS.value, maxCPS.value)

        if (timer.hasTimePassed(delay)) {
            mc.clickMouse() // click mouse
            timer.reset() // reset the timer
        }
    }

    private fun getLeftCPS(): Int {
        val time = System.currentTimeMillis()
        this.clicksLeft.removeIf { clickTime: Long -> clickTime + 1000 < time }
        return this.clicksLeft.size
    }

    private fun getRightCPS(): Int {
        val time = System.currentTimeMillis()
        this.clicksRight.removeIf { clickTime: Long -> clickTime + 1000 < time }
        return this.clicksRight.size
    }
}