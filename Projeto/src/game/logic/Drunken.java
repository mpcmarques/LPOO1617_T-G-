package game.logic;

/** 
 * Class represents a Drunken guard
 * */
public class Drunken extends Guard {
	private boolean isSleeping;
	private int sleepTime;

	/** 
	 * Drunken guard constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Drunken(int x, int y) {
		super(x,y);
		this.setSleeping(false);
		this.setSleepTime(0);
	}

	/**
	 * Return true if guard is sleeping
	 * @return the isSleeping
	 */
	public boolean isSleeping() {
		return isSleeping;
	}

	/**
	 * Set drunken guard sleeping
	 * @param isSleeping the isSleeping to set
	 */
	public void setSleeping(boolean isSleeping) {
		//	Change letter is drunk guard is sleeping
		if (isSleeping){
			//	Random the sleeping time
			int sleepTime = RandomService.getRandomInt(3,8);
			this.setSleepTime(sleepTime);
		} 
		this.isSleeping = isSleeping;
	}
	
	@Override
	public String getLetter() {
		if (isSleeping) {
			return "g";
		} else {
			return "G";
		}
	}
	
	@Override
	public boolean canMoveTo() {
		return false;
	}

	/**
	 * Get drunken guard sleep time
	 * @return the sleepTime
	 */
	public int getSleepTime() {
		return sleepTime;
	}

	/**
	 * Set drunken guard sleep time
	 * @param sleepTime the sleepTime to set
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
}
