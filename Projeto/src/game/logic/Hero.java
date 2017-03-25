package game.logic;

public class Hero extends GameObject{
	private boolean hasKey;
	private boolean hasClub;
	
	///	Constructor
	Hero(int x, int y) {
		super(x,y);
		setHasKey(false);
	}

	@Override public String getLetter(){
		if (hasKey){
			return "K";
		}
		else if (hasClub){
			return "A";
		} else {
			return "H";
		}
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}

	/**
	 * @return the hasKey
	 */
	public boolean hasKey() {
		return hasKey;
	}

	/**
	 * @param hasKey the hasKey to set
	 */
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}

	/**
	 * @return the hasClub
	 */
	public boolean isHasClub() {
		return hasClub;
	}

	/**
	 * @param hasClub the hasClub to set
	 */
	public void setHasClub(boolean hasClub) {
		this.hasClub = hasClub;
	}
}

