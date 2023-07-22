package org.cubewhy.lunarcn.injection.agent;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentLoader {
    public static void premain(String args, @NotNull Instrumentation instrumentation) {
        instrumentation.addTransformer(
                new SafeTransformer() {
                    @Override
                    public byte @Nullable [] transform(@NotNull ClassLoader loader, @NotNull String className, @Nullable Class<?> classBeingRedefined, @Nullable ProtectionDomain protectionDomain, @NotNull byte[] classfileBuffer) {
                        try {
                            return this.transform(loader, className, classfileBuffer);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                        return null;
                    }

                    @SneakyThrows
                    @Override
                    public byte @Nullable [] transform(@NotNull ClassLoader loader, @NotNull String className, byte @NotNull [] originalClass) {
                        if (className.startsWith("net/minecraft/client/")) {
                            instrumentation.removeTransformer(this);
                            loader.loadClass("org.cubewhy.lunarcn.injection.agent.Agent").getDeclaredMethod("init", Instrumentation.class).invoke(null, instrumentation);
                        }
                        return null;
                    }
                }
        );
    }
}
