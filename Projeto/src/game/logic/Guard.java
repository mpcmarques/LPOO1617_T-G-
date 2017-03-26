package game.logic;
import java.util.ArrayList;
/** 
 * Abstract class represents a guard
 * */
public abstract class Guard extends GameObject {
	
	//	Direction guard is moving
	private int moveCounter;
	private ArrayList<Coordinate2d> movePositions;
	/** 
	 * Guard constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Guard(int x, int y){
		super(x,y);
		this.setMoveCounter(0);
	}
	
	/**
	 * Return guard move counter
	 * @return the moveCounter
	 */
	public int getMoveCounter() {
		return moveCounter;
	}

	/**
	 * Set guard move counter
	 * @param moveCounter the moveCounter to set
	 */
	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	/**
	 * Get guard move positions
	 * @return the movePositions
	 */
	public ArrayList<Coordinate2d> getMovePositions() {
		return movePositions;
	}

	/**
	 * Set guard move positions
	 * @param movePositions the movePositions to set
	 */
	public void setMovePositions(ArrayList<Coordinate2d> movePositions) {
		this.movePositions = movePositions;
	}
}