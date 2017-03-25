package game.logic;

/** 
 * Interface
 * */
public abstract class GameObject {
	private Coordinate2d coordinates;
	
	GameObject(int x, int y){
		this.setCoordinates(new Coordinate2d(x,y));
	}
	
	/**
	 * @return the letter representation of the game object
	 */
	public abstract String getLetter();
	
	/** 
	 * @return True if can move to element
	 * */
	public abstract boolean canMoveTo();

	/**
	 * @return Game object x position
	 */
	public int getX() {
		return coordinates.getX();
	}
	/** 
	 * @return Game object y position
	 * */
	public int getY(){
		return coordinates.getY();
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(Coordinate2d coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the coordinates
	 */
	public Coordinate2d getCoordinates() {
		return coordinates;
	}
}

