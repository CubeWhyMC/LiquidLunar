package org.cubewhy.lunarcn;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import static org.cubewhy.lunarcn.utils.ClientUtils.logger;

@Mod(modid = ForgeMod.modId, name = Client.clientName, version = "unknown", clientSideOnly = true)
public class ForgeMod {
    public static final String modId = "lunarcn";

    @Mod.Instance(ForgeMod.modId)
    public static ForgeMod instance;

    @Mod.EventHandler
    public void init(FMLServerStartingEvent event) {
        logger.info("FML Init");
    }
}
