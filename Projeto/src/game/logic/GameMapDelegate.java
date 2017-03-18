package game.logic;

public interface GameMapDelegate {
	/** 
	 * @brief Player finished game map
	 * */
	default void completed(){}
	/** 
	 * @brief Player pressed a lever
	 * */
	default void pressedLever(){}
	/** 
	 * @brief Updates game, need to call the super method to walk hero in subclasses.
	 * */
	default void updateGame(String typed){}
}
