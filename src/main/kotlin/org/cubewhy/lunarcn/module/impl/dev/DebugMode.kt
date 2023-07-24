package org.cubewhy.lunarcn.module.impl.dev

import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.Client
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo

@ModuleInfo(name = "debugMode", description = "show debug message in chat", category = ModuleCategory.DEV)
class DebugMode : Module() {
    override val moduleImage: ResourceLocation
        get() = ResourceLocation("lunarcn/icons/build.png")
    override fun onDisabled() {
        Client.getInstance().debugMode = false
    }

    override fun onEnabled() {
        Client.getInstance().debugMode = true
    }
}