package cn.cyanbukkit.example.example.paper;


import cn.cyanbukkit.example.cyanlib.scoreboard.paper.BoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreBoardTimer implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        BoardManager.getInstance().setupNewBoard(e.getPlayer(), new AdventureTestAdapter());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        BoardManager.getInstance().removeBoard(e.getPlayer());
    }

}
