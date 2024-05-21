package cn.cyanbukkit.example.cyanlib.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * 玩家动作栏  发包通用版
 */
public class PlayerActionbar {

    public static void sendActionbar(Player player, String text) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CHAT);
        packet.getChatTypes().writeSafely(0, EnumWrappers.ChatType.GAME_INFO);
        packet.getBytes().writeSafely(0, (byte) 2);
        packet.getIntegers().writeSafely(0, 2);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}