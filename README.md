# CyanCommand 类开发文档

## 概述

`CyanCommand` 是 `cn.cyanbukkit.example.cyanlib.command` 包中的一个抽象类，旨在简化 Bukkit 插件中命令的注册、执行和补全逻辑。它提供了一个统一的接口，用于处理主命令、子命令、权限检查、帮助信息和自动补全。开发者可以通过继承此抽象类并实现特定方法来创建自定义命令。

此类使用 Bukkit API 和反射机制来注册命令，并支持子命令通过注解 `@ArgumentCommand` 来定义。它的设计目标是减少命令注册的复杂性，提高代码的可读性和可维护性。

### 关键特性
- **命令注册**：通过 `register()` 方法自动注册命令到 Bukkit 的命令系统。
- **权限管理**：支持权限检查和自定义权限。
- **子命令支持**：使用 `@ArgumentCommand` 注解定义子命令，自动处理执行和补全。
- **帮助信息**：自动生成基于命令描述和子命令的帮助消息。
- **命令补全**：提供 Tab 补全功能，支持子命令的自动补全。
- **灵活性**：通过多种构造函数支持不同程度的配置。

## 字段

`CyanCommand` 类定义了以下公共字段，用于存储命令的基本信息。这些字段可以在构造函数中初始化。

| 字段名称 | 类型 | 描述 |
|----------|------|------|
| `commandText` | `String` | 命令的主文本，例如 "cyan"。必须在构造函数中设置，否则注册时会抛出 `NullPointerException`。 |
| `needPermission` | `boolean` | 是否需要权限来执行命令。默认为 `false`，在有权限设置时自动设为 `true`。 |
| `permission` | `String` | 权限字符串，例如 "myplugin.use"。如果需要权限，此字段必须设置。 |
| `aliases` | `List<String>` | 命令的别名列表，例如 ["alias1", "alias2"]。如果未设置，将默认为空列表。 |
| `aliases` | `String` | 命令的描述，用于帮助信息和命令注册。 |

## 构造函数

`CyanCommand` 提供了多个重载构造函数，允许开发者根据需求灵活配置命令。以下是构造函数的详细列表：

| 构造函数 | 参数 | 描述 |
|----------|------|------|
| `CyanCommand(String cmd)` | `cmd`: 命令主文本 | 快捷注册无权限限制的命令。权限相关字段默认未设置。 |
| `CyanCommand(String cmd, String mainPermission)` | `cmd`: 命令主文本<br>`mainPermission`: 权限字符串 | 注册有权限限制的命令。自动设置 `needPermission` 为 `true`。 |
| `CyanCommand(String cmd, String mainPermission, String description)` | `cmd`: 命令主文本<br>`mainPermission`: 权限字符串<br>`description`: 命令描述 | 注册有权限限制和描述的命令。 |
| `CyanCommand(String cmd, String mainPermission, String description, String... aliases)` | `cmd`: 命令主文本<br>`mainPermission`: 权限字符串<br>`description`: 命令描述<br>`aliases`: 命令别名（可变参数） | 注册有权限限制、描述和别名的命令。别名将转换为列表。 |

**示例使用**：
```java
// 无权限命令
CyanCommand cmd1 = new MyCommand("test");

// 有权限命令
CyanCommand cmd2 = new MyCommand("testcmd", "myplugin.use");

// 有权限、描述和别名
CyanCommand cmd3 = new MyCommand("advanced", "myplugin.advanced", "这是一个高级命令", "adv", "a");
```

## 方法

### 抽象方法

这些方法必须由子类实现。

| 方法名称 | 返回类型 | 参数 | 描述 |
|----------|----------|------|------|
| `mainExecute(CommandSender sender, String commandLabel, String[] args)` | `void` | `sender`: 执行命令的发送者<br>`commandLabel`: 命令标签<br>`args`: 命令参数数组 | 主命令执行逻辑。当命令被执行且无子命令匹配时调用。子类必须重写此方法来定义主命令的行为。 |

### 公共方法

这些方法可以被子类覆盖或直接使用。

| 方法名称 | 返回类型 | 参数 | 描述 |
|----------|----------|------|------|
| `help(CommandSender sender)` | `String` | `sender`: 执行命令的发送者 | 生成并返回命令的帮助信息。自动包含主命令描述和所有子命令的列表。使用 `@ArgumentCommand` 注解标注的方法会被扫描并包含在内。默认实现可被覆盖。 |
| `subCommand(CommandSender sender, String commandLabel, String[] args)` | `void` | `sender`: 执行命令的发送者<br>`commandLabel`: 命令标签<br>`args`: 子命令参数数组 | 默认的子命令执行方法。通常不需要重写；子命令应通过定义新方法并使用 `@ArgumentCommand` 注解来实现。 |
| `tab(CommandSender sender, String[] args)` | `List<String>` | `sender`: 执行命令的发送者<br>`args`: 当前参数数组 | 提供命令补全（Tab 补全）逻辑。默认实现扫描所有带有 `@ArgumentCommand` 注解的方法，并返回子命令名称作为补全建议。子类可以覆盖此方法来实现自定义补全。 |
| `register()` | `void` | 无 | 注册命令到 Bukkit 的命令系统。使用反射机制注册主命令和处理子命令。必须在插件加载后调用。 |

### 注解

`CyanCommand` 类依赖于自定义注解 `@ArgumentCommand` 来定义子命令。以下是 `@ArgumentCommand` 的关键属性：

| 注解属性 | 类型 | 描述 | 默认值 |
|----------|------|------|--------|
| `name()` | `String` | 子命令的名称，例如 "give"。 | 无，必须指定 |
| `description()` | `String` | 子命令的描述，用于帮助信息。 | "" |
| `permission()` | `String` | 子命令的权限字符串。如果为空，则不检查权限。 | "" |

**如何使用 `@ArgumentCommand` 注解**：
- 定义一个方法，参数必须为 `(CommandSender sender, String commandLabel, String[] args)`。
- 使用 `@ArgumentCommand` 注解标注该方法。
- 该方法将被自动扫描并注册为子命令。

**示例**：
```java
@ArgumentCommand(name = "give", description = "给予物品", permission = "myplugin.give")
public void giveCommand(CommandSender sender, String commandLabel, String[] args) {
    // 子命令逻辑
    if (args.length >= 2) {
        String playerName = args[0];
        String itemName = args[1];
        // 执行给予物品逻辑
    } else {
        sender.sendMessage("用法: /command give <player> <item>");
    }
}
```

## 使用指南

### 步骤
1. **继承 `CyanCommand`**：创建一个子类继承 `CyanCommand`，并在构造函数中调用合适的父类构造函数设置命令文本、权限等。
2. **实现抽象方法**：重写 `mainExecute` 方法来处理主命令逻辑。
3. **定义子命令**（可选）：
    - 定义新方法，参数为 `(CommandSender sender, String commandLabel, String[] args)`。
    - 使用 `@ArgumentCommand` 注解标注方法，指定名称、描述和权限。
4. **覆盖其他方法**（可选）：
    - 覆盖 `help` 方法来自定义帮助信息。
    - 覆盖 `tab` 方法来实现自定义命令补全。
5. **注册命令**：在插件的 `onEnable` 方法中，创建命令实例并调用 `register()` 方法。
    - 示例：在 `CyanPluginLauncher` 或插件主类中：
      ```java
      public void onEnable() {
          MyCommand myCommand = new MyCommand();
          myCommand.register();
      }
      ```

### 示例代码

以下是一个完整的示例，演示如何创建一个自定义命令。

```java
import org.bukkit.command.CommandSender;
import cn.cyanbukkit.example.cyanlib.command.ArgumentCommand;
import cn.cyanbukkit.example.cyanlib.command.CyanCommand;
import java.util.Arrays;

public class MyCommand extends CyanCommand {

    public MyCommand() {
        super("mycmd", "myplugin.use", "这是一个示例命令，用于演示 CyanCommand 的使用", "mc", "cmd");
    }

    @Override
    public void mainExecute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("主命令执行成功！参数: " + Arrays.toString(args));
    }

    @ArgumentCommand(name = "help", description = "显示自定义帮助信息")
    public void customHelp(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("自定义帮助: 使用 /mycmd help 来查看更多。");
    }

    @ArgumentCommand(name = "give", description = "给予玩家物品", permission = "myplugin.give")
    public void giveItem(CommandSender sender, String commandLabel, String[] args) {
        if (args.length >= 2) {
            String playerName = args[0];
            String itemName = args[1];
            sender.sendMessage("给予 " + playerName + " 物品 " + itemName);
            // 这里添加实际逻辑，例如使用 Bukkit API 给予物品
        } else {
            sender.sendMessage("用法: /mycmd give <player> <item>");
        }
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "give"); // 自定义补全建议
        }
        return super.tab(sender, args); // 调用父类方法以包含注解定义的子命令
    }
}
```

在插件主类中注册：
```java
import cn.cyanbukkit.example.cyanlib.launcher.CyanPluginLauncher;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // 注册命令
        MyCommand myCommand = new MyCommand();
        myCommand.register();
    }
}
```

### 注意事项
- **权限检查**：命令执行时会自动检查权限。如果子命令有特定权限，确保在 `@ArgumentCommand` 中设置 `permission()` 属性。
- **注解一致性**：子命令方法必须使用 `@ArgumentCommand` 注解。代码中提到 `@RegisterSubCommand`，但实际使用的是 `@ArgumentCommand`，请确保使用正确的注解（可能为笔误）。
- **参数顺序**：子命令方法的参数必须严格为 `(CommandSender sender, String commandLabel, String[] args)`，否则反射机制可能无法正确调用。
- **注册时机**：`register()` 方法应在插件启用时调用。使用反射注册命令，可能在某些 Bukkit 版本中需要处理权限或异常。
- **补全逻辑**：默认 `tab` 方法基于 `@ArgumentCommand` 注解提供补全。如果需要更复杂的补全，覆盖 `tab` 方法。
- **错误处理**：如果 `commandText` 为 null，注册时会抛出异常。确保在构造函数中正确设置。
- **依赖**：此类依赖 `CyanPluginLauncher` 和 Bukkit API，确保插件环境正确。

通过 `CyanCommand`，开发者可以快速构建复杂的命令系统，减少重复代码。如果有自定义需求，可以扩展或覆盖现有方法。

# SmartInventory 开发文档

## 概述

`SmartInventory` 是一个用于创建和管理自定义库存（Inventory）的类，它提供了一个简单且灵活的方式来构建和管理 Minecraft 服务器中的库存界面。

## 类和接口

### SmartInventory 类

`SmartInventory` 类是库存的核心，用于创建和管理库存。

#### 属性

- `id`: 库存的唯一标识符。
- `title`: 库存的标题。
- `type`: 库存的类型。
- `rows`: 库存的行数。
- `columns`: 库存的列数。
- `closeable`: 库存是否可以被关闭。
- `provider`: 提供库存内容的 `InventoryProvider`。
- `parent`: 父库存，用于分页等操作。
- `listeners`: 库存事件监听器列表。
- `manager`: 管理库存的 `InventoryManager`。

#### 方法

- `open(Player player)`: 打开库存。
- `open(Player player, int page)`: 打开指定页面的库存。
- `close(Player player)`: 关闭库存。
- `getId()`: 获取库存的 ID。
- `getTitle()`: 获取库存的标题。
- `getType()`: 获取库存的类型。
- `getRows()`: 获取库存的行数。
- `getColumns()`: 获取库存的列数。
- `isCloseable()`: 检查库存是否可以关闭。
- `setCloseable(boolean closeable)`: 设置库存是否可以关闭。
- `getProvider()`: 获取库存的提供者。
- `getParent()`: 获取父库存。
- `getManager()`: 获取库存的管理者。
- `getListeners()`: 获取库存的事件监听器列表。

#### 构建器

`SmartInventory` 提供了一个构建器 `Builder` 用于构建 `SmartInventory` 实例。

### InventoryProvider 接口

`InventoryProvider` 接口定义了库存内容的初始化和更新逻辑。

#### 方法

- `init(Player player, InventoryContents contents)`: 初始化库存内容。
- `update(Player player, InventoryContents contents)`: 更新库存内容（可选实现）。

### InventoryContents 类

`InventoryContents` 类用于管理库存的具体内容。

## 示例代码

以下是 `SmartInventory` 的使用示例：

```java
SmartInventory INVENTORY = SmartInventory.builder()
    .id("myInventory")
    .provider(new SimpleInventory())
    .size(3, 9)
    .title(ChatColor.BLUE + "My Awesome Inventory!")
    .build();
```

### SimpleInventory 实现

`SimpleInventory` 是 `InventoryProvider` 接口的一个实现示例：

```java
public class SimpleInventory implements InventoryProvider {
    private final Random random = new Random();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        contents.set(1, 1, ClickableItem.of(new ItemStack(Material.CARROT_ITEM),
                e -> player.sendMessage(ChatColor.GOLD + "You clicked on a potato.")));
        contents.set(1, 7, ClickableItem.of(new ItemStack(Material.BARRIER),
                e -> player.closeInventory()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        int state = contents.property("state", 0);
        contents.setProperty("state", state + 1);

        if(state % 5 != 0)
            return;

        short durability = (short) random.nextInt(15);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, durability);
        contents.fillBorders(ClickableItem.empty(glass));
    }
}
```

## 注意事项

- 确保在使用 `SmartInventory` 之前已经正确设置了 `InventoryManager`。
- 在实现 `InventoryProvider` 接口时，应该根据需要重写 `init` 和 `update` 方法。
- 使用 `Builder` 构建 `SmartInventory` 实例时，必须设置 `provider`。

## 结语

`SmartInventory` 提供了一个强大且灵活的方式来创建和管理 Minecraft 服务器中的自定义库存界面，使得开发者可以轻松地实现复杂的用户交互逻辑。



