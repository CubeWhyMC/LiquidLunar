package org.cubewhy.lunarcn.injection.forge.mixins.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Logger;
import org.cubewhy.lunarcn.Client;
import org.cubewhy.lunarcn.FeaturedServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static org.cubewhy.lunarcn.utils.MinecraftInstance.mc;

@Mixin(ServerList.class)
public abstract class MixinServerList {
    @Final
    @Shadow
    private List<ServerData> servers;

    @Final
    @Shadow
    private static Logger logger;

    @Shadow
    public abstract void addServerData(ServerData serverData);

    /**
     * @author CubeWhy
     * @reason 置顶服务器
     */
    @Overwrite
    public void saveServerList() {
        try {
            NBTTagList nbttaglist = new NBTTagList();
            Iterator<ServerData> var2 = this.servers.iterator();

            while (var2.hasNext()) {
                ServerData serverdata = var2.next();
                if (serverdata instanceof FeaturedServerData) {
                    continue;
                }
                nbttaglist.appendTag(serverdata.getNBTCompound());
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setTag("servers", nbttaglist);
            CompressedStreamTools.safeWrite(nbttagcompound, new File(mc.mcDataDir, "servers.dat"));
        } catch (Exception var4) {
            logger.error("Couldn't save server list", var4);
        }
    }

    @Inject(method = "loadServerList", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;mcDataDir:Ljava/io/File;"))
    public void loadServerList(CallbackInfo ci) {
        for (FeaturedServerData serverData :
                Client.featuredServerDataList) {
            this.addServerData(serverData);
        }
    }
}
