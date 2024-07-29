val group = "cn.cyanbukkit.example" // 先更改这里
val version = "0.1"

bukkit {
    name = rootProject.name // 设置插件的名字 已设置跟随项目名
    description = "An example plugin for CyanBukkit" // 设置插件的描述
    authors = listOf("Your Name") // 设置插件作者
    website = "https://cyanbukkit.cn" // 设置插件的网站
    main = "${group}.cyanlib.launcher.CyanPluginLauncher" // 设置插件的主类 修改请到group修改
}

plugins {
    java
    kotlin("jvm") version "1.9.20"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public/")
    maven("https://maven.elmakers.com/repository")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly(fileTree("libs") { include("*.jar") })
//    compileOnly("com.zaxxer:HikariCP:5.1.0") // Java 11+
//    compileOnly("com.zaxxer:HikariCP:4.0.3") // Java 8
//    compileOnly("com.zaxxer:HikariCP:2.4.13") // Java 7
//    compileOnly("com.zaxxer:HikariCP:2.3.13") // Java 6
}

kotlin {
    jvmToolchain(8)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }
}
