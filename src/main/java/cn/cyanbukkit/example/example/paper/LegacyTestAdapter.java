package cn.cyanbukkit.example.example.paper;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Body;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Title;
import org.bukkit.entity.Player;
/**
 * 传统版本计分板适配器示例实现类
 * <p>
 * 适用于Minecraft 1.8.8至1.17版本服务器，使用传统字符串格式的计分板实现
 * <p>
 * 注意：此类使用传统的颜色代码(&)格式而非Kyori Adventure组件
 */
public final class LegacyTestAdapter implements BoardAdapter {
    /**
     * 获取计分板标题
     * <p>
     * 使用传统字符串格式构建计分板标题，支持Minecraft颜色代码(&)
     *
     * @param player 目标玩家对象
     * @return 返回使用传统格式构建的标题对象
     * @see Title#legacy()
     */
    @Override
    public  Title<?> title(Player player) {
        return Title.legacy().ofText("test");
    }
    /**
     * 获取计分板内容主体
     * <p>
     * 使用传统字符串格式构建多行计分板内容，每行字符串支持：
     * <ul>
     *   <li>颜色代码(&)</li>
     *   <li>样式代码(&l-粗体, &o-斜体等)</li>
     *   <li>空字符串("")表示空行</li>
     * </ul>
     *
     * @param player 目标玩家对象
     * @return 返回使用传统格式构建的内容主体对象
     * @see Body#legacy(String...)
     */
    @Override
    public  Body<?> getBody(Player player) {
        return Body.legacy(
                "&7&l+------------------------+",
                "",
                "&8> &eThis is mBoard,say Hello",
                "",
                "&7&l+------------------------+");
    }

}
