# 启动器、Kotlin 集成与依赖加载器

## CyanPluginLauncher（插件入口）
- 位置：`src/main/java/cn/cyanbukkit/example/cyanlib/launcher/CyanPluginLauncher.java`
- 核心职责：
- 初始化 Kotlin 与前置依赖：`KotlinBootstrap.init()`、`MySQLInitialization.getJavaVersionToDownloadHikaricp()`
- 启用 GUI：`new SmartInvsPlugin().onEnable(this)`
- 注册示例命令：`new MyCommand("test").register()`
- 初始化计分板：`BoardManager.load(this)` 并设置更新周期、启动更新任务
- Paper 计分板兼容：优先加载 `ScoreboardLibrary`，失败则回退到 `NoopScoreboardLibrary`

## KotlinBootstrap（依赖前置加载器）
- 自动选择仓库：能连通 Google 则使用 `repo.maven.apache.org`，否则使用阿里云镜像
- 加载 Kotlin 标准库：`org.jetbrains.kotlin:kotlin-stdlib:2.0.21`
- 动态加载依赖：`loadDepend(groupId, artifactId, version)`，用于在运行时拉取并挂载 jar

### 示例：加载计分板依赖
```java
String ver = "2.3.1";
KotlinBootstrap.loadDepend("net.megavex", "scoreboard-library-api", ver);
KotlinBootstrap.loadDepend("net.megavex", "scoreboard-library-extra-kotlin", ver);
```

## MySQLInitialization（按 JDK 版本选择 HikariCP）
- 通过 `java.class.version` 判断字节码版本，选择兼容的 `HikariCP` 版本，并加载 MySQL 驱动及日志依赖。
- 已内置：`mysql-connector-java:8.0.25`、`slf4j-api:2.0.17` 等。

## 与 Kotlin 代码协作
- Kotlin 代码存放在 `src/main/kotlin`，示例：`cn.cyanbukkit.example.Example`
- 在 Java 入口直接调用 Kotlin 对象或类；确保先执行 `KotlinBootstrap.init()` 保证 Kotlin 运行时可用。

## 最佳实践
- 将所有第三方库以 `loadDepend` 的方式声明，避免手动打入插件 jar 体积过大或环境不一致。
- 在 `onEnable()` 中完成 GUI/计分板/命令注册，保持插件初始化集中与可维护。

## 关联源码
- `launcher/CyanPluginLauncher.java`
- `launcher/MySQLInitialization.java`
- `loader/KotlinBootstrap.java`