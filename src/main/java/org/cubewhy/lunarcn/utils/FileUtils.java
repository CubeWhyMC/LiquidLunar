package org.cubewhy.lunarcn.utils;

import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public class FileUtils {
    private static final FileUtils instance = new FileUtils();

    public static FileUtils getInstance() {
        return instance;
    }

    public InputStream getFile(String pathToFile) {
        return this.getClass().getResourceAsStream("/assets/minecraft/" + (new ResourceLocation(pathToFile)).getResourcePath());
    }

    public InputStream getFile(ResourceLocation location) {
        return getFile(location.getResourcePath());
    }
}
