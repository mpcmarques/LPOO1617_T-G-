package game.logic;
public class Guard extends Cell {
	private int x;
	private int y;
	private int guardMoves[];
	private int moveCounter;

	///	Constructor
	Guard(int x, int y) {
		super("G");
		// TODO Auto-generated constructor stub
		this.setX(x);
		this.setY(y);
		this.setMoveCounter(0);
	}

	// MARK: Getters and Setters
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[] getGuardMoves() {
		return guardMoves;
	}

	public void setGuardMoves(int guardMoves[]) {
		this.guardMoves = guardMoves;
		this.setMoveCounter(0);
	}

	public int getMoveCounter() {
		return moveCounter;
	}

	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}
}