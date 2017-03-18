package game.logic;

public class Coordinate2d extends Object{
	private int x, y;
	
	
	//	MARK: Constructor
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
