package game.logic;

public class Ogre extends Cell{
	private Club club;
	private boolean isStunned;
	private int stunCounter;
	
	Ogre(int x, int y, boolean hasClub) {
		super("O", x, y);
		// TODO Auto-generated constructor stub
		setStunned(false);
		setStunCounter(0);
		if (hasClub){
			addClub();
		} else {
			setClub(null);
		}
	}
	
	public void addClub(){
		this.club = new Club(getX(),getY());
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
			setLetter("8");
		} else {
			setLetter("O");
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

}
