// import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled
/**
 * 设置入口哦
 */
val group = "cn.cyanbukkit.example" // 先更改这里
val minecraftVersion = "1.8.8"
version = "0.1"

bukkit {
    name = rootProject.name // 设置插件的名字 已设置跟随项目名
    description = "An example plugin for CyanBukkit" // 设置插件的描述
    authors = listOf("Your Name") // 设置插件作者
    website = "https://cyanbukkit.cn" // 设置插件的网站
    main = "${group}.cyanlib.launcher.CyanPluginLauncher" // 设置插件的主类 修改请到group修改
}

plugins {
    java
    kotlin("jvm") version "2.0.21"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
//    shadow
//    id("com.github.johnrengelman.shadow") version "8.0.0"
//    id("io.gitlab.arturbosch.detekt").version("1.16.0-RC1")
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public/")
    maven("https://lanternmc.coding.net/public-artifacts/cyanbukkit/public/packages/")
    maven("https://maven.elmakers.com/repository")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:${minecraftVersion}-R0.1-SNAPSHOT")
    //==========================
    //    低版本NMS组件
    //==========================
//    compileOnly("org.spigotmc:minecraft-server:${minecraftVersion}-SNAPSHOT")
//    compileOnly("org.bukkit:craftbukkit:${minecraftVersion}-R0.1-SNAPSHOT")
    // 1.20.1+
//    compileOnly("org.spigotmc:spigot-api:${minecraftVersion}-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:spigot:${minecraftVersion}-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:minecraft-server:${minecraftVersion}-R0.1-SNAPSHOT")
//    compileOnly("com.mojang:datafixerupper:6.0.8")
//    compileOnly("com.mojang:brigadier:1.2.9")
//    compileOnly("com.mojang:logging:1.1.1")
    //==========================
    //    高级API
    //==========================
    compileOnly("net.kyori:adventure-platform-bukkit:4.4.0")
    compileOnly("net.kyori:adventure-api:4.21.0")
    //==========================
    //    MySQL
    //==========================
//    compileOnly("com.zaxxer:HikariCP:5.1.0") // Java 11+
//    compileOnly("com.zaxxer:HikariCP:4.0.3") // Java 8
//    compileOnly("com.zaxxer:HikariCP:2.4.13") // Java 7
//    compileOnly("com.zaxxer:HikariCP:2.3.13") // Java 6
    //==========================
    //    Spigot计分板
    //==========================
    val scoreboardLibraryVersion = "2.3.1"
    implementation("net.megavex:scoreboard-library-api:$scoreboardLibraryVersion")
    runtimeOnly("net.megavex:scoreboard-library-implementation:$scoreboardLibraryVersion")
    implementation("net.megavex:scoreboard-library-extra-kotlin:$scoreboardLibraryVersion") // Kotlin specific extensions (optional)
    // Add packet adapter implementations you want:
    runtimeOnly("net.megavex:scoreboard-library-modern:$scoreboardLibraryVersion") // 1.17+
    runtimeOnly("net.megavex:scoreboard-library-modern:$scoreboardLibraryVersion:mojmap") // Mojang mapped variant (only use if you know what you're doing!)
    runtimeOnly("net.megavex:scoreboard-library-packetevents:$scoreboardLibraryVersion") // 1.8+
    runtimeOnly("net.megavex:scoreboard-library-legacy:$scoreboardLibraryVersion") // 1.7.10-1.12.2
    compileOnly(fileTree("libs") { include("*.jar") })
}




kotlin {
    jvmToolchain(8)
}

java {
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }
}


// root build.gradle
//detekt {
//    input = files("src/main/kotlin", "src/main/java")	// 指定需要扫描的源代码文件路径
//    config = files("config/detekt.yml")	// 指定采用的规则集文件
//    reports {	// 指定输出的报告文件类型
//        html {
//            enabled = true                        // Enable/Disable HTML report (default: true)
//            destination = file("build/reports/detekt.html") // Path where HTML report will be stored (default:
//        }
//    }
//}
