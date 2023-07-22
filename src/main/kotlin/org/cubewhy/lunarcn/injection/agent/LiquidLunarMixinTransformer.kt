package org.cubewhy.lunarcn.injection.agent

import org.spongepowered.asm.mixin.MixinEnvironment

object LiquidLunarMixinTransformer : SafeTransformer {

    /**
     * @param loader        The classloader to use to load the class.
     * @param className     The name of the class to load.
     * @param originalClass The original class' bytes.
     * @return The transformed class' bytes from `className`.
     */
    override fun transform(loader: ClassLoader, className: String, originalClass: ByteArray): ByteArray? {
        return LiquidLunarMixinService.transformer.transformClass(
            MixinEnvironment.getDefaultEnvironment(),
            className.replace('/', '.'),
            originalClass
        )
    }
}