package cn.cyanbukkit.example.command;

import cn.cyanbukkit.example.cyanlib.command.CyanCommand;
import cn.cyanbukkit.example.cyanlib.command.RegisterCommand;
import cn.cyanbukkit.example.cyanlib.command.RegisterSubCommand;
import org.bukkit.command.CommandSender;

@RegisterCommand(name = "CyanBukkit", permission = "CyanBukkit.dev")
public class MyCommand extends CyanCommand {


    @Override
    public void mainExecute(CommandSender sender, String commandLabel, String[] args) {

    }


    @RegisterSubCommand(subName = "test")
    public void subCommand(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage("§8这是一个子指令");
    }



}
