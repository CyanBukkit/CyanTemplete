package cn.cyanbukkit.example.example.spigot;

import com.google.common.base.Supplier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.megavex.scoreboardlibrary.api.objective.ObjectiveDisplaySlot;
import net.megavex.scoreboardlibrary.api.objective.ObjectiveManager;
import net.megavex.scoreboardlibrary.api.objective.ScoreboardObjective;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.LineDrawable;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.CollectionSidebarAnimation;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.SidebarAnimation;
import net.megavex.scoreboardlibrary.api.team.ScoreboardTeam;
import net.megavex.scoreboardlibrary.api.team.TeamDisplay;
import net.megavex.scoreboardlibrary.api.team.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

import static cn.cyanbukkit.example.cyanlib.launcher.CyanPluginLauncher.cyanPlugin;

public class SBSBSBSB {

    Sidebar sidebar1 = cyanPlugin.scoreboardLibrary.createSidebar();

    //组件侧边栏是对低级侧边栏的抽象。它们允许您以简洁的方式设计侧边栏。以下是创建具有静态行的 SidebarComponent 的方法：
    SidebarComponent staticLine = SidebarComponent.staticLine(Component.text("A static line"));

    //你可以使用 SidebarComponent.builder（） 将多个 SidebarComponent 链接在一起：
    SidebarComponent lines = SidebarComponent.builder()
            .addComponent(SidebarComponent.staticLine(Component.text("A static line")))
            .addStaticLine(Component.text("Another static line")) // Shorthand for line above
            .build();

    public void ex1(Player p) {

        sidebar1.title(Component.text("Sidebar Title"));
        sidebar1.line(0, Component.empty());
        sidebar1.line(1, Component.text("Line 1"));
        sidebar1.line(2, Component.text("Line 2"));
        sidebar1.line(2, Component.empty());
        sidebar1.line(3, Component.text("LanternMC"));

        sidebar1.addPlayer(p); // Add the player to the sidebar
        // Don't forget to call sidebar.close() once you don't need it anymore!
    }


    public void ex2(Player p) {
        ComponentSidebarLayout layout = new ComponentSidebarLayout(
                SidebarComponent.staticLine(Component.text("Sidebar Title")),
                lines
        );

        Sidebar sidebar = cyanPlugin.scoreboardLibrary.createSidebar();
        // Apply the title & lines components to the Sidebar
        // Do this every time the title or any line needs to be updated
        layout.apply(sidebar);
    }


    public void ex3(Player p) {
        Component lineComponent = Component.text("Line that changes colors");
        Set<NamedTextColor> colors = NamedTextColor.NAMES.values();
        List<Component> frames = new ArrayList<>(colors.size());
        for (NamedTextColor color : colors) {
            frames.add(lineComponent.color(color));
        }

        SidebarAnimation<Component> animation = new CollectionSidebarAnimation<>(frames);
        // You can also implement SidebarAnimation yourself

        SidebarComponent line = SidebarComponent.animatedLine(animation);

        // Advance to the next frame of the animation
        animation.nextFrame();
    }

    public void ex4(Player player) {
        SidebarComponent firstPage = SidebarComponent.builder()
                .addStaticLine(Component.text("First page"))
                .addDynamicLine(() -> Component.text("Level: " + player.getLevel()))
                .build();

        SidebarComponent secondPage = SidebarComponent.builder()
                .addStaticLine(Component.text("Second page"))
                .addDynamicLine(() -> Component.text("Health: " + player.getHealth()))
                .build();

        List<SidebarComponent> pages = Arrays.asList(firstPage, secondPage);
        SidebarAnimation<SidebarComponent> pageAnimation = new CollectionSidebarAnimation<>(pages);
        SidebarComponent paginatedComponent = SidebarComponent.animatedComponent(pageAnimation);
    }

    public class KeyValueSidebarComponent implements SidebarComponent {
        private final Component key;
        private final Supplier<Component> valueSupplier;

        public KeyValueSidebarComponent(@NotNull Component key, @NotNull Supplier<Component> valueSupplier) {
            this.key = key;
            this.valueSupplier = valueSupplier;
        }

        @Override
        public void draw(@NotNull LineDrawable drawable) {
            Component value = valueSupplier.get();
            net.kyori.adventure.text.@NotNull TextComponent line = Component.text()
                    .append(key)
                    .append(Component.text(": "))
                    .append(value.colorIfAbsent(NamedTextColor.AQUA))
                    .build();
            drawable.drawLine(line);
        }
    }


//    public class ExampleComponentSidebar {
//        private final Sidebar sidebar;
//        private final ComponentSidebarLayout componentSidebar;
//        private final SidebarAnimation<Component> titleAnimation;
//
//        public ExampleComponentSidebar(@NotNull Plugin plugin, @NotNull Sidebar sidebar) {
//            this.sidebar = sidebar;
//
//            this.titleAnimation = createGradientAnimation(Component.text("Sidebar Example", Style.style(TextDecoration.BOLD)));
//            @NotNull SidebarComponent title = SidebarComponent.animatedLine(titleAnimation);
//
//            SimpleDateFormat dtf = new SimpleDateFormat("HH:mm:ss");
//
//            // Custom SidebarComponent, see below for how an implementation might look like
//            SidebarComponent onlinePlayers = new KeyValueSidebarComponent(
//                    Component.text("Online players"),
//                    () -> Component.text(plugin.getServer().getOnlinePlayers().size())
//            );
//
//            SidebarComponent lines = SidebarComponent.builder()
//                    .addDynamicLine(() -> {
//                        String time = dtf.format(new Date());
//                        return Component.text(time, NamedTextColor.GRAY);
//                    })
//                    .addBlankLine()
//                    .addStaticLine(Component.text("A static line"))
//                    .addComponent(onlinePlayers)
//                    .addBlankLine()
//                    .addStaticLine(Component.text("epicserver.net", NamedTextColor.AQUA))
//                    .build();
//
//
//            this.componentSidebar = new ComponentSidebarLayout(title, lines);
//        }
//
//        // Called every tick
//        public void tick() {
//            // Advance title animation to the next frame
//            titleAnimation.nextFrame();
//
//            // Update sidebar title & lines
//            componentSidebar.apply(sidebar);
//        }

//        private @NotNull SidebarAnimation<Component> createGradientAnimation(@NotNull Component text) {
//            float step = 1f / 8f;
//
//            TagResolver.Single textPlaceholder = Placeholder.component("text", text);
//            List<Component> frames = new ArrayList<>((int) (2f / step));
//
//            float phase = -1f;
//            while (phase < 1) {
//                frames.add(MiniMessage.miniMessage().deserialize("<gradient:yellow:gold:" + phase + "><text>", textPlaceholder));
//                phase += step;
//            }
//
//            return new CollectionSidebarAnimation<>(frames);
//        }
//    }


    public void team(Player player) {
        TeamManager teamManager = cyanPlugin.scoreboardLibrary.createTeamManager();
        ScoreboardTeam team = teamManager.createIfAbsent("team_name");

        // A TeamDisplay holds all the display properties that a team can have (prefix, suffix etc.).
        // You can apply different TeamDisplays for each player so different players can see
        // different properties on a single ScoreboardTeam. However if you don't need that you can
        // use the default TeamDisplay that is created in every ScoreboardTeam.
        TeamDisplay teamDisplay = team.defaultDisplay();
        teamDisplay.displayName(Component.text("Team Display Name"));
        teamDisplay.prefix(Component.text("[Prefix] "));
        teamDisplay.suffix(Component.text(" [Suffix]"));
        teamDisplay.playerColor(NamedTextColor.RED);
        teamDisplay.addEntry(player.getName());
        teamManager.addPlayer(player); // Player will be added to the default TeamDisplay of each ScoreboardTeam
        // Create a new TeamDisplay like this:
        TeamDisplay newTeamDisplay = team.createDisplay();
        newTeamDisplay.displayName(Component.text("Other Team Display Name"));
        // Change the TeamDisplay a player sees like this:
        team.display(player, newTeamDisplay);
        // Don't forget to call teamManager.close() once you don't need it anymore!
    }


    public void ex5(Player player) {
        ObjectiveManager objectiveManager = cyanPlugin.scoreboardLibrary.createObjectiveManager();
        ScoreboardObjective objective = objectiveManager.create("coolobjective");
        objective.value(Component.text("Display name"));
        objective.score(player.getName(), 69420);
        objectiveManager.display(ObjectiveDisplaySlot.belowName(), objective);

        objectiveManager.addPlayer(player); // Make a player see the objectives
        // Don't forget to call objectiveManager.close() once you don't need it anymore!
    }

}
