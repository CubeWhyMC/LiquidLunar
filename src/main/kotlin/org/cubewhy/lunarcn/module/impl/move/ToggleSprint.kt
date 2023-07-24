package org.cubewhy.lunarcn.module.impl.move

import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.RenderEvent
import org.cubewhy.lunarcn.gui.hud.HudDesigner
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import java.awt.Color

@ModuleInfo(name = "ToggleSprint", description = "Auto sprint when you are moving", category = ModuleCategory.MOVE)
class ToggleSprint : ModuleDraggable() {
    private var position: ScreenPosition = ScreenPosition.fromRelativePosition(0.5, 0.5)

    private var text = ""

    var sprintState = false

    override val moduleImage = ResourceLocation("lunarcn/icons/toggleSprint.png")

    override fun save(position: ScreenPosition) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        return this.position
    }

    override fun getWidth(): Int {
        return fontRenderer.getStringWidth(this.text)
    }

    override fun getHeight(): Int {
        return fontRenderer.FONT_HEIGHT
    }

    override fun render(position: ScreenPosition) {
        fontRenderer.drawString(this.text, position.absoluteX, position.absoluteY, Color(255, 255, 255).rgb)
    }

    @EventTarget
    fun onRender(event: RenderEvent) {
        if (mc.currentScreen is HudDesigner) {
            return
        }

        if (mc.thePlayer == null) {
            return
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown) {
            this.text = "Sneaking [KeyHold]"
        } else if (mc.gameSettings.keyBindSprint.isKeyDown && !sprintState && mc.thePlayer.isSprinting) {
            this.text = "Sprinting [KeyHold]"
        } else if (mc.gameSettings.keyBindSprint.isKeyDown && !sprintState) {
            this.text = "NotSprinting [KeyHold]"
        } else if (mc.thePlayer.isSprinting && !mc.gameSettings.keyBindSprint.isKeyDown) {
            this.text = "Sprinting [Vanilla]"
        } else if (sprintState && !mc.gameSettings.keyBindSneak.isKeyDown) {
            this.text = "Sprinting [Toggled]"
        } else {
            this.text = ""
        }
        mc.gameSettings.keyBindSprint.pressed = sprintState
        if (mc.gameSettings.keyBindSprint.isPressed) {
            sprintState = !sprintState
        }
    }

    override fun renderDummy(position: ScreenPosition) {
        this.text = "ToggleSprint"
        this.render(position)
    }
}