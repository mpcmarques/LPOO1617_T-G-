package game.logic;

/** 
 * Represents a pilar in the game
 * */
public class Pilar extends GameObject {
	/** 
	 * Pilar constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Pilar(int x, int y) {
		super(x,y);
	}
	
	@Override
	public String getLetter() {
		return "p";
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}
}
