package org.cubewhy.lunarcn.injection.agent;

import org.cubewhy.lunarcn.Client;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(Instrumentation instrumentation) {
        System.out.printf("[%s] Loading mixins...\n", Client.clientName);
        MixinBootstrap.init(); // Load mixin
        Mixins.addConfiguration("mixins.lunarcn.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }
}
