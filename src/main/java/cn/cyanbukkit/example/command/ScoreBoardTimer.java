package cn.cyanbukkit.example.command;

import cn.cyanbukkit.example.cyanlib.scorebroad.FixedBody;
import cn.cyanbukkit.example.cyanlib.scorebroad.Line;
import cn.cyanbukkit.example.cyanlib.scorebroad.SidebarBoard;
import cn.cyanbukkit.example.cyanlib.scorebroad.TextLine;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cn.cyanbukkit.example.cyanlib.launcher.CyanPluginLauncher.cyanPlugin;

/**
 * 记分板模板示例
 * 演示如何创建和更新玩家记分板
 */
public class ScoreBoardTimer implements Runnable {
    // 存储玩家和其对应的记分板
    public static final Map<String, SidebarBoard> scoreboards = new HashMap<>();
    
    // 当前处理的玩家
    private final Player playerName;

    public ScoreBoardTimer(Player playerName) {
        this.playerName = playerName;
    }

    /**
     * 核心记分板更新逻辑
     */
    @Override
    public void run() {
        SidebarBoard board = getOrCreateBoard(playerName);
        updateBoardContent(board);
    }

    /**
     * 获取或创建玩家记分板
     */
    private SidebarBoard getOrCreateBoard(Player playerName) {
        if (!scoreboards.containsKey(playerName.getName())) {
            SidebarBoard board = SidebarBoard.of(cyanPlugin, playerName);
            scoreboards.put(playerName.getName(), board);
            return board;
        }
        return scoreboards.get(playerName.getName());
    }

    /**
     * 更新记分板内容
     */
    private void updateBoardContent(SidebarBoard board) {
        board.setHead(TextLine.of("§e§l记分板标题")); // 可自定义标题
        board.setBody(getBody());
        board.update();
    }

    /**
     * 获取记分板内容体
     * 这是一个示例实现，实际应根据需求自定义
     */
    private FixedBody getBody() {
        ArrayList<Line> lines = new ArrayList<>();
        
        // 示例内容
        lines.add(TextLine.of("§f玩家: §a" + playerName));
        lines.add(TextLine.of(" "));
        
        // 添加自定义内容...
        
        return FixedBody.of(lines);
    }

    // 保留原有的时间格式化方法作为示例
    private String getFormattedTime(long millis) {
        int time = (int) millis / 1000;
        int min = time / 60;
        int sec = time % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
