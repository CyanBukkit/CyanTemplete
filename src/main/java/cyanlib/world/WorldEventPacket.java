package cyanlib.world;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WorldEventPacket {

    public static void sendWorldEventPacket(boolean isSplash, Location location, Color color, int colorParam1, int colorParam2, Integer colorParam3, List<Player> players) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.WORLD_EVENT);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0, isSplash ? 2007 : 2002);
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        int colorValue = color.asRGB();
        packet.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
        packet.getIntegers().write(1, colorValue);
        for (Player player : players) {
            World playerWorld = player.getWorld();
            if (playerWorld.equals(location.getWorld())) {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                } catch (InvocationTargetException ignored) {
                }
            }
        }
    }
}