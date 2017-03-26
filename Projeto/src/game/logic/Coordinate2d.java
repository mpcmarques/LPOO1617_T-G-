package game.logic;
/** 
 * Class represents a 2d Coordinate (x,y)
 * */
public class Coordinate2d extends Object{
	private int x, y;
	
	/** 
	 * Coordinate2d constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Coordinate2d(int x, int y){
		setX(x);
		setY(y);
	}

	public boolean equals(Object obj){
		return (obj instanceof Coordinate2d) &&
				((Coordinate2d)obj).getX() == x &&
				((Coordinate2d)obj).getY() == y
				;
	}
	
	/**
	 * Get coordinate x
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * Set coordinate x
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get coordinate y
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set coordinate y
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
