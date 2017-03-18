package game.logic;

public class Cell extends Object {
	private String letter;
	private Coordinate2d coordinate;
	
	//	Constructor
	Cell(String string, int x, int y){
		this.setLetter(string);
		this.setCoordinate(new Coordinate2d(x,y));
	}

	/**
	 * @return the letter
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * @param letter the letter to set
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return coordinate.getX();
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return coordinate.getY();
	}

	/**
	 * @return the coordinate
	 */
	public Coordinate2d getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(Coordinate2d coordinate) {
		this.coordinate = coordinate;
	}
}
