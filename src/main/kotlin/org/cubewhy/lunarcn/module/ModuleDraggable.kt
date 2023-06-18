package org.cubewhy.lunarcn.module

import org.cubewhy.lunarcn.event.EventManager
import org.cubewhy.lunarcn.gui.hud.HudManager
import org.cubewhy.lunarcn.gui.hud.IRenderer
import org.cubewhy.lunarcn.gui.hud.ScreenPosition

abstract class ModuleDraggable : Module(), IRenderer {

    override var state = false
        set(value) {
            if (value) {
                HudManager.getInstance().register(this)
                onEnabled()
            } else {
                HudManager.getInstance().unregister(this)
                onDisabled()
            }
            field = value
        }

    fun getLineOffset(position: ScreenPosition, lineNumber: Int): Int {
        return position.absoluteY + getLineOffset(lineNumber)
    }

    fun getLineOffset(lineNumber: Int): Int {
        return (fontRenderer.FONT_HEIGHT + 3) * lineNumber
    }
}
