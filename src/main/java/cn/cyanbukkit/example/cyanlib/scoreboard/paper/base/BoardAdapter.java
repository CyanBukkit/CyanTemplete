package cn.cyanbukkit.example.cyanlib.scoreboard.paper.base;

import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Body;
import cn.cyanbukkit.example.cyanlib.scoreboard.paper.entity.Title;
import org.bukkit.entity.Player;

public interface BoardAdapter {


	/**
	 * Fetches the title to be represented
	 * on the board that has this adapter instance;
	 *
	 * @param player the player who will view the title
	 * @return the title of the scoreboard
	 */
	Title<?> title(Player player);

	/**
	 * Gets the body to be represented
	 * as the body of the scoreboard
	 * which will occupy this adapter as it's
	 * model or template to take data from.
	 *
	 * @param player the player who will view the lines
	 * @return the body of the scoreboard
	 */
	Body<?> getBody(Player player);

	/**
	 * Returns an update action if
	 * the board has any type of animations
	 * this is recommended to implement and return
	 * your own implementation.However, the best implementation recommended is this:
	 * <p>
	 *   return (board) -> {
	 * 		board.updateTitle();
	 * 		board.updateBody();
	 *   };
	 * </p>
	 *
	 * @return the actions to be executed as an update to the board
	 */
	default BoardUpdate getBoardUpdate() {
		return BoardBase::update;
	}

}
