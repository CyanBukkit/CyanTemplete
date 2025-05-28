package cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.impl;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.BoardManager;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardBase;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardUpdate;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Line;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.util.FastReflection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import static cn.cyanbukkit.example.cyanlib.scoreboard.paper.BoardManager.ADVENTURE_SUPPORT;


public class AdventureBoard extends BoardBase<Component> {

    private static final MethodHandle COMPONENT_METHOD;

    public MethodHandle getCOMPONENT_METHOD() {
        return COMPONENT_METHOD;
    }

    private static final Object EMPTY_COMPONENT;

    public Object getEMPTY_COMPONENT() {
        return EMPTY_COMPONENT;
    }

    static {

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            if (ADVENTURE_SUPPORT) {
                Class<?> paperAdventure = Class.forName("io.papermc.paper.adventure.PaperAdventure");
                Method method = paperAdventure.getDeclaredMethod("asVanilla", Component.class);
                COMPONENT_METHOD = lookup.unreflect(method);
                EMPTY_COMPONENT = COMPONENT_METHOD.invoke(Component.empty());
            } else {
                Class<?> craftChatMessageClass = FastReflection.obcClass("util.CraftChatMessage");
                COMPONENT_METHOD = lookup.unreflect(craftChatMessageClass.getMethod("fromString", String.class));
                EMPTY_COMPONENT = Array.get(COMPONENT_METHOD.invoke(""), 0);
            }
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }
    private final BoardAdapter adapter;

    public BoardAdapter getAdapter() {
        return adapter;
    }

    public AdventureBoard(Player player, BoardAdapter adapter) {
        super(player);
        this.adapter = adapter;

        if (!update()) {
            BoardManager.getInstance().getLogger().warning("Hey! Looks like you're using legacy text for your board instead of components," +
                    " legacy text has been automatically converted for now. It is better that you use kyori adventure for modern minecraft.");
        }
    }

    @Override
    public BoardUpdate getUpdate() {
        return adapter.getBoardUpdate();
    }

    @Override
    protected void sendLineChange(int score) throws Throwable {
        Component line = getLineByScore(score);

        sendTeamPacket(score, BoardBase.TeamMode.UPDATE, line, null);
    }

    @Override
    protected Object toMinecraftComponent(Component component) throws Throwable {
        if (component == null) {
            return EMPTY_COMPONENT;
        }

        // If the server isn't running adventure natively, we convert the component to legacy text
        // and then to a Minecraft chat component
        if (!ADVENTURE_SUPPORT) {
            String legacy = serializeLine(component);

            return Array.get(COMPONENT_METHOD.invoke(legacy), 0);
        }

        return COMPONENT_METHOD.invoke(component);
    }

    @Override
    protected String serializeLine(Component value) {
       return LegacyComponentSerializer.legacySection().serialize(value);
    }

    @Override
    protected Component emptyLine() {
        return Component.empty();
    }

    @Override
    public boolean update() {
        try {
            updateTitle((Component) adapter.title(getPlayer()).get().orElseThrow(NullPointerException::new));
            for (Line<?> line : adapter.getBody(getPlayer()).getLines()) {
                updateLine(line.getIndex(), (Component) line.fetchContent());
            }
            return true;
        } catch (ClassCastException e) {
            for (Line<?> line : adapter.getBody(getPlayer()).getLines()) {
                updateLine(line.getIndex(), deserialize(line.fetchContent()));
            }
            updateTitle(deserialize(adapter.title(getPlayer()).get().orElseThrow(NullPointerException::new)));
            ;
            return false;
        }

    }

    private Component deserialize(Object o) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(o.toString());
    }

}
