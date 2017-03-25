package game.logic;

/** 
 * Class represents a wall in the game
 * */
public class Wall extends GameObject {
	
	/** 
	 * Constructor
	 * */
	Wall(int x, int y) {
		super(x,y);
	}
	
	@Override
	public String getLetter() {
		return "X";
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}
}
