package game.logic;

public class Drunken extends Guard {
	private boolean isSleeping;
	private int sleepTime;

	public Drunken(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.setSleeping(false);
		this.setSleepTime(0);
	}

	public Drunken(){
		super();
		// TODO Auto-generated constructor stub
		this.setSleeping(false);
		this.setSleepTime(0);
	}

	/**
	 * @return the isSleeping
	 */
	public boolean isSleeping() {
		return isSleeping;
	}

	/**
	 * @param isSleeping the isSleeping to set
	 */
	public void setSleeping(boolean isSleeping) {
		//	Change letter is drunk guard is sleeping
		if (isSleeping){
			//	Random the sleeping time
			int sleepTime = RandomService.getRandomInt(3,8);
			this.setSleepTime(sleepTime);
			setLetter("g");
		} else {
			setLetter("G");
		}
		this.isSleeping = isSleeping;
	}

	/**
	 * @return the sleepTime
	 */
	public int getSleepTime() {
		return sleepTime;
	}

	/**
	 * @param sleepTime the sleepTime to set
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

}
