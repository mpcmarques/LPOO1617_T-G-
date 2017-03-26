package game.logic;

/** 
 * This class represents a ogre club
 * */
public class OgreClub extends Club {
	private boolean isOnKey;
	/** 
	 * OgreClub constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	OgreClub(int x, int y) {
		super(x, y);
		this.isOnKey = false;
	}
	/** 
	 * OgreClub constructor
	 * */
	OgreClub(){
		super(0,0);
	}

	/** 
	 * Don't allow moving to a ogre club
	 * */
	public boolean canMoveTo(){
		return false;
	}
	
	public String getLetter(){
		if(isOnKey){
			return "$";
		} else {
			return "*";
		}
	}
	
	/**
	 * Check if ogre club is on key
	 * @return True if ogre club is on key
	 */
	public boolean isOnKey() {
		return isOnKey;
	}

	/**
	 * Set ogre club on club
	 * @param isOnKey Set ogre club is on key
	 */
	public void setOnKey(boolean isOnKey) {
		this.isOnKey = isOnKey;
	}
}
