package org.cubewhy.lunarcn.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.files.configs.PositionConfigFile;
import org.cubewhy.lunarcn.gui.clickgui.ClickGui;
import org.cubewhy.lunarcn.gui.elements.ClientButton;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import static org.cubewhy.lunarcn.utils.RenderUtils.drawHollowRect;

public class HudDesigner extends GuiScreen {
    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();
    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;
    private GuiButton settingsButton;

    public HudDesigner(HudManager hudManager) {
        EventManager.register(this);

        Collection<IRenderer> registeredRenderers = hudManager.getRegisteredRenders();

        for (IRenderer renderer : registeredRenderers) {
            if (!renderer.isEnabled()) {
                continue;
            }
            ScreenPosition position = renderer.load();

            if (position == null) {
                position = ScreenPosition.fromRelativePosition(0.5, 0.5);
            }

            moveElement(renderer, position);
            this.renderers.put(renderer, position);
        }

    }

    private void moveElement(IRenderer renderer, ScreenPosition position) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        int absoluteX = Math.max(0, Math.min(position.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(position.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        position.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    public void initGui() {
        this.buttonList.add(this.settingsButton = new ClientButton(1, this.width / 2 - 100, this.height / 2 + 30, "SETTINGS", true));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        final float zBackUp = this.zLevel;
        this.zLevel = 200;

        drawHollowRect(0, 0, this.width - 1, this.height - 1, new Color(255, 0, 0).getRGB());

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition position = renderers.get(renderer);

            renderer.renderDummy(position);

            drawHollowRect(position.getAbsoluteX(), position.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), new Color(108, 218, 202).getRGB());
        }


        this.zLevel = zBackUp;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            renderers.forEach(IRenderConfig::save);
            PositionConfigFile positionFile = PositionConfigFile.getInstance();
            renderers.forEach(positionFile::saveModule);
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseClickMove(int x, int y, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(x, y, clickedMouseButton, timeSinceLastClick);
        if (selectedRenderer.isPresent()) {
            moveSelectedRenderBy(x - prevX, y - prevY);
        }

        this.prevX = x;
        this.prevY = y;
    }


    @Override
    public void mouseClicked(int x, int y, int mouseButton) throws IOException {
        super.mouseClicked(x, y, mouseButton);
        this.prevX = x;
        this.prevY = y;

        loadMouseOver(x, y);
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition position = renderers.get(renderer);
        position.setAbsolute(position.getAbsoluteX() + offsetX, position.getAbsoluteY() + offsetY);

        moveElement(renderer, position);
    }

    @Override
    public void onGuiClosed() {
        for (IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }
    }

    @Override
    public void actionPerformed(@NotNull GuiButton button) {
        switch (button.id) {
            case 1:
                mc.displayGuiScreen(new ClickGui());
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private class MouseOverFinder implements Predicate<IRenderer> {

        private int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        /**
         * Evaluates this predicate on the given argument.
         *
         * @param renderer the input argument
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition position = renderers.get(renderer);

            int absoluteX = position.getAbsoluteX();
            int absoluteY = position.getAbsoluteY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
                return mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight();
            }

            return false;
        }
    }
}
