//package cn.cyanbukkit.example.command;
//
//import cn.cyanbukkit.example.cyanlib.scoreboard.animation.HighlightingAnimation;
//import cn.cyanbukkit.example.cyanlib.scoreboard.animation.core.Animation;
//import cn.cyanbukkit.example.cyanlib.scoreboard.base.BoardAdapter;
//import cn.cyanbukkit.example.cyanlib.scoreboard.entity.Body;
//import net.md_5.bungee.api.ChatColor;
//import org.bukkit.entity.Player;
//import cn.cyanbukkit.example.cyanlib.scoreboard.entity.Title;
//
//public class TestAdapter implements BoardAdapter {
//
//	private final Animation<String> titleAnimation =
//			new Animation<>("&4mBoard &7| &cA lib" /*message*/,
//					"&4mBoard ",
//					"&4mBoard &fT",
//					"&4mBoard &fTh",
//					"&4mBoard &fThe",
//					"&4mBoard &fThe &7#",
//					"&4mBoard &fThe &7#1 ",
//					"&4mBoard &fThe &7#1 &cL",
//					"&4mBoard &fThe &7#1 &cLi",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLib",
//					"&4mBoard &fThe &7#1 &cLi",
//					"&4mBoard &fThe &7#1 &cL",
//					"&4mBoard &fThe &7#1 ",
//					"&4mBoard &fThe &7#",
//					"&4mBoard &fThe",
//					"&4mBoard &fTh",
//					"&4mBoard &fT",
//					"&4mBoard ",
//					"&4mBoard ");
//
//
//	/**
//	 * Fetches the title to be represented
//	 * on the board that has this adapter instance;
//	 *
//	 * @param player the player who will view the title
//	 * @return the title of the scoreboard
//	 */
//
//	@Override
//	public  Title title(Player player) {
//		return Title.builder()
//				.withText("&4mBoard &7| &cA lib")
//				.withAnimation(titleAnimation)
//				.build();
//	}
//
//	/**
//	 * Gets the body to be represented
//	 * as the body of the scoreboard
//	 * which will occupy this adapter as it's
//	 * model or template to take data from.
//	 *
//	 * @param player the player who will view the lines
//	 * @return the body of the scoreboard
//	 */
//	@Override
//	public Body getBody(Player player) {
//		Body body = Body.("&7&l&m+----------------+");
//		body.addNewLine(HighlightingAnimation.of("Test Highlighted", ChatColor.GOLD, ChatColor.YELLOW));
//		body.addNewLine("&7&l&m+-----------------+");
//		return body;
//	}
//
//}
