package org.cubewhy.lunarcn.module.impl.render

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import java.awt.Color

@ModuleInfo(name = "Keystrokes", description = "Display what key you pressed", category = ModuleCategory.RENDER)
class Keystrokes : ModuleDraggable() {
    var position: ScreenPosition = ScreenPosition.fromRelativePosition(0.5, 0.5)
    override val moduleImage =  ResourceLocation("lunarcn/icons/keystrokes.png")

    enum class Mode(vararg keys: Key) {
        WASD(Key.keyW, Key.keyA, Key.keyS, Key.keyD),
        WASD_WITH_MOUSE(
            Key.keyW,
            Key.keyA,
            Key.keyS,
            Key.keyD,
            Key.keyLMB,
            Key.keyRMB
        ),
        WASD_WITH_SPRINT(
            Key.keyW,
            Key.keyA,
            Key.keyS,
            Key.keyD,
            Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 41, 58, 18)
        ),
        WASD_WITH_SPRINT_MOUSE(
            Key.keyW,
            Key.keyA,
            Key.keyS,
            Key.keyD,
            Key.keyLMB,
            Key.keyRMB,
            Key("SPRINT", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 61, 58, 18)
        ),
        WASD_WITH_JUMP(
            Key.keyW,
            Key.keyA,
            Key.keyS,
            Key.keyD,
            Key("SPACE", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 41, 58, 18)
        ),
        WASD_WITH_JUMP_MOUSE(
            Key.keyW,
            Key.keyA,
            Key.keyS,
            Key.keyD,
            Key.keyLMB,
            Key.keyRMB,
            Key("SPACE", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 61, 58, 18)
        );

        var width: Int = 0
        var height: Int = 0
        var keys: Array<out Key>;

        init {
            this.keys = keys;
            for (key in keys) {
                this.width = this.width.coerceAtLeast(key.x + key.width)
                this.height = this.height.coerceAtLeast(key.y + key.height)
            }
        }

    }

    class Key(
        val name: String, private val keyBind: KeyBinding, val x: Int, val y: Int, val width: Int, val height: Int
    ) {

        companion object {
            val keyW = Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18)
            val keyA = Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18)
            val keyS = Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18)
            val keyD = Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18)

            val keyLMB = Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18)
            val keyRMB = Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18)
        }

        fun isDown(): Boolean {
            return keyBind.isKeyDown
        }
    }

    var mode: Mode = Mode.WASD_WITH_JUMP_MOUSE;

    override fun save(position: ScreenPosition) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        return this.position
    }

    override fun getWidth(): Int {
        return mode.width
    }

    override fun getHeight(): Int {
        return mode.height
    }

    override fun render(position: ScreenPosition) {

//        val blend: Boolean = GL11.glIsEnabled(GL11.GL_BLEND)
//        GL11.glPushMatrix()
//
//        GL11.glDisable(GL11.GL_BLEND) // 不要取消注释这些代码, 否则会出现渲染错误

        for (key in mode.keys) {
            val textWidth = fontRenderer.getStringWidth(key.name)

            Gui.drawRect(
                position.absoluteX + key.x,
                position.absoluteY + key.y,
                position.absoluteX + key.x + key.width,
                position.absoluteY + key.y + key.height,
                if (key.isDown()) Color(255, 255, 255, 102).rgb else Color(0, 0, 0, 102).rgb
            )

            fontRenderer.drawString(
                key.name,
                position.absoluteX + key.x + key.width / 2 - textWidth / 2,
                position.absoluteY + key.y + key.height / 2 - 4,
                if (key.isDown()) Color.BLACK.rgb else Color.WHITE.rgb
            )
        }

//        if (blend) {
//            GL11.glEnable(GL11.GL_BLEND)
//        }
//
//        GL11.glPopMatrix()
    }
}