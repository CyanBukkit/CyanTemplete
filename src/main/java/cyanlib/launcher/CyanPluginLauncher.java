package cyanlib.launcher;

import cyanlib.loader.KotlinBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 嵌套框架
 */

public class CyanPluginLauncher extends JavaPlugin {

    public static CyanPluginLauncher cyanPlugin;

    public CyanPluginLauncher() {
        cyanPlugin = this;
        KotlinBootstrap.init();
    }




    @Override
    public void onLoad() {
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }



}