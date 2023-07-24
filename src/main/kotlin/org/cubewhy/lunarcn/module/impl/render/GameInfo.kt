package org.cubewhy.lunarcn.module.impl.render

import net.minecraft.client.renderer.GlStateManager
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.JoinServerEvent
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.MSTimer
import org.cubewhy.lunarcn.utils.RenderUtils
import java.awt.Color

@ModuleInfo(name = "GameInfo", description = "Show current session", category = ModuleCategory.RENDER)
class GameInfo : ModuleDraggable() {
    private var position: ScreenPosition? = null;
    private val timer: MSTimer = MSTimer()

    override fun save(position: ScreenPosition) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        if (this.position == null) {
            this.position = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        return this.position!!
    }

    override fun getWidth(): Int {
        return 300
    }

    override fun getHeight(): Int {
        return 50
    }

    override fun render(position: ScreenPosition?) {
        if (timer.time == -1L) {
            this.timer.reset()
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F)
        val x = this.position!!.absoluteX
        val y = this.position!!.absoluteY
        // 绘制背景
        RenderUtils.drawRoundedRect(
            x, y,
            width, height, 2,
            Color(0, 0, 0, 30)
        )
        // 绘制文字
        RenderUtils.drawString(
            "Player: " + mc.session.username,
            x + 10,
            y + 10,
            Color(255, 255, 255, 50).rgb,
            true
        )
        RenderUtils.drawString(
            "Play time: " + formatTime(timer.timePassed()),
            x + 10,
            y + fontRenderer.FONT_HEIGHT * 2,
            Color(255, 255, 255, 50).rgb,
            true
        )
        RenderUtils.drawString(
            "Server IP: " + getServerIp(),
            x + 10,
            y + fontRenderer.FONT_HEIGHT * 3,
            Color(255, 255, 255, 50).rgb,
            true
        )
    }

    private fun getServerIp(): String {
        val serverIp = if (mc.isSingleplayer) {
            "SinglePlayer"
        } else if (mc.currentServerData != null) {
            mc.currentServerData.serverIP
        } else {
            "Null"
        }
        return serverIp
    }

    private fun formatTime(time: Long): String {
        val realTime = time / 1000
        val hours = realTime / 3600
        val minutes = realTime % 3600 / 60
        val seconds = realTime % 3600 % 60

        return "${hours}:${minutes}:${seconds}"
    }

    @EventTarget
    fun onJoinServer(e: JoinServerEvent) {
        this.timer.reset()
    }
}