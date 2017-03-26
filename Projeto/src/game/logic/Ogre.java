package game.logic;

public class Ogre extends GameObject {
	private boolean isStunned;
	private boolean isOnKey;
	private int stunCounter;
	private OgreClub club;
	
	/** 
	 * Constructor
	 * */
	public Ogre(int x, int y, boolean hasClub) {
		super(x,y);
		this.setOnKey(false);
		setStunned(false);
		setStunCounter(0);
		if (hasClub) setClub(new OgreClub(x,y));
	}
	
	@Override
	public String getLetter() {
		if(isStunned){
			return "8";
		} else if (isOnKey){
			return "$";
		}else{
			return "O"; 
		}
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}
	
	/** 
	 * Adds club to ogre
	 * */
	public void addClub(){
		this.setClub(new OgreClub(getX(),getY()));
	}

	/**
	 * @return the isStunned
	 */
	public boolean isStunned() {
		return isStunned;
	}

	/** 
	 * Decreases stun counter
	 * */
	public void decreaseStunCounter(){
		this.stunCounter--;
		if(stunCounter <= 0){
			stunCounter = 0;
			// Stop stun
			this.setStunned(false);
		}
	}
	
	/**
	 * @param isStunned the isStunned to set
	 */
	public void setStunned(boolean isStunned) {
		if (isStunned){
			// Set stun counter
			setStunCounter(2);
		}
		this.isStunned = isStunned;
	}
	
	/**
	 * @return the stunCounter
	 */
	public int getStunCounter() {
		return stunCounter;
	}

	/**
	 * @param stunCounter the stunCounter to set
	 */
	public void setStunCounter(int stunCounter) {
		this.stunCounter = stunCounter;
	}

	/**
	 * @return the club
	 */
	public OgreClub getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public void setClub(OgreClub club) {
		this.club = club;
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
