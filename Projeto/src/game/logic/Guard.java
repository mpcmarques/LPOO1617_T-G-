package game.logic;
import java.util.ArrayList;

public abstract class Guard extends Cell {
	private int moveCounter;
	private ArrayList<Coordinate2d> movePositions;

	/** 
	 * Constructor
	 * */
	public Guard(int x, int y) {
		super("G", x, y);
		// TODO Auto-generated constructor stub
		this.setMoveCounter(0);
	}
	
	/** 
	 * Creates guard with coordinates 0,0
	 * */
	Guard(){
		super("G",0,0);
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