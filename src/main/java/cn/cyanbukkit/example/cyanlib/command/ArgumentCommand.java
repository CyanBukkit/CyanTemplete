package cn.cyanbukkit.example.cyanlib.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentCommand {
    String name();
    String permission() default "";
    String description() default "";
}
