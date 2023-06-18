package org.cubewhy.lunarcn.module.modules.render

import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo

@ModuleInfo(name = "Lighting", document = "set gamma to 1000F", category = ModuleCategory.RENDER)
class Lighting : Module() {

    private val currentGamma = mc.gameSettings.gammaSetting
    override fun onEnabled() {
        mc.gameSettings.gammaSetting = 1000f
    }

    override fun onDisabled() {
        mc.gameSettings.gammaSetting = currentGamma
    }
}