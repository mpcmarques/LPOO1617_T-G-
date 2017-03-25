package game.logic;

/** 
 * @brief Represents a pilar in the game
 * */
public class Pilar extends GameObject {
	/** 
	 * Pilar constructor
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
