package game.logic;

/** 
 * Abstract class represents a game object
 * */
public abstract class GameObject {
	private Coordinate2d coordinates;
	/** 
	 * Game Object constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	GameObject(int x, int y){
		this.setCoordinates(new Coordinate2d(x,y));
	}
	
	/**
	 * Get letter representation of the game object
	 * @return the letter representation of the game object
	 */
	public abstract String getLetter();
	
	/** 
	 * Returns true if can move to the game object
	 * @return True if can move to the game object
	 * */
	public abstract boolean canMoveTo();

	/**
	 * Return object x position
	 * @return Game object x position
	 */
	public int getX() {
		return coordinates.getX();
	}
	/** 
	 * Return object y position
	 * @return Game object y position
	 * */
	public int getY(){
		return coordinates.getY();
	}

	/**
	 * Set game object coordinates
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(Coordinate2d coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Return game object coordinates
	 * @return the coordinates
	 */
	public Coordinate2d getCoordinates() {
		return coordinates;
	}
}

