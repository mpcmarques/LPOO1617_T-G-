package game.logic;

/** 
 * Class represents a key in the game
 * */
public class Key extends GameObject {
	/** 
	 * Key constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Key(int x, int y) {
		super(x,y);
	}
	
	@Override
	public boolean canMoveTo() {
		return true;
	}

	@Override
	public String getLetter() {
		return "k";
	}
}
