package game.logic;

/** 
 * Class represents a door in the game
 * */
public class Door extends GameObject {
	private boolean isOpen;

	/** 
	 * Door constructor
	 * @param x Position x
	 * @param y Position y
	 * */
	public Door(int x, int y) {
		super(x,y);
		setOpen(false);
	}
	
	@Override public String getLetter(){
		if(isOpen){ 
			return "S";
		}
		else return "I";
	}
	
	@Override
	public boolean canMoveTo() {
		if (isOpen){
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Return true if door is open
	 * @return True if the door is open
	 * */
	public boolean isOpen() {
		return isOpen;
	}
	
	/** 
	 * Set door open or closed
	 * @param isOpen Door is open
	 * */
	private void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	/** 
	 * Opens the door
	 * */
	public void openDoor(){
		this.setOpen(true);
	}
}