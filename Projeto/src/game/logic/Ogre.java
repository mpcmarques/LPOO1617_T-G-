package game.logic;

public class Ogre extends GameObject {
	private boolean isStunned;
	private int stunCounter;
	private boolean hasClub;
	
	/** 
	 * Constructor
	 * */
	public Ogre(int x, int y, boolean hasClub) {
		super(x,y);
		setStunned(false);
		setStunCounter(0);
		this.hasClub = hasClub;
	}
	
	@Override
	public String getLetter() {
		if(isStunned){
			return "8";
		} else {
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
		this.setHasClub(true);
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
	 * @return the hasClub
	 */
	public boolean hasClub() {
		return hasClub;
	}

	/**
	 * @param hasClub the hasClub to set
	 */
	public void setHasClub(boolean hasClub) {
		this.hasClub = hasClub;
	}
}
