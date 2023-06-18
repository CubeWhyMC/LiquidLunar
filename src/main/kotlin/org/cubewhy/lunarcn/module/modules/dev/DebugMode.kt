package org.cubewhy.lunarcn.module.modules.dev

import org.cubewhy.lunarcn.Client
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.LoggerUtils

@ModuleInfo(name = "debugMode", document = "show debug message in chat", category = ModuleCategory.DEV)
class DebugMode : Module() {
    override fun onDisabled() {
        Client.getInstance().debugMode = false
    }

    override fun onEnabled() {
        Client.getInstance().debugMode = true
    }
}