# 菜单系统开发教程（SmartInventory）

## 概述
- 提供箱子 GUI 的构建与生命周期管理：打开、关闭、更新、事件监听。
- 通过 `InventoryProvider` 实现渲染与动态更新，`InventoryManager` 负责全局监听与调度。

## 初始化与启用
- 在入口 `CyanPluginLauncher#onEnable()` 中启用：`new SmartInvsPlugin().onEnable(this)`。
- 这会创建并初始化全局 `InventoryManager`，注册事件监听与定时更新任务。

## 构建与打开
```java
SmartInventory inv = SmartInventory.builder()
    .id("demo")
    .provider(new SimpleInventory())
    .size(3, 9)
    .title(ChatColor.BLUE + "Demo GUI")
    .build();

inv.open(player); // 打开到指定玩家
```

## InventoryProvider
- 渲染入口：`init(Player, InventoryContents)`
- 周期更新：`update(Player, InventoryContents)`（可选）
- 示例：`example/SimpleInventory.java` 展示了边框填充、点击事件与动态换色。

## 事件与行为
- 顶部容器点击事件被拦截并交由 `ClickableItem` 执行；底部背包的某些移动行为默认被限制。
- `InventoryManager` 会分发 `InventoryClick/Drag/Open/Close` 等事件至注册的 `InventoryListener`。
- 关闭行为：`SmartInventory#isCloseable()` 为 `false` 时会自动重新打开以阻止关闭。

## 错误处理
- 打开异常：`InventoryManager#handleInventoryOpenError` 会关闭并记录错误。
- 更新异常：`InventoryManager#handleInventoryUpdateError` 会关闭并记录错误。

## 关联源码
- `src/main/java/cn/cyanbukkit/example/cyanlib/inventory/SmartInventory.java`
- `src/main/java/cn/cyanbukkit/example/cyanlib/inventory/InventoryManager.java`
- 打开器：`inventory/opener/*`
- 内容接口：`inventory/content/*`
- 示例 Provider：`src/main/java/cn/cyanbukkit/example/example/SimpleInventory.java`