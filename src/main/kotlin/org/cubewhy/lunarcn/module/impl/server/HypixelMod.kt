package org.cubewhy.lunarcn.module.impl.server

import net.minecraft.network.play.server.S02PacketChat
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.JoinServerEvent
import org.cubewhy.lunarcn.event.events.PacketEvent
import org.cubewhy.lunarcn.event.events.TickEvent
import org.cubewhy.lunarcn.event.events.WorldEvent
import org.cubewhy.lunarcn.module.Module
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.MSTimer
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.StringValue
import java.util.regex.Pattern

@ModuleInfo(name = "Hypixel Mod", description = "Helpful tools for hypixel server", category = ModuleCategory.SERVER)
class HypixelMod : Module() {
    private var currentServerIsHypixel = false

    private val autoGG = BooleanValue("AutoGG", true) // the autoGG toggle
    private val goodGameMessage = StringValue("Good Game Msg", "gg")
    private val autoNext = BooleanValue("AutoNext", false)

    private val autoTip = BooleanValue("AutoTip", true) // do /tip all auto

    private val hypixelIP = Pattern.compile(".hypixel\\.net");

    private val timer = MSTimer()

    @EventTarget
    fun onJoinServer(event: JoinServerEvent) {
        currentServerIsHypixel = hypixelIP.matcher(event.ip).find()
        if (!currentServerIsHypixel) {
            state = false // TODO Add tempDisable in ModuleInfo and remove this
        } else {
            this.timer.reset() // reset the timer
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        // AutoGG
        val packet = event.packet
        if (packet is S02PacketChat && autoGG.value) {
            val clickEvent = packet.chatComponent.chatStyle.chatClickEvent.value
            if (clickEvent.startsWith("/play", true)) {
                // Next game action
                mc.thePlayer.sendChatMessage("/ac ${goodGameMessage.value}") // send the message
                if (autoNext.value) {
                    mc.thePlayer.sendChatMessage(clickEvent) // send the command for nextGame
                }
            }
        }
    }

    @EventTarget
    fun onTick(event: TickEvent) {
        if (autoTip.value && timer.hasTimePassed(600000)) {
            mc.thePlayer.sendChatMessage("/tip all") // do /tip all
            timer.reset() // reset the timer
        }
    }

    @EventTarget
    fun onWorld(event: WorldEvent) {
        // TODO call /locraw to get current loc
    }
}