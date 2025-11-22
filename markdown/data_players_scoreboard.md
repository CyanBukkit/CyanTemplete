# 数据库、玩家接口与计分板

## MySQL 快捷接口（MySQLFunction）
- 提供常见 SQL 模板枚举与构建方法，简化语句拼接。
- 位置：`src/main/java/cn/cyanbukkit/example/cyanlib/mysql/MySQLFunction.java`

### 常用方法
- `insert(map, table)` 生成 INSERT 语句
- `find(table, condition)` 生成条件查询
- `update(table, map, condition)` 生成更新语句

### 示例
```java
HashMap<String, Object> data = new HashMap<>();
data.put("name", "test");
data.put("value", 123);
String sql = MySQLFunction.insert(data, "my_table");
// INSERT INTO my_table (name,value) VALUES ('test','123')
```

> 说明：本枚举仅提供语句模板，数据库连接与执行需自建（建议使用已加载的 HikariCP）。

## 玩家接口（Title/TabList/NameTag）
- 位置：`src/main/java/cn/cyanbukkit/example/cyanlib/player/*`
- 当前为占位与扩展接口：
- `Title#send(Player, String, String)`：发送标题副标题（待实现）
- `TabList` 与 `NameTag`：用于后续扩展玩家 Tab/名牌显示

## 计分板（Paper 端）
- 管理器：`BoardManager`，自适配 Adventure/Legacy 两套实现
- 使用步骤：
- `BoardManager.load(plugin)` 初始化
- `BoardManager.getInstance().setupNewBoard(player, adapter)` 设置玩家计分板
- `BoardManager.getInstance().removeBoard(player)` 玩家退出时移除
- `setUpdateInterval(ticks)` 调整更新周期，`startBoardUpdaters()` 开启任务

### 适配器示例
- Adventure 组件：`example/paper/AdventureTestAdapter.java`
- Legacy 字符串：`example/paper/LegacyTestAdapter.java`
- 生命周期监听：`example/paper/ScoreBoardTimer.java`

## 关联源码
- `cyanlib/mysql/MySQLFunction.java`
- `cyanlib/player/*`
- `cyanlib/scoreboard/paper/*`
- 示例：`example/paper/*`