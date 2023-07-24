package org.cubewhy.lunarcn.module;

import org.cubewhy.lunarcn.event.EventManager;
import org.cubewhy.lunarcn.files.configs.ModuleConfigFile;
import org.cubewhy.lunarcn.files.configs.PositionConfigFile;
import org.cubewhy.lunarcn.gui.clickgui.ClickGuiModule;
import org.cubewhy.lunarcn.gui.hud.HudManager;
import org.cubewhy.lunarcn.gui.hud.IRenderer;
import org.cubewhy.lunarcn.utils.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;


public class ModuleManager {

    private static final ModuleManager instance = new ModuleManager();

    private final ArrayList<Module> registeredModules = new ArrayList<>();

    public static ModuleManager getInstance() {
        return instance;
    }

    public void registerModules() {
        this.register(new ClickGuiModule());

        ClassUtils.INSTANCE.resolvePackage(this.getClass().getPackage().getName() + ".impl", Module.class)
                .forEach(this::register);
    }


    /*
     * 实例化模块并注册
     */
    private void register(Class<? extends Module> moduleClass) {
        logger.info("Loading module " + moduleClass.getName());
        try {
            register(moduleClass.newInstance());
        } catch (IllegalAccessException e) {
            register((Module) ClassUtils.INSTANCE.getObjectInstance(moduleClass));
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("Can't load module " + moduleClass.getName());
        }
    }

    public void register(@NotNull Module module) {
        ModuleConfigFile.getInstance().initModule(module); // Configure file

        System.out.println("Module " + module.getModuleInfo().name() + " state: " + module.getState());

        if (module.getState()) {
            try {
                HudManager.getInstance().register((IRenderer) module); // register by HudManager
                if (module instanceof ModuleDraggable) {
                    PositionConfigFile.getInstance().initModule((ModuleDraggable) module); // Set position
                }
            } catch (ClassCastException throwable) {
                EventManager.register(module); // register events
            }
        }

        registeredModules.add(module);
    }


    /*
     * 获取注册过的模块
     * */
    public ArrayList<Module> getRegisteredModules() {
        return this.registeredModules;
    }

    public Module getModule(Class<?extends Module> module) {
        for (Module module1 : getRegisteredModules()) {
            if (module1.getClass().equals(module)) {
                return module1;
            }
        }
        return null;
    }
}
