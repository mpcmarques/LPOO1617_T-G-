package game.logic;

public class Coordinate2d extends Object{
	private int x, y;
	
	
	//	MARK: Constructor
	public Coordinate2d(int x, int y){
		setX(x);
		setY(y);
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
