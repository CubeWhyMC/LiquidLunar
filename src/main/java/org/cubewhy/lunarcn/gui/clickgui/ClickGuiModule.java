package org.cubewhy.lunarcn.gui.clickgui;

import org.cubewhy.lunarcn.module.Module;
import org.cubewhy.lunarcn.module.ModuleCategory;
import org.cubewhy.lunarcn.module.ModuleInfo;
import org.cubewhy.lunarcn.value.StringValues;

@ModuleInfo(name = "ClickGui", description = "ClickGui settings", category = ModuleCategory.CLIENT)
public class ClickGuiModule extends Module {
    private final StringValues styles = new StringValues("Styles", new String[]{"LiquidLunar"});
}
