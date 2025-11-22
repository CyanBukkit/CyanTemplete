# CyanTemplete4TraeAI 项目引导

本项目为 Minecraft Bukkit/Spigot/Paper 插件开发模板，包含命令系统、箱子菜单 GUI、依赖前置加载器、Kotlin 集成、MySQL 快捷接口、计分板接口等常用开发能力。

开发教程文档已全部迁移至 `markdown/` 目录，请从下方文档索引进入对应模块教程。

## 功能总览
- 命令系统：`CyanCommand` + `@ArgumentCommand` 注解式子命令与 Tab 补全
- 菜单系统：`SmartInventory` 背包 GUI + `InventoryProvider` 渲染与更新
- 启动入口：`CyanPluginLauncher` 统一初始化、示例命令注册、计分板加载
- 依赖加载器：`KotlinBootstrap` 动态选择仓库与加载 Maven 依赖
- 数据接口：`MySQLFunction` 常用 SQL 模板方法
- 计分板接口：Paper 端自适配 Adventure/Legacy 两套实现
- Kotlin 集成：支持在 `kotlin/` 目录编写逻辑并由 Java 入口调用

## 目录结构
- `src/main/java/cn/cyanbukkit/example/cyanlib/` 基础接口与能力实现
- `src/main/java/cn/cyanbukkit/example/example/` 示例命令与示例 GUI/计分板适配器
- `src/main/kotlin/cn/cyanbukkit/example/` Kotlin 示例入口
- `src/main/resources/` 资源文件（如 `config.yml`）

## 快速开始
- 插件入口：在 `CyanPluginLauncher` 的 `onEnable()` 内统一进行 GUI/计分板初始化与命令注册
- 命令注册：继承 `CyanCommand`，在构造函数设置主命令与权限，调用 `register()` 完成注册
- GUI 使用：编写 `InventoryProvider`，通过 `SmartInventory.builder()` 构建并 `open(player)`
- 计分板：`BoardManager.load(plugin)` 后使用 `setupNewBoard(player, adapter)` 设置玩家计分板
- 依赖：通过 `KotlinBootstrap.loadDepend(groupId, artifactId, version)` 动态加载

## 文档索引
- 命令系统开发教程：`markdown/commands.md`
- 菜单系统开发教程：`markdown/inventory.md`
- 启动器、Kotlin 与加载器：`markdown/launcher_kotlin_loader.md`
- 数据库、玩家与计分板：`markdown/data_players_scoreboard.md`
- 鉴权模块与扩展：`markdown/auth.md`

如需构建与发布，请根据你的构建工具（Gradle/Maven）配置；本模板未强制绑定构建脚本，建议使用 Gradle 并引入 Spigot/Paper API 依赖。



