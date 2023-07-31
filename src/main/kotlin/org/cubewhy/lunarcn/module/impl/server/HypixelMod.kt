package org.cubewhy.lunarcn.module.impl.server

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.network.play.server.S02PacketChat
import org.cubewhy.lunarcn.event.EventTarget
import org.cubewhy.lunarcn.event.events.*
import org.cubewhy.lunarcn.files.configs.ServerConfigFile
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import org.cubewhy.lunarcn.utils.MSTimer
import org.cubewhy.lunarcn.value.BooleanValue
import org.cubewhy.lunarcn.value.StringValue
import java.awt.Color
import java.util.regex.Pattern

@ModuleInfo(name = "Hypixel Mod", description = "Helpful tools for hypixel server", category = ModuleCategory.SERVER)
class HypixelMod : ModuleDraggable() {
    private var position: ScreenPosition? = null

    private var currentServerIsHypixel = false

    private val autoGG = BooleanValue("AutoGG", true) // the autoGG toggle
    private val goodGameMessage = StringValue("Good Game Msg", "gg")
    private val autoNext = BooleanValue("AutoNext", false)

    private val autoTip = BooleanValue("AutoTip", true) // do /tip all auto

    private val hypixelIP = Pattern.compile(".hypixel\\.net")

    private val timer = MSTimer()

    // Current server info
    private var subServer: String? = null
    private var gameType: String? = null
    private var lastLobby: String? = null
    private var serverType: ServerType = ServerType.LOBBY

    private val addresses = ServerConfigFile.getInstance().getServerAddressesList(ServerConfigFile.ServerEnum.HYPIXEL)


    private var texts = ArrayList<String>()

    enum class ServerType {
        LIMBO, // The afk server
        LOBBY, // Lobby server
        GAME; // in game, see var gameType
    }

    override fun onEnabled() {
        checkHypixel()
    }

    override fun save(position: ScreenPosition) {
        this.position = position
    }

    override fun load(): ScreenPosition {
        if (position == null) {
            position = ScreenPosition.fromRelativePosition(0.5, 0.5)
        }
        return position!!
    }

    override fun getWidth(): Int {
        // get the max string width
        var temp = 0
        for (text in this.texts) {
            if (text.length > temp) {
                temp = text.length
            }
        }
        return temp
    }

    override fun getHeight(): Int {
        return fontRenderer.FONT_HEIGHT * this.texts.size
    }

    override fun render(position: ScreenPosition?) {
        if (this.texts.size == 0) {
            return // No texts to render
        }

        // position x, y
        val x = this.position!!.absoluteX
        val y = this.position!!.absoluteX

        for (text in this.texts) {
            fontRenderer.drawString(text, x.toFloat(), y.toFloat(), Color(255, 255, 255).rgb, true)
        }
    }

    @EventTarget
    fun onJoinServer(event: JoinServerEvent) {
        checkHypixel()
    }

    private fun checkHypixel() {
        if (mc.currentServerData == null || mc.isSingleplayer) {
            return
        }
        val ip = mc.currentServerData.serverIP
        currentServerIsHypixel = hypixelIP.matcher(ip).find() || ip in addresses
        if (!currentServerIsHypixel) {
            state = false
        } else {
            this.timer.reset() // reset the timer
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        if (!currentServerIsHypixel) {
            return
        }
        // AutoGG
        val packet = event.packet
        if (event.type == PacketEvent.Type.RECEIVE && packet is S02PacketChat && autoGG.value) {
            if (packet.chatComponent.chatStyle.chatClickEvent != null) {
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
    }

    @EventTarget
    fun onTick(event: TickEvent) {
        if (autoTip.value && timer.hasTimePassed(600000) && currentServerIsHypixel) {
            mc.thePlayer.sendChatMessage("/tip all") // do /tip all
            timer.reset() // reset the timer
        }
    }

    @EventTarget
    fun onWorld(event: WorldEvent) {
        // TODO call /locraw to get current loc
        if (!(currentServerIsHypixel && mc.thePlayer != null)) {
            return
        }
        mc.thePlayer.sendChatMessage("/locraw") // send the message
    }

    @EventTarget
    fun onChat(event: ChatEvent) {
        // get json
        val msgRaw = event.chatMessage // get the latest message (may json)
        // is json
        if (msgRaw.startsWith("{") && msgRaw.endsWith("}")) {
            // parse the json
            val gson = Gson()
            val json = gson.fromJson(msgRaw, JsonObject::class.java)
            this.subServer = json.get("server").asString // sub server name
            if (this.subServer.equals("limbo")) {
                // AFK Server
                this.serverType = ServerType.LIMBO
            } else {
                this.gameType = json.get("gametype").asString // the game type, etc. BEDWARS, SKYWARS
            }
            this.serverType = if (json.has("lobby")) ServerType.LOBBY else ServerType.GAME
            if (serverType == ServerType.LOBBY) {
                this.lastLobby = json.get("lobby").asString
            } else if (serverType == ServerType.GAME) {
                this.processGame(json)
            }
        }
    }

    /**
     * Process the game
     * etc. bedWars resource counts
     * */
    private fun processGame(json: JsonObject) {
        // TODO in game help
        // clean text list
        this.texts = ArrayList()
        // add texts
        if (this.serverType == ServerType.LIMBO) {
            this.texts.add("I'm AFK.")
        } else {
            val gameType = json.get("gametype").asString // game type
            this.texts.add("Game type: $gameType")
        }
    }
}