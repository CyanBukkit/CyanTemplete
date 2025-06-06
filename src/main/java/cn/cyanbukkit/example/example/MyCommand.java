package cn.cyanbukkit.example.example;

import cn.cyanbukkit.example.cyanlib.command.CyanCommand;
import cn.cyanbukkit.example.cyanlib.command.ArgumentCommand;
import cn.cyanbukkit.example.cyanlib.inventory.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommand extends CyanCommand {


    public MyCommand(String cmd) {
        super(cmd);
    }

    @Override
    public void mainExecute(CommandSender sender, String commandLabel, String[] args) {}


    @ArgumentCommand(name = "test")
    public void subCommand(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("§8这是一个子指令");
    }


    @ArgumentCommand(name =  "opengui")
    public void gui(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SmartInventory INVENTORY = SmartInventory.builder()
                    .id("myInventory")
                    .provider(new SimpleInventory())
                    .size(3, 9)
                    .title(ChatColor.BLUE + "My Awesome Inventory!")
                    .build();
        }
    }



}
