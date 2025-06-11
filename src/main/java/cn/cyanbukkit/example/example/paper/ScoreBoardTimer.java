package cn.cyanbukkit.example.example.paper;


import cn.cyanbukkit.example.cyanlib.scoreboard.paper.BoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
/**
 * 计分板生命周期管理监听器
 * <p>
 * 负责处理玩家加入/退出时的计分板创建和销毁逻辑
 * <p>
 * 使用示例：
 * <pre>
 * // 注册监听器
 * Bukkit.getPluginManager().registerEvents(new ScoreBoardTimer(), plugin);
 * </pre>
 */
public class ScoreBoardTimer implements Listener {
    /**
     * 玩家加入事件处理
     * <p>
     * 当玩家加入服务器时，为其创建并设置计分板
     * <p>
     * 使用{@link AdventureTestAdapter}作为计分板内容适配器
     *
     * @param e 玩家加入事件
     * @see BoardManager#setupNewBoard(org.bukkit.entity.Player, cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter)
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        BoardManager.getInstance().setupNewBoard(e.getPlayer(), new AdventureTestAdapter());
    }
    /**
     * 玩家退出事件处理
     * <p>
     * 当玩家退出服务器时，移除其计分板以释放资源
     *
     * @param e 玩家退出事件
     * @see BoardManager#removeBoard(org.bukkit.entity.Player)
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        BoardManager.getInstance().removeBoard(e.getPlayer());
    }

}
