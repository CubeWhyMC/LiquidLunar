package org.cubewhy.lunarcn.module.impl.render

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.KeyEvent
import org.cubewhy.lunarcn.event.events.TickEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.KeyValue
import org.cubewhy.lunarcn.value.StringValues
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display

@ModuleInfo(name = "FreeLook", description = "Look around without a rotation", category = ModuleCategory.RENDER)
class FreeLook : Module() {
    var cameraYaw: Float = 0.0f
        get() = if (freeLookState) field else mc.thePlayer.rotationYaw
    var cameraPitch: Float = 0.0f
        get() = if (freeLookState) field else mc.thePlayer.rotationPitch
    private var previous = 0

    // holdDown = true
    private val returnOnRelease = BooleanValue("ReturnOnRelease", true)
    private var freeLookState = false

    private val key = KeyValue("TriggerKey", Keyboard.KEY_LMENU)

    override fun onDisabled() {
        freeLookState = false // must be false
    }

    @EventTarget
    fun onKey(event: KeyEvent) {
        if (event.keyCode == key.value) {
            if (Keyboard.getEventKeyState()) {
                freeLookState = !freeLookState // toggle
                cameraYaw = mc.thePlayer.rotationYaw
                cameraPitch = mc.thePlayer.rotationPitch

                if (freeLookState) {
                    previous = mc.gameSettings.thirdPersonView
                    mc.gameSettings.thirdPersonView = 1
                } else {
                    // make it back
                    mc.gameSettings.thirdPersonView = previous
                }
            } else if (returnOnRelease.value) {
                println("OFF")
                freeLookState = false
                mc.gameSettings.thirdPersonView = previous
            }
        }
        // built-in perspective keyBind pressed
        if (event.keyCode == mc.gameSettings.keyBindTogglePerspective.keyCode) {
            // toggle
            freeLookState = false
        }
    }

    fun overrideMouse(): Boolean {
        if (mc.inGameHasFocus && Display.isActive()) {
            if (!freeLookState) {
                return true
            }
            mc.mouseHelper.mouseXYChange()
            val f1: Float = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F
            val f2: Float = f1 * 3 * 8.0F
            val f3: Float = mc.mouseHelper.deltaX * f2
            val f4: Float = mc.mouseHelper.deltaY * f2

            cameraYaw += f3 * 0.15F
            cameraPitch += f4  * 0.15F

            if (cameraPitch > 90F) {
                // you can't rotate anymore LOL
                cameraPitch = 90F
            }

            if (cameraYaw < -90F) {
                cameraYaw = -90F
            }
            return false
        }
        return true
    }
}