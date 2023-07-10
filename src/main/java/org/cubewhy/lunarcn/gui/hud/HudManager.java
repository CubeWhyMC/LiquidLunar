package org.cubewhy.lunarcn.gui.hud;

import com.google.common.collect.Sets;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.event.EventTarget;
import org.cubewhy.lunarcn.event.events.Render2DEvent;
import org.cubewhy.lunarcn.event.events.RenderEvent;
import org.cubewhy.lunarcn.gui.notification.Notification;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

public class HudManager {

    private static HudManager instance = null;
    private final Set<IRenderer> registeredRenders = Sets.newHashSet();
    private final LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    private Notification currentNotification = null;

    private HudManager() {

    }

    public static HudManager getInstance() {
        if (instance == null) {
            instance = new HudManager();
            EventManager.register(instance);
        }
        return instance;
    }

    public void register(IRenderer... renderers) {
        this.registeredRenders.addAll(Arrays.asList(renderers));
        Arrays.asList(renderers).forEach(EventManager::register);
    }

    public void unregister(IRenderer... renderers) {
        Arrays.asList(renderers).forEach(this.registeredRenders::remove);
        Arrays.asList(renderers).forEach(EventManager::unregister);
    }

    public Collection<IRenderer> getRegisteredRenders() {
        return Sets.newHashSet(registeredRenders);
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new HudDesigner(this));
    }

    @EventTarget
    public void onRender(RenderEvent event) {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) {
            for (IRenderer renderer : registeredRenders) {
                callRenderer(renderer);
            }
        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        this.updateNotifications();
        if (currentNotification!= null) {
            currentNotification.render();
        }
    }

    private void callRenderer(@NotNull IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }

        ScreenPosition position = renderer.load();

        if (position == null) {
            position = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        renderer.render(position);
    }

    public void addNotification(Notification notification) {
        pendingNotifications.add(notification);
    }

    private void updateNotifications() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }
    }
}
