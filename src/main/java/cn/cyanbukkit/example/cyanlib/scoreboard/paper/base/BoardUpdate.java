package cn.cyanbukkit.example.cyanlib.scoreboard.paper.base;

@FunctionalInterface
public interface BoardUpdate  {
	/**
	 * How you identify the actions that yet to be executed
	 * when the board is updated in a scheduled task
	 *
	 * @param board the board to be updated
	 */
	void update(BoardBase<?> board);

}
