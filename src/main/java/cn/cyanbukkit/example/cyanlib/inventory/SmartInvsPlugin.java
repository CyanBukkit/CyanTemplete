package cn.cyanbukkit.example.cyanlib.inventory;

import org.bukkit.plugin.java.JavaPlugin;

public class SmartInvsPlugin {

    private static SmartInvsPlugin instance;
    private static InventoryManager invManager;
    // 加载的启动入口
    public void onEnable(JavaPlugin javaPlugin) {
        instance = this;
        invManager = new InventoryManager(javaPlugin);
        invManager.init();
    }

    public static InventoryManager manager() { return invManager; }
    public static SmartInvsPlugin instance() { return instance; }

}
