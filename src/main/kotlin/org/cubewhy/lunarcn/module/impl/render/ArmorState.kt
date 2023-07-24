package org.cubewhy.lunarcn.module.impl.render

import net.minecraft.client.renderer.RenderHelper
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import org.cubewhy.lunarcn.gui.hud.ScreenPosition
import org.cubewhy.lunarcn.module.ModuleCategory
import org.cubewhy.lunarcn.module.ModuleDraggable
import org.cubewhy.lunarcn.module.ModuleInfo
import org.lwjgl.opengl.GL11
import java.awt.Color

@ModuleInfo(name = "ArmorState", description = "Render your armor you wear", category = ModuleCategory.RENDER)
class ArmorState : ModuleDraggable() {

    private var position: ScreenPosition = ScreenPosition.fromRelativePosition(0.5, 0.5)

    override fun save(position: ScreenPosition) {
        this.position = position;
    }

    override fun load(): ScreenPosition {
        return position
    }

    override fun getWidth(): Int {
        return 64
    }

    override fun getHeight(): Int {
        return 64
    }

    override fun render(position: ScreenPosition) {
        for (i in 0 until mc.thePlayer.inventory.armorInventory.size) {
            val item: ItemStack? = mc.thePlayer.inventory.armorInventory[i]
            if (item != null) {
                renderItemStack(position, i, item)
            }
        }
    }

    override fun renderDummy(position: ScreenPosition) {
        renderItemStack(position, 3, ItemStack(Items.diamond_helmet))
        renderItemStack(position, 2, ItemStack(Items.diamond_chestplate))
        renderItemStack(position, 1, ItemStack(Items.diamond_leggings))
        renderItemStack(position, 0, ItemStack(Items.diamond_boots))
    }

    private fun renderItemStack(position: ScreenPosition, i: Int, itemStack: ItemStack?) {
        if (itemStack == null) {
            return;
        }

        GL11.glPushMatrix();

        val yAdd = (-16 * i) + 48

        if (itemStack.item.isDamageable) {
            val damage = ((itemStack.maxDamage - itemStack.itemDamage) / itemStack.maxDamage.toDouble()) * 100
            fontRenderer.drawString(
                String.format("%.2f%%", damage),
                position.absoluteX + 20,
                position.absoluteY + yAdd + 5,
                Color(255, 255, 255).rgb
            )
        }

        RenderHelper.enableGUIStandardItemLighting()
        mc.renderItem.renderItemAndEffectIntoGUI(itemStack, position.absoluteX, position.absoluteY + yAdd)
        GL11.glPopMatrix()
    }
}