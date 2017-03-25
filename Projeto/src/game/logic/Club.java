package game.logic;

/** 
 * Represents a club in the game
 * */
public class Club extends GameObject{
	
	/** 
	 * Constructor
	 * */
	Club(int x, int y) {
		super(x, y);
	}
	
	@Override
	public String getLetter() {
		return "*";
	}
	@Override
	public boolean canMoveTo() {
		return true;
	}
}
