package game.logic;
import java.util.ArrayList;

public abstract class Guard extends GameObject {
	
	//	Direction guard is moving
	private int moveCounter;
	private ArrayList<Coordinate2d> movePositions;
	
	public Guard(int x, int y){
		super(x,y);
		this.setMoveCounter(0);
	}
	
	/**
	 * @return the moveCounter
	 */
	public int getMoveCounter() {
		return moveCounter;
	}

	/**
	 * @param moveCounter the moveCounter to set
	 */
	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	/**
	 * @return the movePositions
	 */
	public ArrayList<Coordinate2d> getMovePositions() {
		return movePositions;
	}

	/**
	 * @param movePositions the movePositions to set
	 */
	public void setMovePositions(ArrayList<Coordinate2d> movePositions) {
		this.movePositions = movePositions;
	}
}