package cn.cyanbukkit.example.cyanlib.server;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.lang.reflect.InvocationTargetException;

/**
 *  服务器包发送
 */
public class ProtocolSupport {

    private static com.comphenix.protocol.ProtocolManager getProtocolManager() {
        return ProtocolLibrary.getProtocolManager();
    }

    public static void sendServerPacket(Player player, PacketContainer packet) {
        try {
            getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet.", e);
        }
    }

    public static void sendServerPacketAll(Collection<Player> players, PacketContainer packet) {
        for (Player player : players) {
            sendServerPacket(player, packet);
        }
    }
}