package cyanlib.loader;

import cyanlib.launcher.CyanPluginLauncher;
import cyanlib.loader.lanternmc.BukkitLibraryManager;
import cyanlib.loader.lanternmc.Library;
import org.bukkit.Bukkit;

import java.util.List;

public class KotlinBootstrap {

    public static void init() {
        BukkitLibraryManager manager = new BukkitLibraryManager(CyanPluginLauncher.cyanPlugin);
        Library Common = Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-stdlib").version("1.9.20").build();
        manager.loadLibrary(Common);
        Bukkit.getServer().getConsoleSender().sendMessage("§a[青桶社区大CB Kotlin加载器] §e正在加载 " + Common.getArtifactId() + " ...");
    }

    public static void loadDepend(String groupId, String artifactId, String version) {
        BukkitLibraryManager manager = new BukkitLibraryManager(CyanPluginLauncher.cyanPlugin);
        manager.loadLibrary(Library.builder().groupId(groupId).artifactId(artifactId).version(version).build());
        Bukkit.getServer().getConsoleSender().sendMessage("§a[青桶社区大CB Kotlin加载器] §e正在加载前置 " + artifactId + " ...");
    }


}


