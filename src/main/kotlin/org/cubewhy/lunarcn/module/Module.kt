package org.cubewhy.lunarcn.module

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.Client
import org.cubewhy.lunarcn.event.EventManager
import org.cubewhy.lunarcn.gui.hud.HudManager
import org.cubewhy.lunarcn.utils.MinecraftInstance

open class Module : MinecraftInstance() {
    open var state = false
        set(value) {
            if (value) {
                EventManager.register(this)
                onEnabled()
            } else {
                EventManager.unregister(this)
                onDisabled()
            }
            field = value
        }
    protected val mc: Minecraft = Minecraft.getMinecraft()
    protected val fontRenderer: FontRenderer = mc.fontRendererObj
    protected val client: Client = Client.getInstance()

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