# 命令系统开发教程（CyanCommand）

## 概述
- 通过抽象类 `CyanCommand` 与注解 `@ArgumentCommand` 实现主命令 + 子命令的统一注册、权限校验、帮助信息和 Tab 补全。
- 反射扫描当前类所有带注解的方法作为子命令，未匹配时回退到主命令 `mainExecute`。

## 快速开始
- 新建类并继承 `CyanCommand`，在构造方法中设置主命令文本、权限、描述、别名。
- 在插件启用时实例化并调用 `register()` 完成注册。

```java
public class MyCommand extends CyanCommand {
    public MyCommand() {
        super("mycmd", "myplugin.use", "示例命令", "mc", "cmd");
    }

    @Override
    public void mainExecute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("主命令执行: " + Arrays.toString(args));
    }

    @ArgumentCommand(name = "test", description = "测试子命令")
    public void test(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("这是一个子指令");
    }
}

// onEnable()
new MyCommand().register();
```

## 执行流程
- 主命令执行入口：`register()` 会将一个 `Command` 注册到 Bukkit；执行时先校验主权限（如设置）。
- help 子命令：当 `args[0] == "help"` 时调用 `help(sender)` 自动生成帮助文本。
- 子命令匹配：扫描 `@ArgumentCommand` 注解，按名称精确匹配执行，支持子权限；参数数组会去掉第一个子命令名。
- 未匹配时：调用 `mainExecute(sender, commandLabel, args)`。

## Tab 补全
- 默认实现会在第一个参数时返回所有子命令名称；可重写 `tab(sender, args)` 以提供更丰富补全。

## 常见问题
- 未设置 `commandText`：调用 `register()` 会抛出空指针，确保构造方法正确初始化。
- 子命令参数签名必须为 `(CommandSender, String, String[])`，否则反射调用失败。
- 注解名 `@ArgumentCommand` 才是正确用法，旧文档中的 `@RegisterSubCommand` 为笔误。

## 关联源码
- `src/main/java/cn/cyanbukkit/example/cyanlib/command/CyanCommand.java`
- `src/main/java/cn/cyanbukkit/example/cyanlib/command/ArgumentCommand.java`
- 示例：`src/main/java/cn/cyanbukkit/example/example/MyCommand.java`