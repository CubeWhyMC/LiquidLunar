package org.cubewhy.lunarcn.module.impl.server

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.JoinServerEvent
import org.cubewhy.lunarcn.event.events.ScreenChangeEvent
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.worker.PingWorker
import java.awt.Color

@ModuleInfo(name = "ServerPing", description = "Show current server's ping", category = ModuleCategory.SERVER)
class ServerPing : ModuleDraggable() {
    private var position: ScreenPosition? = null

    private var text: String = "Ping: 0ms"
    private var worker = PingWorker()
    private var workerThread = Thread(worker)

    override fun onEnabled() {
        workerThread.start()
        worker.isRun = true
    }

    override fun onDisabled() {
        worker.isRun = false
    }

    override fun save(position: ScreenPosition?) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        if (this.position == null) {
            this.position = ScreenPosition.fromRelativePosition(0.5, 0.5)
        }
        return this.position!!
    }

    @EventTarget
    fun onJoinServer(event: JoinServerEvent) {
        // change ip
        worker.currentConnection = event.address
        if (!worker.isRun) {
            // start ping thread
            workerThread.start()
            worker.isRun = true
        }
    }

    @EventTarget
    fun onScreenChange(event: ScreenChangeEvent) {
        // if not in game or in single-player, disable worker
        if (mc.isSingleplayer || mc.thePlayer == null) {
            worker.isRun = false // disable worker
        }
    }

    override fun getWidth(): Int {
        return fontRenderer.getStringWidth(this.text)
    }

    override fun getHeight(): Int {
        return fontRenderer.FONT_HEIGHT
    }

    override fun render(position: ScreenPosition) {
        val x = position.absoluteX
        val y = position.absoluteY
        fontRenderer.drawString(text, x, y, Color(255, 255, 255).rgb)
        if (mc.isSingleplayer) {
            this.text = "Ping: SinglePlayer" // single-player
        } else {
            this.text = "Ping: " + worker.ping + "ms" // set test
        }
    }

    override fun renderDummy(position: ScreenPosition) {
        if (this.text.isEmpty()) {
            this.text = "Ping"
        }
        render(position)
    }
}