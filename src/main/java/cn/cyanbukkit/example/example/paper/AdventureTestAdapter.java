package cn.cyanbukkit.example.example.paper;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Body;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Title;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * 基于Kyori Adventure的计分板适配器示例实现类
 * <p>
 * 适用于Minecraft 1.18+版本，使用Kyori Adventure API处理文本组件
 */
public final class AdventureTestAdapter implements BoardAdapter {

    /**
     * 获取计分板标题
     * <p>
     * 使用Kyori Adventure的Component文本组件构建计分板标题
     *
     * @param player 目标玩家对象
     * @return 返回使用Adventure API构建的标题对象
     * @see Title#adventure()
     */
    @Override
    public  Title<?> title(Player player) {
        return Title.adventure().ofComponent(Component.text("test"));
    }
    /**
     * 获取计分板内容主体
     * <p>
     * 使用Kyori Adventure的Component文本组件构建多行计分板内容，
     * 支持颜色代码(&)和样式
     *
     * @param player 目标玩家对象
     * @return 返回使用Adventure API构建的内容主体对象
     * @see Body#adventure(Component...)
     */
    @Override
    public  Body<?> getBody(Player player) {
        return Body.adventure(
                Component.text("&7&l+------------------------+"),
                Component.empty(),
                Component.text("&8> &eThis is mBoard,say Hello"),
                Component.empty(),
                Component.text("&7&l+------------------------+"));
    }

}
