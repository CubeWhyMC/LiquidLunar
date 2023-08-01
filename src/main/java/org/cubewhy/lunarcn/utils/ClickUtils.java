package org.cubewhy.lunarcn.utils;

public class ClickUtils {
    public static long getRandomClickCPS(int min, int max) {
        return (long) (Math.random() * (1000 / min - 1000 / max + 1) + 1000 / max);
    }
}
