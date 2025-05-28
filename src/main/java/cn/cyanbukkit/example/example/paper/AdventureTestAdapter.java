package cn.cyanbukkit.example.example.paper;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Body;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Title;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

//for minecraft 1.18+, uses KyoriAdventure
public final class AdventureTestAdapter implements BoardAdapter {

    @Override
    public  Title<?> title(Player player) {
        return Title.adventure().ofComponent(Component.text("test"));
    }

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
