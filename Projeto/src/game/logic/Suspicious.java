package game.logic;

public class Suspicious extends Guard {
	private boolean isInReverse;
	
	public Suspicious(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.setInReverse(false);
	}
	
	public Suspicious(){
		super();
		this.setInReverse(false);
	}
	
	//	Change guard direction
	public void changeDirection(){
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
