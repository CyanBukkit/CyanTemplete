package cn.cyanbukkit.example.cyanlib.command;

import cn.cyanbukkit.example.cyanlib.launcher.CyanPluginLauncher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.cyanbukkit.example.cyanlib.launcher.CyanPluginLauncher.cyanPlugin;

/**
 * 全新执行注册核心类
 */
public abstract class CyanCommand {
    public String commandText;
    public boolean needPermission;
    public String permission;
    public List<String> aliases;
    public String description;

    /**
     * 快捷注册指令无权限限制
     * @param cmd
     */
    public CyanCommand(String cmd) {
        this.commandText = cmd;
        needPermission = false;
    }

    /**
     * 快捷注册指令有权限限制
     * @param cmd
     * @param mainPermission
     */
    public CyanCommand(String cmd, String mainPermission) {
        this.commandText = cmd;
        this.permission = mainPermission;
        needPermission = true;
    }

    /**
     * 快捷注册指令有权限限制和描述
     * @param cmd
     * @param mainPermission
     * @param description
     */
    public CyanCommand(String cmd, String mainPermission, String description) {
        this.commandText = cmd;
        this.permission = mainPermission;
        this.description = description;
        needPermission = true;
    }

    /**
     * 快捷注册指令有权限限制和描述和别名
     * @param cmd
     * @param mainPermission
     * @param description
     * @param aliases
     */
    public CyanCommand(String cmd, String mainPermission, String description, String... aliases) {
        this.commandText = cmd;
        this.permission = mainPermission;
        this.description = description;
        this.aliases = Arrays.asList(aliases);
        needPermission = true;
    }

    /**
     * 主指令 不用请留空! 子指令走下来会执行这个的 不用请留空
     *
     * @param commandLabel 指令
     * @param args         参数        执行总指令的时候会返回数组 该方法会先跑然后在找sub 去跑sub
     */
    public abstract void mainExecute(CommandSender sender, String commandLabel, String[] args);

    /**
     * 预设帮助
     *
     * @return 返回帮助信息
     */
    public String help(CommandSender sender) {
        String helpMessage = "      §b/" + commandText + "§f| §9帮助信息\n";
        StringBuilder stringBuilder = new StringBuilder(helpMessage);
        stringBuilder.append("§8|-§b/").append(commandText).append(" help - 查看帮助\n");
        // 设计一个精美指令样式图
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            ArgumentCommand argumentCommand = method.getAnnotation(ArgumentCommand.class);
            if (argumentCommand == null) {
                continue;
            }
            stringBuilder.append("§8|-§b/").append(commandText).append(" ").append(argumentCommand.name())
                         .append(" §9").append(argumentCommand.description()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 注册子指令比如 /cyan give <player> <item> <count>
     * 你可以SubCommand 必须按照一下书写方法 可以创建好几个 方法参数必须保持一直
     *
     * @param args args返回的是  <player> <item> <count> 这个数组
     * @see ArgumentCommand
     * 使用 @RegisterSubCommand 注解注册并且重写这个方法 其中args
     */
    public void subCommand(CommandSender sender, String commandLabel, String[] args) {
    }

    public java.util.List<String> tab(CommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            ArgumentCommand argumentCommand = method.getAnnotation(ArgumentCommand.class);
            if (argumentCommand == null) {
                continue;
            }
            if (args.length == 1) {
                list.add(argumentCommand.name());
            }
        }
        return list;
    }

    /**
     * 注册成SpigotMC指令
     */
    public final void register() {
        // 查看谁继承了CyanCommand 并且获取
        if (commandText == null) {
            throw new NullPointerException("");
        }
        // 注册主指令
        Command command = new Command(
                commandText,
                description != null ? description : "",
                "",
                aliases != null ? aliases : new ArrayList<>()
        ) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                // 检查主指令权限
                if (needPermission && !sender.hasPermission(permission)) {
                    sender.sendMessage("§c你没有权限执行这个指令");
                    return true;
                }

                if (args.length > 0) {
                    // 优先执行子指令 help
                    if (args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage(help(sender));
                        return true;
                    }

                    // 获取当前类（CyanCommand）的所有声明的方法，包括公有、保护、默认（包）访问和私有方法，但不包括继承的方法
                    Method[] methods = CyanCommand.this.getClass().getDeclaredMethods();
                    for (Method method : methods) {
                        ArgumentCommand argumentCommand = method.getAnnotation(ArgumentCommand.class);
                        if (argumentCommand == null) {
                            continue;
                        }

                        // 检查子指令权限
                        if (args[0].equalsIgnoreCase(argumentCommand.name())) {
                            boolean hasPermission = argumentCommand.permission().isEmpty()
                                    || sender.hasPermission(argumentCommand.permission());
                            if (hasPermission) {
                                List<String> list = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
                                method.setAccessible(true);
                                try {
                                    method.invoke(CyanCommand.this, sender, commandLabel, list.toArray(new String[0]));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                sender.sendMessage("§c你没有权限执行这个指令");
                            }
                            return true;
                        }
                    }
                }

                // 如果没有匹配的子指令，执行主指令
                mainExecute(sender, commandLabel, args);
                return true;
            }

            @Override
            public java.util.List<String> tabComplete(org.bukkit.command.CommandSender sender, String alias, String[] args) {
                return tab(sender, args);
            }
        };
        CyanPluginLauncher.commands.add(command);
        Class<?> pluginManagerClass = cyanPlugin.getServer().getPluginManager().getClass();
        try {
            Field field = pluginManagerClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) field.get(cyanPlugin.getServer().getPluginManager());
            commandMap.register(cyanPlugin.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
