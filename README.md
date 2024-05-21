# CyanCommand 类文档

## 概览

`CyanCommand` 类是一个抽象类，它为在 SpigotMC 服务器插件中创建和注册命令提供了一个框架。它使用注解来定义主命令和子命令，并提供了执行这些命令和生成帮助信息的方法。

## 方法

### `mainExecute(CommandSender sender, String commandLabel, String[] args)`

这是一个抽象方法，任何扩展 `CyanCommand` 的类都必须实现它。它定义了主命令的行为。

### `help(CommandSender sender)`

这个方法生成命令的帮助信息。它使用 `RegisterCommand` 和 `RegisterSubCommand` 注解来收集关于命令及其子命令的信息。

### `subCommand(CommandSender sender, String commandLabel, String[] args)`

这个方法用于定义子命令的行为。它可以在子类中被重写以提供特定的功能。

### `tab(CommandSender sender, String[] args)`

这个方法用于提供命令的标签补全。它返回子命令名称的列表。

### `register()`

这个方法将命令注册到 SpigotMC 服务器。它创建一个新的 `Command` 实例，并将其添加到服务器的命令映射中。

## 注解

### `RegisterCommand`

这个注解用于标记一个类为命令。它有两个属性：`name`，定义命令的名称，以及 `permission`，定义执行命令所需的权限。

### `RegisterSubCommand`

这个注解用于标记一个方法为子命令。它有三个属性：`subName`，定义子命令的名称，`howToUse`，提供如何使用子命令的描述，以及 `permission`，定义执行子命令所需的权限。

## 示例用法

```java
@RegisterCommand(name = "mycommand", permission = "myplugin.mycommand")
public class MyCommand extends CyanCommand {

    @Override
    public void mainExecute(CommandSender sender, String commandLabel, String[] args) {
        // 主命令功能在这里
    }

    @RegisterSubCommand(subName = "mysubcommand", howToUse = "像这样使用", permission = "myplugin.mycommand.mysubcommand")
    public void subCommand(CommandSender sender, String commandLabel, String[] args) {
        // 子命令功能在这里
    }
}
```

在这个示例中，创建了一个名为 `mycommand` 的新命令，以及一个名为 `mysubcommand` 的子命令。`mainExecute` 方法被重写以提供主命令的功能，`subCommand` 方法被重写以提供子命令的功能。



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