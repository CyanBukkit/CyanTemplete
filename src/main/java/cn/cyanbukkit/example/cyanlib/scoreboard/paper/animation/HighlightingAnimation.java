package cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation;


import cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation.core.Animation;
import net.md_5.bungee.api.ChatColor;

public final class HighlightingAnimation extends Animation<String> {

	private final  HighLighter highLighter;
	private int position=0;

	private HighlightingAnimation(String message,
	                              String primaryColor,
	                              String secondaryColor) {
		super(message);
		this.highLighter = HighLighter.of(message, primaryColor, secondaryColor);
	}

	private HighlightingAnimation(String message,
	                              ChatColor primaryColor,
	                              ChatColor secondaryColor) {
		super(message);
		this.highLighter = HighLighter.of(message, primaryColor, secondaryColor);
	}

	public static HighlightingAnimation of(String message,
	                                       ChatColor primaryColor,
	                                       ChatColor secondaryColor) {
		return new HighlightingAnimation(message, primaryColor, secondaryColor);
	}

	public static HighlightingAnimation of(String message,
	                                       String primaryColor,
	                                       String secondaryColor) {
		return new HighlightingAnimation(message, primaryColor, secondaryColor);
	}

	@Override
	public String fetchNextChange() {
		return this.highLighter.nextResult();
	}

	@Override
	public String fetchPreviousChange() {
		if(position < 0) {
			position = 0;
		}
		String prev = highLighter.getHighLighted(position);
		position--;
		return prev;
	}

}
