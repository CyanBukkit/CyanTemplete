package cn.cyanbukkit.example.cyanlib.launcher;

import cn.cyanbukkit.example.cyanlib.loader.KotlinBootstrap;

/**
 * 高级开发API初始化
 */
public class AdventureAPIInitialization {

    public static final String VERSION = "4.21.0";

    public static void init() {
        KotlinBootstrap.loadDepend("net.kyori","adventure-api", VERSION);
        KotlinBootstrap.loadDepend("net.kyori","adventure-key" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-nbt" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-platform-api", "4.4.0");
        KotlinBootstrap.loadDepend("net.kyori","adventure-platform-bukkit", "4.4.0");
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-bungeecord", "4.4.0");
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-gson" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-json" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-legacy" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","examination-api", "1.3.0");
        KotlinBootstrap.loadDepend("net.kyori","examination-string", "1.3.0");
        // idea 缓存里没有
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-plain" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-minimessage" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-logger-slf4j" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-ansi" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-gson-legacy-impl" ,VERSION );
        KotlinBootstrap.loadDepend("net.kyori","adventure-text-serializer-json-legacy-impl" ,VERSION );
    }


    /**
     * 拓展包自由拓展
     */
    public static void expand() {
        KotlinBootstrap.loadDepend("net.kyori", "text-api", "3.0.4");
        KotlinBootstrap.loadDepend("net.kyori", "mammoth", "1.4.0");
        KotlinBootstrap.loadDepend("net.kyori", "indra-common", "3.1.3");
        KotlinBootstrap.loadDepend("net.kyori", "text-serializer-gson", "3.0.4");
        KotlinBootstrap.loadDepend("net.kyori", "event-api", "3.0.0");
    }
}
