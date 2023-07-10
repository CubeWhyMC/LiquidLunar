package org.cubewhy.lunarcn.module.modules.server

import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.JoinServerEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import java.util.regex.Pattern

@ModuleInfo(name = "Hypixel Mod", description = "Helpful tools for hypixel server", category = ModuleCategory.SERVER)
class HypixelMod : Module() {
    private var currentServerIsHypixel = false

    private val hypixelIP = Pattern.compile(".hypixel\\.net");

    @EventTarget
    fun onJoinServer(event: JoinServerEvent) {
        currentServerIsHypixel = hypixelIP.matcher(event.ip).find()
    }
}