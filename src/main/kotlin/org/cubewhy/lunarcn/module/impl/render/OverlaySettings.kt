package org.cubewhy.lunarcn.module.impl.render

import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.DoubleValue

@ModuleInfo(name = "OverlaySettings", description = "In-Game overlays", category = ModuleCategory.RENDER, canEnabled = false)
class OverlaySettings : Module() {
    val pumpkin = BooleanValue("Render pumpkin", true)
    val fireHeight = DoubleValue("Fire Height", 1.0, 0.0, 1.0)
}