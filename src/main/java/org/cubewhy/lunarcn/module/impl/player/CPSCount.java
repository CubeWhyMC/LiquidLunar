package org.cubewhy.lunarcn.module.impl.player;

import org.cubewhy.lunarcn.gui.hud.ScreenPosition;
import org.cubewhy.lunarcn.module.ModuleCategory;
import org.cubewhy.lunarcn.module.ModuleDraggable;
import org.cubewhy.lunarcn.module.ModuleInfo;
import org.cubewhy.lunarcn.value.BooleanValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

@ModuleInfo(name = "CPSCount", description = "Render your CPS on screen", category = ModuleCategory.PLAYER)
public class CPSCount extends ModuleDraggable {
    private final ArrayList<Long> clicksLeft = new ArrayList<>();
    private final ArrayList<Long> clicksRight = new ArrayList<>();
    private final BooleanValue showRightButton = new BooleanValue("ShowRightButton", false);
    private ScreenPosition position;
    private String cpsText = "";
    private long leftLastPressed = -1;
    private long rightLastPressed = -1;

    private boolean leftHasPressed = false;
    private boolean rightHasPressed = false;

    @Override
    public void save(ScreenPosition position) {
        this.position = position;
    }

    @Override
    public ScreenPosition load() {
        if (this.position == null) {
            this.position = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }
        return this.position;
    }

    @Override
    public int getWidth() {
        return getFontRenderer().getStringWidth(this.cpsText);
    }

    @Override
    public int getHeight() {
        return getFontRenderer().FONT_HEIGHT;
    }

    @Override
    public void render(@NotNull ScreenPosition position) {
        // Update cps text
        this.cpsText = "CPS: " + getLeftCPS(); // left cps
        if (this.showRightButton.getValue()) {
            this.cpsText += " | " + getRightCPS(); // render right cps
        }

        // Render text
        getFontRenderer().drawString(cpsText, position.getAbsoluteX(), position.getAbsoluteY(), new Color(255, 255, 255).getRGB());

        // process mouse clicks
        final boolean leftState = Mouse.isButtonDown(0);
        final boolean rightState = Mouse.isButtonDown(3);

        if (leftState ^ this.leftHasPressed) {
            // left key
            this.leftLastPressed = System.currentTimeMillis();
            if (leftState) {
                this.clicksLeft.add(this.leftLastPressed);
            }
            this.leftHasPressed = leftState;
        }

        if (rightState ^ this.rightHasPressed) {
            // right key
            this.rightLastPressed = System.currentTimeMillis();
            if (rightState) {
                this.clicksRight.add(this.rightLastPressed);
            }
            this.rightHasPressed = leftState;
        }
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        super.renderDummy(position);
    }

    private int getLeftCPS() {
        long time = System.currentTimeMillis();
        this.clicksLeft.removeIf(clickTime -> clickTime + 1000 < time); // 移除过久的点击
        return this.clicksLeft.size();
    }

    private int getRightCPS() {
        long time = System.currentTimeMillis();
        this.clicksRight.removeIf(clickTime -> clickTime + 1000 < time); // 移除过久的点击
        return this.clicksRight.size();
    }
}
