import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

val group = "cn.cyanbukkit.example" // 先更改这里
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
//    id("io.gitlab.arturbosch.detekt").version("1.16.0-RC1")
    kotlin("jvm") version "2.0.21"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    //shadow
//    id("com.github.johnrengelman.shadow") version "8.0.0"
}

repositories {
    maven("https://lanternmc.coding.net/public-artifacts/cyanbukkit/public/packages/")
    maven("https://maven.elmakers.com/repository")
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly(fileTree("libs") { include("*.jar") })
//    compileOnly("com.zaxxer:HikariCP:5.1.0") // Java 11+
//    compileOnly("com.zaxxer:HikariCP:4.0.3") // Java 8
//    compileOnly("com.zaxxer:HikariCP:2.4.13") // Java 7
//    compileOnly("com.zaxxer:HikariCP:2.3.13") // Java 6
    implementation(kotlin("stdlib-jdk8"))
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
