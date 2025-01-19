package cn.cyanbukkit.example.cyanlib.launcher;

import cn.cyanbukkit.example.command.MyCommand;
import cn.cyanbukkit.example.cyanlib.inventory.SmartInvsPlugin;
import cn.cyanbukkit.example.cyanlib.loader.KotlinBootstrap;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * 嵌套框架
 */

public class CyanPluginLauncher extends JavaPlugin {

    public static CyanPluginLauncher cyanPlugin;

    public CyanPluginLauncher() {
        cyanPlugin = this;
        KotlinBootstrap.init();
        JavaHandle.getJavaVersionToDownloadHikaricp();
    }


    @Override
    public void onLoad() {
    }


    @Override
    public void onEnable() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("CyanBukkit验收插件前围栏请按照技术的指引进行解锁");
            input = scanner.nextLine();
        } while (!"CYANBUKKIT".equalsIgnoreCase(input));
        // 发给客户： 启动时输入CYANBUKKIT进行解锁 验收插件后会删掉这个围栏的哦！
        
        new SmartInvsPlugin().onEnable(this); // 写GUI必要的加载入口
    }

    @Override
    public void onDisable() {

    }



}