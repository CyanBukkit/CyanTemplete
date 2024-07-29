package cn.cyanbukkit.example.cyanlib.launcher;

import cn.cyanbukkit.example.cyanlib.loader.KotlinBootstrap;

public class JavaHandle {

    public static void getJavaVersionToDownloadHikaricp() {
        String javaVersion = System.getProperty("java.version");
        String[] split = javaVersion.split("\\.");
        int version = Integer.parseInt(split[1]);
        if (version == 8) {
            KotlinBootstrap.loadDepend("com.zaxxer","HikariCP","4.0.3");
        } else if (version >= 11) {
            KotlinBootstrap.loadDepend("com.zaxxer","HikariCP","5.1.0");
        } else if (version == 7) {
            KotlinBootstrap.loadDepend("com.zaxxer","HikariCP","2.4.13");
        } else if (version == 6) {
            KotlinBootstrap.loadDepend("com.zaxxer","HikariCP","2.3.13");
        } else {
            System.out.println("Java 8");
        }
        KotlinBootstrap.loadDepend("mysql","mysql-connector-java","8.0.25");
    }

}
