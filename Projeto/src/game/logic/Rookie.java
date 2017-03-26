package game.logic;

/** 
 * Class represents a Rookie guard in the game
 * */
public class Rookie extends Guard {
	
	/** 
	 * Rookie constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Rookie(int x, int y){
		super(x,y);
	}

	@Override
	public String getLetter() {
		return "G";
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}
}
