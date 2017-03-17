package game.logic;
public class Hero extends Cell {
	private boolean haveKey;
	
	///	Constructor
	Hero(int x, int y) {
		super("H", x, y);
		setHaveKey(false);
	}

	/**
	 * @return the haveKey
	 */
	public boolean isHaveKey() {
		return haveKey;
	}

	/**
	 * @param haveKey the haveKey to set
	 */
	public void setHaveKey(boolean haveKey) {
		if (haveKey) {
			setLetter("K");
		} else {
			setLetter("H");
		}
		this.haveKey = haveKey;
	}
}

