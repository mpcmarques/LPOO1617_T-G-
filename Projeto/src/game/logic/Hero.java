package game.logic;

public class Hero extends Cell {
	private boolean haveKey;
	private Club club;
	
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
	
	public boolean hasWeapon(){
		return club != null;
	}

	/**
	 * @return the club
	 */
	public Club getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public void setClub(Club club) {
		this.club = club;
		// Change letter
		this.setLetter("A");
	}
}

