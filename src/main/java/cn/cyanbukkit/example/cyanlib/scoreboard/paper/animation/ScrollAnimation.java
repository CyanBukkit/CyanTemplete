package cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation.core.Animation;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation.core.ChangesSequence;
import org.bukkit.ChatColor;

public final class ScrollAnimation extends Animation<String> {
	private final Scroller scroller;

	private ScrollAnimation(String original, int width, int spaceBetween) {
		super(original, ChangesSequence.of());
		this.scroller = Scroller.of(ChatColor.translateAlternateColorCodes('&', original), width, spaceBetween);
	}

	public static ScrollAnimation of(String msg, int width, int spaceBetween) {
		return new ScrollAnimation(msg, width, spaceBetween);
	}

	@Override
	public String fetchNextChange() {
		return scroller.next();
	}

	@Override
	public String fetchPreviousChange() {
		return scroller.next();
	}

}
