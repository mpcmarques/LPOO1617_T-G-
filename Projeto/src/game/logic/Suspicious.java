package game.logic;

/** 
 * Class represents a suspicious guard in the game
 * */
public class Suspicious extends Guard {
	private boolean isInReverse;
	
	/** 
	 * 	Suspicious constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Suspicious(int x, int y) {
		super(x,y);
		this.setInReverse(false);
	}
	
	@Override
	public String getLetter() {
		return "G";
	}
	@Override
	public boolean canMoveTo() {
		return false;
	}
	
	//	Change guard direction
	public void reverseDirection(){
		if (isInReverse){
			this.setInReverse(false);
		} else {
			this.setInReverse(true);
		}
	}

	/**
	 * @return the isInReverse
	 */
	public boolean isInReverse() {
		return isInReverse;
	}

	/**
	 * @param isInReverse the isInReverse to set
	 */
	public void setInReverse(boolean isInReverse) {
		this.isInReverse = isInReverse;
	}

}
