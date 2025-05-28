package cn.cyanbukkit.example.example.paper;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Body;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Title;
import org.bukkit.entity.Player;

//This is mainly for legacy minecraft servers (1.8.8 to 1.17)
public final class LegacyTestAdapter implements BoardAdapter {

    @Override
    public  Title<?> title(Player player) {
        return Title.legacy().ofText("test");
    }

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
