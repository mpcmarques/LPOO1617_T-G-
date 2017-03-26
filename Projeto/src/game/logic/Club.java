package game.logic;

/** 
 * Represents a club in the game
 * */
public class Club extends GameObject{
	
	/** 
	 * Club constructor
	 * @param x Position x
	 * @param y Position y
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
