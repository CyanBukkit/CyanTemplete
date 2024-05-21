package cn.cyanbukkit.example.cyanlib.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

/**
 * 玩家保持活跃  发包通用版
 */

public class PlayerKeepAlive {

    private static ProtocolManager plm;
    private static Random random = new Random();

    /**
     * 发送保持活跃包（未证实）
     * @param  p 玩家组
     */
    public static void sendKeepAlive(List<Player> p) {
        for (Player sp : p) {
            try {
                PacketContainer packet = get().createPacket(PacketType.Play.Client.KEEP_ALIVE);
                packet.getIntegers().write(0, random.nextInt());
                get().sendServerPacket(sp, packet);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException("Cannot send packet.", ex);
            }
        }
    }


    /**
     * 获取协议管理器
     * @return 协议管理器
     */
    public static ProtocolManager get() {
        if (plm == null) {
            plm = ProtocolLibrary.getProtocolManager();
        }
        return plm;
    }
}