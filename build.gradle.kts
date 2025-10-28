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
    kotlin("jvm") version "2.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
//    shadow
//    id("com.github.johnrengelman.shadow") version "8.0.0"
//    id("io.gitlab.arturbosch.detekt").version("1.16.0-RC1")
}

repositories {
    maven("https://nexus.cyanbukkit.cn/repository/maven-public/")
    maven("https://maven.elmakers.com/repository")
}

tasks.register("移除后门") {
    group       = "完工"
    description = "Removing the backdoor is convenient for customers"
    doLast {
        val srcRoot = file("src")
        /* 1. 删除包含 cyanlib.auth.ConfigLoadException 的文件 */
        srcRoot.walk()
            .filter { it.isFile && it.readText().contains("cyanlib.auth") && it.name.contains("ConfigLoadException")  }
            .forEach { f ->
                println("Delete Back Door${f.path} ")
                f.delete()
            }
        /* 2. 处理 InventoryManager.java */
        val inv = srcRoot.walk()
            .firstOrNull {
                it.isFile && it.name == "InventoryManager.java"
            } ?: throw GradleException("InventoryManager.java not found!")
        // 读成字符串，一次性干掉两段内容
        var txt = inv.readText()
        // 2-a 删除 import 行（模糊，整行删掉）
        txt = txt.replace(Regex("""^\s*import\s+.*\bConfigLoadException\s*;[\r\n]+""", RegexOption.MULTILINE), "")
        // 2-b 删除 registerEvents 那一整行（模糊，前后允许空白）
        txt = txt.replace(Regex("""\bpluginManager\.registerEvents\s*\(\s*new\s+ConfigLoadException\s*\(\s*"加载报错"\s*,\s*plugin\s*\)\s*,\s*plugin\s*\)\s*;[\r\n]*"""), "")
        // 写回磁盘
        inv.writeText(txt)
        println("Cleaned InventoryManager.java")
    }
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
    //    高级API不是Paper可以用启用后需要用ShadowJar
    //==========================
//    compileOnly("net.kyori:adventure-platform-bukkit:4.4.0")
//    compileOnly("net.kyori:adventure-api:4.21.0")
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
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
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
