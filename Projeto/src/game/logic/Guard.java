package game.logic;
public class Guard extends Cell {
	private int guardMoves[];
	private int moveCounter;

	///	Constructor
	Guard(int x, int y) {
		super("G", x, y);
		// TODO Auto-generated constructor stub
		this.setX(x);
		this.setY(y);
		this.setMoveCounter(0);
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