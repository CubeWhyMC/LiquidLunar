package org.cubewhy.lunarcn.module.impl.render

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.KeyEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.KeyValue
import org.cubewhy.lunarcn.value.StringValues
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display

@ModuleInfo(name = "SnapLook", description = "Fast look around", category = ModuleCategory.RENDER)
class FreeLook : Module() {
    var cameraYaw: Float = 0.0f
        get() = if (snapLookState) field else mc.thePlayer.rotationYaw
    var cameraPitch: Float = 0.0f
        get() = if (snapLookState) field else mc.thePlayer.rotationPitch
    private var previous = 0

    private val view = StringValues("View", listOf("Third", "Second").toTypedArray())
    private val returnOnRelease = BooleanValue("ReturnOnRelease", true)
    private var snapLookState = false

    private val key = KeyValue("TriggerKey", Keyboard.KEY_LMENU)

    @EventTarget
    fun onKey(event: KeyEvent) {
        if (key.isPressed) {
            snapLookState = !snapLookState
            cameraYaw = mc.thePlayer.rotationYaw
            cameraPitch = mc.thePlayer.rotationPitch
            if (snapLookState) {
                previous = mc.gameSettings.thirdPersonView
                mc.gameSettings.thirdPersonView = if (view.value == "Third") 1 else 2
            } else {
                // make it back
                mc.gameSettings.thirdPersonView = previous
            }
        } else if (returnOnRelease.value) {
            snapLookState = false
            mc.gameSettings.thirdPersonView = previous
        }
        // built-in perspective keyBind pressed
        if (event.keyCode == mc.gameSettings.keyBindTogglePerspective.keyCode) {
            // toggle
            snapLookState = false
        }
    }

    fun overrideMouse(): Boolean {
        if (mc.inGameHasFocus && Display.isActive()) {
            if (!snapLookState) {
                return true
            }
            mc.mouseHelper.mouseXYChange()
            val f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F
            val f2 = f1 * 3 * 8.0F
            val f3 = mc.mouseHelper.deltaX * f2
            val f4 = mc.mouseHelper.deltaY * f2

            cameraYaw += f3 * 0.15F
            cameraPitch += f4  * 0.15F

            if (cameraPitch > 90F) {
                // you can't rotate anymore LOL
                cameraPitch = 90F;
            }

            if (cameraYaw < -90F) {
                cameraYaw = -90F
            }
        }
        return false
    }
}