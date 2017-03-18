package game.logic;
import java.util.ArrayList;

public class Guard extends Cell {
	private int moveCounter;
	private ArrayList<Coordinate2d> movePositions;

	///	Constructor
	Guard(int x, int y) {
		super("G", x, y);
		// TODO Auto-generated constructor stub
		this.setMoveCounter(0);
	}

	public int getMoveCounter() {
		return moveCounter;
	}

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