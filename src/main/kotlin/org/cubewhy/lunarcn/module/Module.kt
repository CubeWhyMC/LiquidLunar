package org.cubewhy.lunarcn.module

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.util.ResourceLocation
import org.apache.logging.log4j.Logger
import org.cubewhy.lunarcn.Client
import org.cubewhy.lunarcn.event.EventManager
import org.cubewhy.lunarcn.gui.hud.HudManager
import org.cubewhy.lunarcn.gui.notification.Notification
import org.cubewhy.lunarcn.utils.ClientUtils
import org.cubewhy.lunarcn.utils.MinecraftInstance

open class Module : MinecraftInstance() {
    open var state = false
        set(value) {
            if (value) {
                if (!moduleInfo.canEnabled) {
                    field = false
                    HudManager.getInstance().addNotification(
                        Notification(
                            "Error",
                            "Module " + moduleInfo.name + " can't be enable!",
                            Notification.Type.ERROR,
                            5
                        )
                    )
                    return
                }
                EventManager.register(this)
                onEnabled()
            } else {
                EventManager.unregister(this)
                onDisabled()
            }
            field = value
        }
    protected val mc: Minecraft = Minecraft.getMinecraft()
    protected val fontRenderer: FontRenderer = MinecraftInstance.fontRenderer
    protected val client: Client = Client.getInstance()
    protected val logger: Logger = ClientUtils.logger

    open val moduleImage: ResourceLocation = ResourceLocation("lunarcn/default-module-image.png")
    val moduleInfo = javaClass.getAnnotation(ModuleInfo::class.java)!!

    open fun onEnabled() {

    }

    open fun onDisabled() {

    }

    fun toggle() {
        this.state = !this.state
    }
}