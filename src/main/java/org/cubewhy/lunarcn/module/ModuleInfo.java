package org.cubewhy.lunarcn.module;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();

    String description() default "a Module";

    ModuleCategory category();

    boolean canEnabled() default true;
    boolean hideFromClickGui() default false;
}
