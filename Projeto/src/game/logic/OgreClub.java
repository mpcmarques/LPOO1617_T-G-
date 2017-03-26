package game.logic;

/** 
 * This class represents a ogre club
 * */
public class OgreClub extends Club {
	private boolean isOnKey;
	
	OgreClub(int x, int y) {
		super(x, y);
		this.isOnKey = false;
	}
	
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
	 * @return the isOnKey
	 */
	public boolean isOnKey() {
		return isOnKey;
	}

	/**
	 * @param isOnKey the isOnKey to set
	 */
	public void setOnKey(boolean isOnKey) {
		this.isOnKey = isOnKey;
	}
}
