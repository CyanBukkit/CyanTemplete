package cyanlib.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class PlayerKeepAlive {

    private static ProtocolManager plm;
    private static Random random = new Random();

    public static void sendKeepAlive(List<Player> p) {
        for (Player sp : p) {
            try {
                PacketContainer packet = get().createPacket(PacketType.Play.Client.KEEP_ALIVE);
                packet.getIntegers().write(0, random.nextInt());
                get().sendServerPacket(sp, packet);
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ProtocolManager get() {
        if (plm == null) {
            plm = ProtocolLibrary.getProtocolManager();
        }
        return plm;
    }
}