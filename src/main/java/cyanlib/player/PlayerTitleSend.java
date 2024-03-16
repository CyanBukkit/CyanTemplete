package cyanlib.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PlayerTitleSend {

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (ProtocolLibrary.getProtocolManager().getMinecraftVersion().compareTo(MinecraftVersion.CAVES_CLIFFS_1) >= 0) {
            PacketContainer packetTitle = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SET_TITLE_TEXT);
            packetTitle.getChatComponents().write(0, WrappedChatComponent.fromText(title));

            PacketContainer packetSubtitle = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SET_SUBTITLE_TEXT);
            packetSubtitle.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));

            PacketContainer packetTitleTime = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SET_TITLES_ANIMATION);
            packetTitleTime.getIntegers().write(0, fadeIn);
            packetTitleTime.getIntegers().write(1, stay);
            packetTitleTime.getIntegers().write(2, fadeOut);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetTitle);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetSubtitle);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetTitleTime);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            PacketContainer packetTitle = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
            packetTitle.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
            packetTitle.getChatComponents().write(0, WrappedChatComponent.fromText(title));

            PacketContainer packetSubtitle = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
            packetSubtitle.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
            packetSubtitle.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));

            PacketContainer packetTitleTime = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TITLE);
            packetTitleTime.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
            packetTitleTime.getIntegers().write(0, fadeIn);
            packetTitleTime.getIntegers().write(1, stay);
            packetTitleTime.getIntegers().write(2, fadeOut);

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetTitle);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetSubtitle);
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetTitleTime);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}