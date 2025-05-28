package cn.cyanbukkit.example.cyanlib.launcher;

import cn.cyanbukkit.example.cyanlib.loader.KotlinBootstrap;

/**
 * MySQL的初始化
 */
public class MySQLInitialization {

    // 17 = 61.0
    // 11 = 55.0
    // 8 = 52.0
    public static void getJavaVersionToDownloadHikaricp() {
        double classVersion = Double.parseDouble(System.getProperty("java.class.version"));
        System.out.println("classVersion: " + classVersion);
        if (classVersion >= 55.0) {
            KotlinBootstrap.loadDepend("com.zaxxer", "HikariCP", "5.1.0");
        } else if (classVersion < 55.0 && classVersion >= 52.0) {
            KotlinBootstrap.loadDepend("com.zaxxer", "HikariCP", "4.0.3");
        } else {
            KotlinBootstrap.loadDepend("com.zaxxer", "HikariCP", "2.4.13");
        }
        KotlinBootstrap.loadDepend("mysql", "mysql-connector-java", "8.0.25");
        KotlinBootstrap.loadDepend("org.slf4j", "slf4j-api", "2.0.17");
        KotlinBootstrap.loadDepend("org.java-websocket", "Java-WebSocket", "1.6.0");
        KotlinBootstrap.loadDepend("com.alibaba", "fastjson", "2.0.57");
        KotlinBootstrap.loadDepend("com.alibaba.fastjson2", "fastjson2", "2.0.57");
        KotlinBootstrap.loadDepend("com.alibaba.fastjson2", "fastjson2-extension", "2.0.57");
    }

}
