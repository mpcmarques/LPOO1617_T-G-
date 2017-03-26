package game.logic;
/** 
 * Class represents a hero in the game
 * */
public class Hero extends GameObject{
	private boolean hasKey;
	private boolean hasClub;
	
	/** 
	 * Hero constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Hero(int x, int y) {
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
	 * Return true if hero has key
	 * @return the hasKey
	 */
	public boolean hasKey() {
		return hasKey;
	}

	/**
	 * Set if hero has the key
	 * @param hasKey the hasKey to set
	 */
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}

	/**
	 * Return true if hero has the club
	 * @return the hasClub
	 */
	public boolean isHasClub() {
		return hasClub;
	}

	/**
	 * Set club to the hero
	 * @param hasClub the hasClub to set
	 */
	public void setHasClub(boolean hasClub) {
		this.hasClub = hasClub;
	}
}

