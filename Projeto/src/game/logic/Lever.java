package game.logic;
/** 
 * Class represents a lever in the game
 * */
public class Lever extends GameObject{

	/** 
	 * Lever constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Lever(int x, int y) {
		super(x,y);
	}
	
	@Override
	public String getLetter() {
		return "l";
	}
	
	@Override
	public boolean canMoveTo() {
		return true;
	}
}