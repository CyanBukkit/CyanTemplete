package cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.animation.core.Animation;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.base.BoardAdapter;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.util.Optional;

/**
 *
 * A simple interface to represent the data required
 * to be obtained about the title of a scoreboard
 *
 * @see BoardAdapter
 * @since 1.0
 * @author Mqzen (aka Mqzn)
 *
 */
public interface Title<T> {

	/**
	 *
	 * A simple method to provide the text of the title
	 * that will be viewed to a certain player
	 *
	 * @return the text/content of this title
	 */
	Optional<T> get();

	/**
	 * Sets a {@link Animation} for the title
	 * @param animation the animation
	 * @return the title animation
	 * @param <TITLE> the type of title instance to return
	 */
	<TITLE extends Title<T>> TITLE withAnimation(Animation<T> animation);

	/**
	 * A method to provide the animation of the title
	 * By default, a title has no animation unless
	 * you set its animation by overriding this method
	 * and returning your animation object
	 * e.g: Optional.ofNullable(yourAnimationInstance)
	 *
	 * @return the Animation of the title if present
	 */
	default Optional<Animation<T>> loadAnimation() {
		return Optional.empty();
	}

	static TitleImplementation.LegacyTitle legacy() {
		return new TitleImplementation.LegacyTitle();
	}
	static TitleImplementation.AdventureTitle adventure() {
		return new TitleImplementation.AdventureTitle();
	}

	default boolean hasAnimation() {
		return loadAnimation().isPresent();
	}


	class TitleImplementation<T> implements Title<T>{
		 private T content;

		 public T getContent() {
			 return content;
		 }

		 public void setContent(T content) {
			 this.content = content;
		 }

		 private Animation<T> titleAnimation;

		 public Animation<T> getTitleAnimation() {
			 return titleAnimation;
		 }

		 public void setTitleAnimation(Animation<T> animation) {
			 this.titleAnimation = animation;
		 }


		public TitleImplementation() {

		 }

		 public TitleImplementation(T content) {
			 this.content = content;
		 }
		 @Override
		 public Optional<T> get() {
			 return Optional.of(content);
		 }

		 @Override
		 @SuppressWarnings("unchecked")
		 public <TITLE extends Title<T>> TITLE withAnimation(Animation<T> animation) {
			 setTitleAnimation(animation);
			 return (TITLE) this;
		 }

		 /**
		  * A method to provide the animation of the title
		  * By default, a title has no animation unless
		  * you set its animation by overriding this method
		  * and returning your animation object
		  * e.g: Optional.ofNullable(yourAnimationInstance)
		  *
		  * @return the Animation of the title if present
		  */
		 @Override
		 public Optional<Animation<T>> loadAnimation() {
			 return Optional.ofNullable(titleAnimation);
		 }

		 public static class LegacyTitle extends TitleImplementation<String> {

			 public LegacyTitle ofText(String content) {
				 super.setContent(ChatColor.translateAlternateColorCodes('&', content));
				 return this;
			 }
		 }
		 public static class AdventureTitle extends TitleImplementation<Component> {

			 public AdventureTitle ofComponent(Component content) {
				 super.setContent(content);
				 return this;
			 }
		 }
	 }



}
