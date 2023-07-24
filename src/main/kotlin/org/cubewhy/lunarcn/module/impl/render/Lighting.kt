package org.cubewhy.lunarcn.module.impl.render

import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo

@ModuleInfo(name = "Lighting", description = "set gamma to 1000F", category = ModuleCategory.RENDER)
class Lighting : Module() {

    private val currentGamma = mc.gameSettings.gammaSetting
    override val moduleImage =  ResourceLocation("lunarcn/icons/lighting.png")
    override fun onEnabled() {
        mc.gameSettings.gammaSetting = 1000f
    }

    override fun onDisabled() {
        mc.gameSettings.gammaSetting = currentGamma
    }
}