package cn.cyanbukkit.allcmd.cyanlib.loader;

import cn.cyanbukkit.allcmd.cyanlib.launcher.CyanPluginLauncher;
import cn.cyanbukkit.allcmd.cyanlib.loader.lanternmc.BukkitLibraryManager;
import cn.cyanbukkit.allcmd.cyanlib.loader.lanternmc.Library;
import org.bukkit.Bukkit;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class KotlinBootstrap {

    public static void init() {
        //
        BukkitLibraryManager manager = new BukkitLibraryManager(CyanPluginLauncher.cyanPlugin);
        // 根据联网的是否能正常链接Google判定地区在哪连不上就是国内
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.connect();
            manager.addRepository("https://repo.maven.apache.org/maven2/");
        } catch (Exception e) {
            manager.addRepository("https://maven.aliyun.com/repository/public");
        }
        Library Common = Library.builder().groupId("org.jetbrains.kotlin").artifactId("kotlin-stdlib").version("1.9.20").build();
        manager.loadLibrary(Common);
        Bukkit.getServer().getConsoleSender().sendMessage("§a[青桶社区大CB Kotlin加载器] §e正在加载 " + Common.getArtifactId() + " ...");
    }

    public static void loadDepend(String groupId, String artifactId, String version) {
        BukkitLibraryManager manager = new BukkitLibraryManager(CyanPluginLauncher.cyanPlugin);
        // 根据联网的是否能正常链接Google判定地区在哪连不上就是国内
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            manager.addRepository("https://repo.maven.apache.org/maven2/");
        } catch (Exception e) {
            manager.addRepository("https://maven.aliyun.com/repository/central");
        }
        manager.loadLibrary(Library.builder().groupId(groupId).artifactId(artifactId).version(version).build());
        Bukkit.getServer().getConsoleSender().sendMessage("§a[青桶社区大CB Kotlin加载器] §e正在加载前置 " + artifactId + " ...");
    }


}


