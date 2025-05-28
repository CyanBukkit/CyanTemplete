package cn.cyanbukkit.example.cyanlib.launcher;

import cn.cyanbukkit.example.example.MyCommand;
import cn.cyanbukkit.example.cyanlib.inventory.SmartInvsPlugin;
import cn.cyanbukkit.example.cyanlib.loader.KotlinBootstrap;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.BoardManager;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 嵌套框架
 */

public class CyanPluginLauncher extends JavaPlugin {

    public static CyanPluginLauncher cyanPlugin;
    public static List<Command> commands = new ArrayList<>();
    public ScoreboardLibrary scoreboardLibrary;

    public CyanPluginLauncher() {
        cyanPlugin = this;
        KotlinBootstrap.init();
        AdventureAPIInitialization.init();
        MySQLInitialization.getJavaVersionToDownloadHikaricp();
        // SPIGOT 计分板
        String scoreboardLibraryVersion = "2.3.1";
        KotlinBootstrap.loadDepend("net.megavex", "scoreboard-library-api", scoreboardLibraryVersion);
        KotlinBootstrap.loadDepend("net.megavex", "scoreboard-library-extra-kotlin", scoreboardLibraryVersion);
    }


    @Override
    public void onLoad() {
    }


    @Override
    public void onEnable() {
        // 背包控制器
        new SmartInvsPlugin().onEnable(this); // 写GUI必要的加载入口
        new MyCommand("test").register();
        // Paper计分板控制器
        BoardManager.load(this);
        BoardManager.getInstance().setUpdateInterval(4L); //default is 2L
        BoardManager.getInstance().startBoardUpdaters();
        //paper 计分板示例
        //getServer().getPluginManager().registerEvents(new ScoreBoardTimer(), this);
        // spigot
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(this);
        } catch (NoPacketAdapterAvailableException e) {
            // If no packet adapter was found, you can fallback to the no-op implementation:
            scoreboardLibrary = new NoopScoreboardLibrary();
            getLogger().warning("No scoreboard packet adapter available!");
        }


        /*发给客户：启动时输入CYANBUKKIT进行解锁 验收插件后会删掉这个围栏的哦！
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("CyanBukkit验收插件前围栏请按照技术的指引进行解锁");
            input = scanner.nextLine();
        } while (!"CYANBUKKIT".equalsIgnoreCase(input));*/

    }

    /**
     * 指令列表
     */
    public void pluginCommand() {
        Command thisPluginAllCommand = new Command("cyanbukkit") {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                sender.sendMessage("§8该插件有插件");
                commands.forEach(command -> {
                    sender.sendMessage("§8" + command.getName() + "§8: " + command.getDescription());
                });
                return true;
            }
        };
        Class<?> pluginManagerClass = getServer().getPluginManager().getClass();
        try {
            Field field = pluginManagerClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) field.get(getServer().getPluginManager());
            commandMap.register(getName(), thisPluginAllCommand);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }


}